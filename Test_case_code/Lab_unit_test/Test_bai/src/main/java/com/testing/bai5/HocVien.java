package com.testing.bai5;

public class HocVien {
    public double toan, ly, hoa;

    public double avg() { return (toan + ly + hoa) / 3; }

    public boolean scholarship() {
        return avg() >= 8 && toan >= 5 && ly >= 5 && hoa >= 5;
    }
}
