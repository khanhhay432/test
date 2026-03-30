package lab5;

import org.testng.Assert;
import org.testng.annotations.*;

/**
 * BÀI 5 - Bài tập 5.2: MC/DC Test Cases cho hàm duDieuKienVay
 *
 * ================================================================
 * BẢNG CHÂN TRỊ (Truth Table):
 * Điều kiện đơn: A=(tuoi>=22), B=(thuNhap>=10tr), C=(coTaiSan), D=(tin>=700)
 * Kết quả = (A&&B) && (C||D)
 *
 * Row | A | B | C | D | A&&B | C||D | Kết quả
 *  1  | T | T | T | T |  T   |  T   |   T
 *  2  | T | T | T | F |  T   |  T   |   T
 *  3  | T | T | F | T |  T   |  T   |   T
 *  4  | T | T | F | F |  T   |  F   |   F  ← A&&B=T nhưng C||D=F
 *  5  | T | F | T | T |  F   |  T   |   F  ← B=F làm A&&B=F
 *  6  | T | F | T | F |  F   |  T   |   F
 *  7  | T | F | F | T |  F   |  T   |   F
 *  8  | T | F | F | F |  F   |  F   |   F
 *  9  | F | T | T | T |  F   |  T   |   F  ← A=F làm A&&B=F
 * 10  | F | T | T | F |  F   |  T   |   F
 * 11  | F | T | F | T |  F   |  T   |   F
 * 12  | F | T | F | F |  F   |  F   |   F
 * 13  | F | F | T | T |  F   |  T   |   F
 * 14  | F | F | T | F |  F   |  T   |   F
 * 15  | F | F | F | T |  F   |  T   |   F
 * 16  | F | F | F | F |  F   |  F   |   F
 *
 * MC/DC PAIRS:
 *   A độc lập: Row1(T,T,T,T→T) vs Row9(F,T,T,T→F) → B,C,D giữ nguyên, A thay đổi, Output khác
 *   B độc lập: Row1(T,T,T,T→T) vs Row5(T,F,T,T→F) → A,C,D giữ nguyên, B thay đổi, Output khác
 *   C độc lập: Row3(T,T,F,T→T) vs Row4(T,T,F,F→F) → A,B giữ nguyên, D=F; C thay đổi...
 *              Thực ra: Row1(T,T,T,T→T) vs Row4(T,T,F,F→F) không isolate C
 *              C độc lập: Row1(T,T,T,F→T) vs Row4(T,T,F,F→F) [D=F, A=T, B=T, C thay đổi]
 *   D độc lập: Row3(T,T,F,T→T) vs Row4(T,T,F,F→F) [A=T,B=T,C=F, D thay đổi, Output khác]
 *
 * TẬP MC/DC TỐI THIỂU: Row1, Row2, Row3, Row4, Row5, Row9
 * (6 test case = n+2 do kết hợp)
 * ================================================================
 */
public class VayVonMCDCTest {

    @Test(description = "MC/DC-Row1: A=T,B=T,C=T,D=T → TRUE (baseline)")
    public void testMCDC_AllTrue_Baseline() {
        // tuoi=25≥22(A=T), thu=15tr≥10tr(B=T), coTaiSan=true(C=T), tin=750≥700(D=T)
        boolean result = VayVon.duDieuKienVay(25, 15_000_000, true, 750);
        Assert.assertTrue(result, "Tat ca dieu kien deu dung phai duoc vay");
    }

    @Test(description = "MC/DC-Row2: A=T,B=T,C=T,D=F → TRUE (C=T du de C||D=T)")
    public void testMCDC_C_True_D_False() {
        // D=F nhưng C=T → C||D=T → được vay
        boolean result = VayVon.duDieuKienVay(25, 15_000_000, true, 600);
        Assert.assertTrue(result, "Co tai san bao lanh du de duoc vay du tin dung thap");
    }

    @Test(description = "MC/DC-Row3: A=T,B=T,C=F,D=T → TRUE (D=T du de C||D=T)")
    public void testMCDC_C_False_D_True() {
        // C=F nhưng D=T → C||D=T → được vay
        boolean result = VayVon.duDieuKienVay(25, 15_000_000, false, 750);
        Assert.assertTrue(result, "Tin dung tot du de duoc vay du khong co tai san");
    }

    @Test(description = "MC/DC-Row4: A=T,B=T,C=F,D=F → FALSE [doc lap C va D]")
    public void testMCDC_NoBaoDam() {
        // C=F, D=F → C||D=F → không được vay dù điều kiện cơ bản đủ
        boolean result = VayVon.duDieuKienVay(25, 15_000_000, false, 600);
        Assert.assertFalse(result, "Khong co tai san va tin dung thap: khong duoc vay");
    }

    @Test(description = "MC/DC-Row5: A=T,B=F,C=T,D=T → FALSE [doc lap B]")
    public void testMCDC_ThuNhap_DocLap() {
        // B=F (thu nhập thấp) → A&&B=F → không được vay dù bảo đảm đủ
        boolean result = VayVon.duDieuKienVay(25, 8_000_000, true, 750);
        Assert.assertFalse(result, "Thu nhap thap: khong du dieu kien co ban");
    }

    @Test(description = "MC/DC-Row9: A=F,B=T,C=T,D=T → FALSE [doc lap A]")
    public void testMCDC_Tuoi_DocLap() {
        // A=F (tuổi < 22) → A&&B=F → không được vay dù điều kiện khác đủ
        boolean result = VayVon.duDieuKienVay(20, 15_000_000, true, 750);
        Assert.assertFalse(result, "Tuoi duoi 22: khong du dieu kien co ban");
    }

    // ===== BOUNDARY TEST CASES =====

    @Test(description = "Boundary: Tuoi bien 22 → A=T")
    public void testBoundary_Tuoi22() {
        boolean result = VayVon.duDieuKienVay(22, 15_000_000, true, 750);
        Assert.assertTrue(result, "Tuoi bien 22 phai duoc vay");
    }

    @Test(description = "Boundary: Tin dung bien 700 → D=T")
    public void testBoundary_TinDung700() {
        boolean result = VayVon.duDieuKienVay(25, 15_000_000, false, 700);
        Assert.assertTrue(result, "Tin dung bien 700 phai du dieu kien");
    }

    @Test(description = "Boundary: Thu nhap bien 10tr → B=T")
    public void testBoundary_ThuNhap10Tr() {
        boolean result = VayVon.duDieuKienVay(25, 10_000_000, true, 750);
        Assert.assertTrue(result, "Thu nhap bien 10tr phai du dieu kien");
    }

    @DataProvider(name = "vayVonData")
    public Object[][] provideData() {
        return new Object[][] {
            // {tuoi, thuNhap, coTaiSan, tinDung, expected, desc}
            {25, 15_000_000.0, true,  750, true,  "Full OK"},
            {20, 15_000_000.0, true,  750, false, "Tuoi qua tre"},
            {25,  8_000_000.0, true,  750, false, "Thu nhap thap"},
            {25, 15_000_000.0, false, 600, false, "Khong co bao dam"},
            {25, 15_000_000.0, true,  600, true,  "Co tai san bao lanh"},
            {25, 15_000_000.0, false, 700, true,  "Tin dung tot"},
        };
    }

    @Test(dataProvider = "vayVonData",
          description = "DataProvider: Kiem tra nhieu truong hop vay von")
    public void testVayVon_DataProvider(int tuoi, double thu, boolean coTaiSan,
                                         int tin, boolean expected, String desc) {
        boolean result = VayVon.duDieuKienVay(tuoi, thu, coTaiSan, tin);
        Assert.assertEquals(result, expected, "[" + desc + "] Ket qua sai");
    }
}
