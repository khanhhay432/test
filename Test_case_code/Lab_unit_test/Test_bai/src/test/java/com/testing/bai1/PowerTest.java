package com.testing.bai1;

import org.junit.Test;
import static org.junit.Assert.*;

public class PowerTest {

    @Test
    public void testPositiveExponent() {
        assertEquals(8, PowerCalculator.power(2,3), 0.0001);
    }

    @Test
    public void testZeroExponent() {
        assertEquals(1, PowerCalculator.power(5,0), 0.0001);
    }

    @Test
    public void testNegativeExponent() {
        assertEquals(0.25, PowerCalculator.power(2,-2), 0.0001);
    }

    @Test
    public void testBaseZeroPositiveN() {
        assertEquals(0, PowerCalculator.power(0,5), 0.0001);
    }

    @Test
    public void testNegativeBaseOddExponent() {
        assertEquals(-8, PowerCalculator.power(-2,3), 0.0001);
    }

    @Test
    public void testNegativeBaseEvenExponent() {
        assertEquals(16, PowerCalculator.power(-2,4), 0.0001);
    }

    @Test
    public void testExponentOne() {
        assertEquals(5, PowerCalculator.power(5,1), 0.0001);
    }

    @Test
    public void testExponentMinusOne() {
        assertEquals(0.2, PowerCalculator.power(5,-1), 0.0001);
    }

}
