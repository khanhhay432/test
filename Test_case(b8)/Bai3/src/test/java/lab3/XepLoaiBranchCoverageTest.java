package lab3;

import org.testng.Assert;
import org.testng.annotations.*;

/**
 * BÀI 3 - Bài tập 3.1: Statement & Branch Coverage cho hàm xepLoai
 *
 * ============================================================
 * CFG & PHÂN TÍCH COVERAGE:
 *
 * STATEMENT COVERAGE (5 lệnh return):
 *   TC1 phủ: N1-True
 *   TC2 phủ: N2-True
 *   TC3 phủ: N3-True
 *   TC4 phủ: N4-True
 *   TC5 phủ: N5-True
 *   TC6 phủ: N5-False
 *   → Statement Coverage = 6/6 = 100%
 *
 * BRANCH COVERAGE (10 nhánh = 5 decision × 2):
 *   N1-True  : TC1 (diem=-1)
 *   N1-False : TC2,TC3,TC4,TC5,TC6
 *   N2-True  : TC2 (diem=9.0)
 *   N2-False : TC3,TC4,TC5,TC6
 *   N3-True  : TC3 (diem=7.5)
 *   N3-False : TC4,TC5,TC6
 *   N4-True  : TC4 (diem=6.0)
 *   N4-False : TC5,TC6
 *   N5-True  : TC5 (coThiLai=true)
 *   N5-False : TC6 (coThiLai=false)
 *   → Branch Coverage = 10/10 = 100%
 * ============================================================
 */
public class XepLoaiBranchCoverageTest {

    // ===== STATEMENT COVERAGE TEST CASES =====

    @Test(description = "TC01-Stmt: Diem am → 'Diem khong hop le' [phu N1-True]")
    public void TC01_DiemKhongHopLe_Am() {
        Assert.assertEquals(XepLoai.xepLoai(-1, false), "Diem khong hop le",
            "Diem am phai la 'Diem khong hop le'");
    }

    @Test(description = "TC02-Stmt: Diem > 10 → 'Diem khong hop le' [phu N1-True]")
    public void TC02_DiemKhongHopLe_Lon() {
        Assert.assertEquals(XepLoai.xepLoai(11, false), "Diem khong hop le",
            "Diem > 10 phai la 'Diem khong hop le'");
    }

    @Test(description = "TC03-Stmt: Diem 9.0 → 'Gioi' [phu N1-False, N2-True]")
    public void TC03_Gioi() {
        Assert.assertEquals(XepLoai.xepLoai(9.0, false), "Gioi",
            "Diem 9.0 phai la 'Gioi'");
    }

    @Test(description = "TC04-Stmt: Diem 7.5 → 'Kha' [phu N2-False, N3-True]")
    public void TC04_Kha() {
        Assert.assertEquals(XepLoai.xepLoai(7.5, false), "Kha",
            "Diem 7.5 phai la 'Kha'");
    }

    @Test(description = "TC05-Stmt: Diem 6.0 → 'Trung Binh' [phu N3-False, N4-True]")
    public void TC05_TrungBinh() {
        Assert.assertEquals(XepLoai.xepLoai(6.0, false), "Trung Binh",
            "Diem 6.0 phai la 'Trung Binh'");
    }

    @Test(description = "TC06-Branch: Diem 4.0, coThiLai=true → 'Thi lai' [phu N4-False, N5-True]")
    public void TC06_ThiLai() {
        Assert.assertEquals(XepLoai.xepLoai(4.0, true), "Thi lai",
            "Diem 4.0 coThiLai=true phai la 'Thi lai'");
    }

    @Test(description = "TC07-Branch: Diem 4.0, coThiLai=false → 'Yeu - Hoc lai' [phu N5-False]")
    public void TC07_YeuHocLai() {
        Assert.assertEquals(XepLoai.xepLoai(4.0, false), "Yeu - Hoc lai",
            "Diem 4.0 coThiLai=false phai la 'Yeu - Hoc lai'");
    }

    // ===== BOUNDARY TEST CASES (giá trị biên) =====

    @Test(description = "TC08-Boundary: Diem bien 8.5 → 'Gioi'")
    public void TC08_Boundary_8_5() {
        Assert.assertEquals(XepLoai.xepLoai(8.5, false), "Gioi",
            "Diem bien 8.5 phai la 'Gioi'");
    }

    @Test(description = "TC09-Boundary: Diem bien 7.0 → 'Kha'")
    public void TC09_Boundary_7_0() {
        Assert.assertEquals(XepLoai.xepLoai(7.0, false), "Kha",
            "Diem bien 7.0 phai la 'Kha'");
    }

    @Test(description = "TC10-Boundary: Diem bien 5.5 → 'Trung Binh'")
    public void TC10_Boundary_5_5() {
        Assert.assertEquals(XepLoai.xepLoai(5.5, false), "Trung Binh",
            "Diem bien 5.5 phai la 'Trung Binh'");
    }

    @Test(description = "TC11-Boundary: Diem bien 0 → 'Yeu - Hoc lai'")
    public void TC11_Boundary_0() {
        Assert.assertEquals(XepLoai.xepLoai(0, false), "Yeu - Hoc lai",
            "Diem bien 0 (hop le) phai la 'Yeu - Hoc lai'");
    }

    @Test(description = "TC12-Boundary: Diem bien 10 → 'Gioi'")
    public void TC12_Boundary_10() {
        Assert.assertEquals(XepLoai.xepLoai(10, false), "Gioi",
            "Diem bien 10 phai la 'Gioi'");
    }
}
