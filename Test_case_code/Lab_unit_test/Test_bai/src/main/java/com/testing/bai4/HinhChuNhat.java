package com.testing.bai4;

public class HinhChuNhat {
    private Diem tl, br;

    public HinhChuNhat(Diem tl, Diem br) {
        if (tl.x >= br.x || tl.y <= br.y)
            throw new IllegalArgumentException("Invalid Rectangle");
        this.tl = tl; this.br = br;
    }

    public double area() {
        return (br.x - tl.x) * (tl.y - br.y);
    }

    public boolean intersect(HinhChuNhat o) {
        return !(o.tl.x >= br.x || o.br.x <= tl.x || o.tl.y <= br.y || o.br.y >= tl.y);
    }
}
