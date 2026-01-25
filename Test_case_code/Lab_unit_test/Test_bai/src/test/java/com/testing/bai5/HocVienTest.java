package com.testing.bai5;
import org.junit.Test;
import static org.junit.Assert.*;

public class HocVienTest {

    @Test
    public void testEligible() {
        HocVien hv = new HocVien();
        hv.toan = 9; hv.ly = 8; hv.hoa = 8;
        assertTrue(hv.scholarship());
    }

    @Test
    public void testLowAverage() {
        HocVien hv = new HocVien();
        hv.toan = 7; hv.ly = 7; hv.hoa = 7;
        assertFalse(hv.scholarship());
    }

    @Test
    public void testSubjectBelow5() {
        HocVien hv = new HocVien();
        hv.toan = 9; hv.ly = 9; hv.hoa = 4;
        assertFalse(hv.scholarship());
    }
}
