package com.testing.bai2;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class PolynomialTest {

    @Test
    public void testConstantPolynomial() {
        Polynomial p = new Polynomial(0, Arrays.asList(5));
        assertEquals(5, p.cal(10), 0.0001);
    }

    @Test
    public void testXEqualsZero() {
        Polynomial p = new Polynomial(3, Arrays.asList(7,2,3,4));
        assertEquals(7, p.cal(0), 0.0001);
    }

    @Test
    public void testNegativeX() {
        Polynomial p = new Polynomial(2, Arrays.asList(1,2,1));
        assertEquals(0, p.cal(-1), 0.0001);
    }

    @Test
    public void testNegativeCoefficients() {
        Polynomial p = new Polynomial(2, Arrays.asList(-1,-2,-1));
        assertEquals(-9, p.cal(2), 0.0001);
    }

    @Test
    public void testZeroCoefficients() {
        Polynomial p = new Polynomial(3, Arrays.asList(5,0,0,0));
        assertEquals(5, p.cal(100), 0.0001);
    }
}
