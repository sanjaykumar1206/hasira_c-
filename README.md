Hashira Placement Assignment - Polynomial Secret Finder
Project Overview

This Java program reads a JSON file containing points from a polynomial and computes the secret constant (c) of the polynomial using Lagrange interpolation.
The program can handle values in different bases (binary, octal, decimal, hexadecimal, etc.) and works with large numbers using BigInteger.

This assignment is based on Shamir’s Secret Sharing concept.

Project Files
File Name	Description
Main.java	Java source code for computing the polynomial constant.
testcase1.json	Sample JSON test case 1.
testcase2.json	Sample JSON test case 2.
jackson-core-2.15.2.jar	Jackson library core JAR.
jackson-annotations-2.15.2.jar	Jackson library annotations JAR.
jackson-databind-2.15.2.jar	Jackson library databind JAR.
Prerequisites

Java JDK 17 or above installed.

Download Jackson libraries:

jackson-core-2.15.2.jar

jackson-annotations-2.15.2.jar

jackson-databind-2.15.2.jar

How to Run
1. Compile the Java program

Windows:

javac -cp ".;jackson-core-2.15.2.jar;jackson-annotations-2.15.2.jar;jackson-databind-2.15.2.jar" Main.java


Linux / Mac:

javac -cp ".:jackson-core-2.15.2.jar:jackson-annotations-2.15.2.jar:jackson-databind-2.15.2.jar" Main.java

2. Run the program

Windows:

java -cp ".;jackson-core-2.15.2.jar;jackson-annotations-2.15.2.jar;jackson-databind-2.15.2.jar" Main testcase1.json
java -cp ".;jackson-core-2.15.2.jar;jackson-annotations-2.15.2.jar;jackson-databind-2.15.2.jar" Main testcase2.json


Linux / Mac:

java -cp ".:jackson-core-2.15.2.jar:jackson-annotations-2.15.2.jar:jackson-databind-2.15.2.jar" Main testcase1.json
java -cp ".:jackson-core-2.15.2.jar:jackson-annotations-2.15.2.jar:jackson-databind-2.15.2.jar" Main testcase2.json

3. Expected Output

Testcase 1:

Decoded points (first 3):
x = 1, y = 4
x = 2, y = 7
x = 3, y = 12
c (decimal) = 3


Testcase 2:

Decoded points (first 7):
x = 1, y = ...
...
c (decimal) = -6290016743746469796


The output c (decimal) is the secret constant of the polynomial.

Notes

Make sure all JAR files and JSON files are in the same folder as Main.java.

If k points are not provided or if JSON has invalid base values, the program will show an error.

The program uses BigInteger to handle very large numbers.

Author

Your Name: sanjay kumar yadla
Course / Year: Final Year, 2026
Assignment: Hashira Placements Assignment – Polynomial Secret Finder
