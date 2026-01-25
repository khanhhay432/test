package com.testing.bai4;
import org.junit.Test;
import static org.junit.Assert.*;

public class RectangleTest {

    @Test
    public void testArea() {
        HinhChuNhat r = new HinhChuNhat(new Diem(0,5), new Diem(5,0));
        assertEquals(25, r.area(), 0.0001);
    }

    @Test
    public void testIntersect() {
        HinhChuNhat r1 = new HinhChuNhat(new Diem(0,5), new Diem(5,0));
        HinhChuNhat r2 = new HinhChuNhat(new Diem(3,4), new Diem(7,1));
        assertTrue(r1.intersect(r2));
    }

    @Test
    public void testNoIntersect() {
        HinhChuNhat r1 = new HinhChuNhat(new Diem(0,5), new Diem(5,0));
        HinhChuNhat r2 = new HinhChuNhat(new Diem(6,4), new Diem(9,1));
        assertFalse(r1.intersect(r2));
    }
}
