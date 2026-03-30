package com.testing.bai2;
import java.util.List;

public class Polynomial {
    private int n;
    private List<Integer> a;

    public Polynomial(int n, List<Integer> a) {
        if (n < 0 || a.size() != n + 1)
            throw new IllegalArgumentException("Invalid Data");
        this.n = n;
        this.a = a;
    }

    public double cal(double x) {
        double result = 0;
        for (int i = 0; i <= n; i++)
            result += a.get(i) * Math.pow(x, i);
        return result;
    }
}
