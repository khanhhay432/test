package com.testing.bai3;
import org.junit.Test;
import static org.junit.Assert.*;

public class RadixTest {

    @Test
    public void testBinary() {
        assertEquals("1010", new Radix(10).convert(2));
    }

    @Test
    public void testHex() {
        assertEquals("FF", new Radix(255).convert(16));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRadix() {
        new Radix(10).convert(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeNumber() {
        new Radix(-5);
    }
}
