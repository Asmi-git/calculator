package com.example;

/**
 * Pure-Java calculator that provides the four basic arithmetic operations.
 * No servlet or framework imports — this class is independently testable.
 */
public class Calculator {

    /**
     * Adds two numbers.
     *
     * @param a first operand
     * @param b second operand
     * @return a + b
     */
    public double add(double a, double b) {
        return a + b;
    }

    /**
     * Subtracts {@code b} from {@code a}.
     *
     * @param a first operand
     * @param b second operand
     * @return a - b
     */
    public double subtract(double a, double b) {
        return a - b;
    }

    /**
     * Multiplies two numbers.
     *
     * @param a first operand
     * @param b second operand
     * @return a * b
     */
    public double multiply(double a, double b) {
        return a * b;
    }

    /**
     * Divides {@code a} by {@code b}.
     *
     * @param a dividend
     * @param b divisor — must not be zero
     * @return a / b
     * @throws ArithmeticException if {@code b} is zero
     */
    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a / b;
    }
}
