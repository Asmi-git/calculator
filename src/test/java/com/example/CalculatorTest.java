package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Calculator}.
 *
 * Covers: normal values, negative operands, zero operands, boundary conditions,
 * and the divide-by-zero exception contract.
 */
@DisplayName("Calculator")
class CalculatorTest {

    private Calculator calc;

    @BeforeEach
    void setUp() {
        calc = new Calculator();
    }

    // ------------------------------------------------------------------
    // add
    // ------------------------------------------------------------------

    @Test
    @DisplayName("add: two positive numbers")
    void addTwoPositives() {
        assertEquals(7.0, calc.add(3, 4), 1e-9);
    }

    @Test
    @DisplayName("add: positive and negative number")
    void addPositiveAndNegative() {
        assertEquals(-1.0, calc.add(4, -5), 1e-9);
    }

    @Test
    @DisplayName("add: two negative numbers")
    void addTwoNegatives() {
        assertEquals(-9.0, calc.add(-4, -5), 1e-9);
    }

    @Test
    @DisplayName("add: with zero operand")
    void addWithZero() {
        assertEquals(5.0, calc.add(5, 0), 1e-9);
        assertEquals(5.0, calc.add(0, 5), 1e-9);
    }

    @Test
    @DisplayName("add: fractional numbers")
    void addFractional() {
        assertEquals(0.3, calc.add(0.1, 0.2), 1e-9);
    }

    // ------------------------------------------------------------------
    // subtract
    // ------------------------------------------------------------------

    @Test
    @DisplayName("subtract: two positive numbers")
    void subtractTwoPositives() {
        assertEquals(1.0, calc.subtract(5, 4), 1e-9);
    }

    @Test
    @DisplayName("subtract: result is negative")
    void subtractResultNegative() {
        assertEquals(-3.0, calc.subtract(2, 5), 1e-9);
    }

    @Test
    @DisplayName("subtract: two negative numbers")
    void subtractTwoNegatives() {
        assertEquals(-1.0, calc.subtract(-4, -3), 1e-9);
    }

    @Test
    @DisplayName("subtract: with zero")
    void subtractWithZero() {
        assertEquals(7.0,  calc.subtract(7, 0),  1e-9);
        assertEquals(-7.0, calc.subtract(0, 7),  1e-9);
    }

    @Test
    @DisplayName("subtract: same numbers yields zero")
    void subtractSameNumbers() {
        assertEquals(0.0, calc.subtract(42, 42), 1e-9);
    }

    // ------------------------------------------------------------------
    // multiply
    // ------------------------------------------------------------------

    @Test
    @DisplayName("multiply: two positive numbers")
    void multiplyTwoPositives() {
        assertEquals(12.0, calc.multiply(3, 4), 1e-9);
    }

    @Test
    @DisplayName("multiply: positive and negative")
    void multiplyPositiveAndNegative() {
        assertEquals(-20.0, calc.multiply(4, -5), 1e-9);
    }

    @Test
    @DisplayName("multiply: two negatives give positive")
    void multiplyTwoNegatives() {
        assertEquals(16.0, calc.multiply(-3, -5), 1e-9);
    }

    @Test
    @DisplayName("multiply: by zero")
    void multiplyByZero() {
        assertEquals(0.0, calc.multiply(99, 0), 1e-9);
        assertEquals(0.0, calc.multiply(0, 99), 1e-9);
    }

    @Test
    @DisplayName("multiply: by one (identity)")
    void multiplyByOne() {
        assertEquals(8.0, calc.multiply(8, 1), 1e-9);
    }

    @Test
    @DisplayName("multiply: fractional numbers")
    void multiplyFractional() {
        assertEquals(2.0, calc.multiply(0.5, 4.0), 1e-9);
    }

    // ------------------------------------------------------------------
    // divide
    // ------------------------------------------------------------------

    @Test
    @DisplayName("divide: two positive numbers")
    void divideTwoPositives() {
        assertEquals(2.5, calc.divide(5, 2), 1e-9);
    }

    @Test
    @DisplayName("divide: positive by negative")
    void dividePositiveByNegative() {
        assertEquals(-2.0, calc.divide(6, -3), 1e-9);
    }

    @Test
    @DisplayName("divide: negative by negative")
    void divideNegativeByNegative() {
        assertEquals(3.0, calc.divide(-9, -3), 1e-9);
    }

    @Test
    @DisplayName("divide: zero dividend")
    void divideZeroDividend() {
        assertEquals(0.0, calc.divide(0, 5), 1e-9);
    }

    @Test
    @DisplayName("divide: by one (identity)")
    void divideByOne() {
        assertEquals(7.0, calc.divide(7, 1), 1e-9);
    }

    @Test
    @DisplayName("divide: fractional result")
    void divideFractional() {
        assertEquals(0.25, calc.divide(1, 4), 1e-9);
    }

    @Test
    @DisplayName("divide: by zero throws ArithmeticException")
    void divideByZeroThrows() {
        ArithmeticException ex = assertThrows(
                ArithmeticException.class,
                () -> calc.divide(10, 0)
        );
        // Verify the exception message is informative
        assertNotNull(ex.getMessage(), "Exception message must not be null");
        assertFalse(ex.getMessage().isBlank(), "Exception message must not be blank");
    }

    @Test
    @DisplayName("divide: negative number by zero throws ArithmeticException")
    void divideNegativeByZeroThrows() {
        assertThrows(ArithmeticException.class, () -> calc.divide(-5, 0));
    }
}
