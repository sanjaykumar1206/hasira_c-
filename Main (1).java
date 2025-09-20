import java.io.File;
import java.math.BigInteger;
import java.util.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    // Simple pair
    static class Point {
        final long x;
        final BigInteger y;
        Point(long x, BigInteger y) { this.x = x; this.y = y; }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java -cp \".;lib/*\" Main <filename.json>");
            return;
        }

        String fileName = args[0];
        File file = new File(fileName);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(file);

        int n = root.get("keys").get("n").asInt();
        int k = root.get("keys").get("k").asInt();
        if (k <= 0) {
            System.err.println("Invalid k in keys");
            return;
        }

        // Read all points (keys except "keys")
        List<Point> points = new ArrayList<>();
        Iterator<String> fn = root.fieldNames();
        while (fn.hasNext()) {
            String key = fn.next();
            if (key.equals("keys")) continue;
            long x = Long.parseLong(key);
            JsonNode node = root.get(key);
            int base = Integer.parseInt(node.get("base").asText());
            String valueStr = node.get("value").asText();
            BigInteger y;
            try {
                y = new BigInteger(valueStr, base);
            } catch (Exception ex) {
                System.err.println("Failed to parse value '" + valueStr + "' at base " + base + " for x=" + key);
                return;
            }
            points.add(new Point(x, y));
        }

        if (points.size() < k) {
            System.err.println("Not enough points: provided " + points.size() + ", required k=" + k);
            return;
        }

        // Deterministic selection: sort by x and pick first k
        points.sort(Comparator.comparingLong(p -> p.x));
        List<Point> use = points.subList(0, k);

        // Debug: print decoded points used
        System.out.println("Decoded points (first " + k + "):");
        for (Point p : use) {
            System.out.println("x = " + p.x + ", y = " + p.y);
        }

        // Compute P(0) exactly via Lagrange:
        // P(0) = sum_i [ y_i * prod_{j!=i} (0 - x_j) / (x_i - x_j) ]
        // We'll accumulate result as rational resultNum/resultDen
        BigInteger resultNum = BigInteger.ZERO;
        BigInteger resultDen = BigInteger.ONE;

        int m = use.size();
        for (int i = 0; i < m; i++) {
            BigInteger yi = use.get(i).y;
            BigInteger numProd = BigInteger.ONE; // numerator product (yi * product (0 - xj))
            BigInteger denProd = BigInteger.ONE; // denominator product (product (xi - xj))

            long xi = use.get(i).x;
            for (int j = 0; j < m; j++) {
                if (j == i) continue;
                long xj = use.get(j).x;
                numProd = numProd.multiply(BigInteger.valueOf(-xj)); // (0 - xj) = -xj
                denProd = denProd.multiply(BigInteger.valueOf(xi - xj)); // (xi - xj)
            }

            BigInteger termNum = yi.multiply(numProd);
            BigInteger termDen = denProd;

            // Normalize sign: make denominator positive
            if (termDen.signum() < 0) {
                termDen = termDen.negate();
                termNum = termNum.negate();
            }

            // Add termNum/termDen to resultNum/resultDen:
            // newNum = resultNum*termDen + termNum*resultDen
            // newDen = resultDen * termDen
            BigInteger newNum = resultNum.multiply(termDen).add(termNum.multiply(resultDen));
            BigInteger newDen = resultDen.multiply(termDen);

            // Reduce by gcd
            BigInteger g = newNum.gcd(newDen);
            if (!g.equals(BigInteger.ZERO)) {
                newNum = newNum.divide(g);
                newDen = newDen.divide(g);
            }

            resultNum = newNum;
            resultDen = newDen;
        }

        // Final: resultNum/resultDen should be integer for integer-coefficient polynomials;
        // but we handle the general case: check divisibility.
        BigInteger[] divrem = resultNum.divideAndRemainder(resultDen);
        if (!divrem[1].equals(BigInteger.ZERO)) {
            // If not exact integer, print warning and print quotient (floor). 
            // (For this assignment the result should be integer.)
            System.err.println("Warning: P(0) is not an exact integer. Printing floor division.");
            System.out.println(divrem[0].toString());
        } else {
            System.out.println("c (decimal) = " + divrem[0].toString());
        }
    }
}
