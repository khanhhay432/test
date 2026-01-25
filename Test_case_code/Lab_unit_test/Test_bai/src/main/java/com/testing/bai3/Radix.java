package com.testing.bai3;

public class Radix {
    private int number;

    public Radix(int number) {
        if (number < 0) throw new IllegalArgumentException("Incorrect Value");
        this.number = number;
    }

    public String convert(int radix) {
        if (radix < 2 || radix > 16)
            throw new IllegalArgumentException("Invalid Radix");
        return Integer.toString(number, radix).toUpperCase();
    }
}
