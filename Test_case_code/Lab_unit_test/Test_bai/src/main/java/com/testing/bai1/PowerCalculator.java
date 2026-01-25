package com.testing.bai1;

public class PowerCalculator {
    public static double power(double x, int n) {
        if (n == 0) return 1.0;
        if (n > 0) return x * power(x, n - 1);
        return power(x, n + 1) / x;
    }
}
