package lab4;

import org.testng.Assert;
import org.testng.annotations.*;

/**
 * BÀI 4 - Bài tập 4.2: Basis Path Test Cases cho tinhPhiShip
 *
 * CC = 8 → 8 Basis Path test cases
 *
 * ĐƯỜNG CƠ SỞ (Basis Paths):
 *   P1: D1-True  → Exception (trọng lượng không hợp lệ)
 *   P2: D1-F, D2-T, D3-F, D7-F  → Nội thành ≤5kg, không member
 *   P3: D1-F, D2-T, D3-T, D7-F  → Nội thành >5kg, không member
 *   P4: D1-F, D2-F, D4-T, D5-F, D7-F → Ngoại thành ≤3kg
 *   P5: D1-F, D2-F, D4-T, D5-T, D7-F → Ngoại thành >3kg
 *   P6: D1-F, D2-F, D4-F, D6-F, D7-F → Tỉnh khác ≤2kg
 *   P7: D1-F, D2-F, D4-F, D6-T, D7-F → Tỉnh khác >2kg
 *   P8: Path bất kỳ + D7-T             → laMember=true (giảm 10%)
 */
public class PhiShipBasisPathTest {

    @Test(description = "P1: Trong luong khong hop le → IllegalArgumentException")
    public void testP1_InvalidWeight() {
        Assert.assertThrows(IllegalArgumentException.class,
            () -> PhiShip.tinhPhiShip(-1, "noi_thanh", false),
            "Trong luong am phai throw IllegalArgumentException");
        Assert.assertThrows(IllegalArgumentException.class,
            () -> PhiShip.tinhPhiShip(0, "noi_thanh", false),
            "Trong luong = 0 phai throw IllegalArgumentException");
    }

    @Test(description = "P2: Noi thanh, <=5kg, khong member → phi=15000")
    public void testP2_NoiThanh_NheKhong() {
        double result = PhiShip.tinhPhiShip(3, "noi_thanh", false);
        Assert.assertEquals(result, 15000.0, 0.01,
            "Noi thanh 3kg: phi co ban = 15000");
    }

    @Test(description = "P3: Noi thanh, >5kg, khong member → phi = 15000 + (kg-5)*2000")
    public void testP3_NoiThanh_NangKhong() {
        // 7kg: 15000 + (7-5)*2000 = 15000 + 4000 = 19000
        double result = PhiShip.tinhPhiShip(7, "noi_thanh", false);
        Assert.assertEquals(result, 19000.0, 0.01,
            "Noi thanh 7kg: 15000 + 2*2000 = 19000");
    }

    @Test(description = "P4: Ngoai thanh, <=3kg, khong member → phi=25000")
    public void testP4_NgoaiThanh_NheKhong() {
        double result = PhiShip.tinhPhiShip(2, "ngoai_thanh", false);
        Assert.assertEquals(result, 25000.0, 0.01,
            "Ngoai thanh 2kg: phi co ban = 25000");
    }

    @Test(description = "P5: Ngoai thanh, >3kg, khong member → phi = 25000 + (kg-3)*3000")
    public void testP5_NgoaiThanh_NangKhong() {
        // 5kg: 25000 + (5-3)*3000 = 25000 + 6000 = 31000
        double result = PhiShip.tinhPhiShip(5, "ngoai_thanh", false);
        Assert.assertEquals(result, 31000.0, 0.01,
            "Ngoai thanh 5kg: 25000 + 2*3000 = 31000");
    }

    @Test(description = "P6: Tinh khac, <=2kg, khong member → phi=50000")
    public void testP6_TinhKhac_NheKhong() {
        double result = PhiShip.tinhPhiShip(1, "tinh_khac", false);
        Assert.assertEquals(result, 50000.0, 0.01,
            "Tinh khac 1kg: phi co ban = 50000");
    }

    @Test(description = "P7: Tinh khac, >2kg, khong member → phi = 50000 + (kg-2)*5000")
    public void testP7_TinhKhac_NangKhong() {
        // 4kg: 50000 + (4-2)*5000 = 50000 + 10000 = 60000
        double result = PhiShip.tinhPhiShip(4, "tinh_khac", false);
        Assert.assertEquals(result, 60000.0, 0.01,
            "Tinh khac 4kg: 50000 + 2*5000 = 60000");
    }

    @Test(description = "P8: laMember=true → giam 10% (noi thanh 3kg: 15000*0.9=13500)")
    public void testP8_Member_Discount() {
        // Nội thành 3kg không member = 15000 → member = 15000 * 0.9 = 13500
        double result = PhiShip.tinhPhiShip(3, "noi_thanh", true);
        Assert.assertEquals(result, 13500.0, 0.01,
            "Member giam 10%: 15000 * 0.9 = 13500");
    }

    @Test(description = "Bonus: Ngoai thanh 6kg member → (25000+9000)*0.9=30600")
    public void testBonus_NgoaiThanh_Member() {
        // 6kg ngoại thành: 25000 + (6-3)*3000 = 34000 → member: 34000*0.9 = 30600
        double result = PhiShip.tinhPhiShip(6, "ngoai_thanh", true);
        Assert.assertEquals(result, 30600.0, 0.01,
            "Ngoai thanh 6kg member: (25000+9000)*0.9=30600");
    }
}
