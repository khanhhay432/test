package lab3;

import org.testng.Assert;
import org.testng.annotations.*;

/**
 * BÀI 3 - Bài tập 3.2: Branch Coverage cho hàm tinhTienNuoc
 *
 * BẢNG THEO DÕI NHÁNH:
 * ┌──────┬────────┬──────────────────┬──────────────────────────────┐
 * │  TC  │  soM3  │ loaiKhachHang    │ Nhánh phủ                    │
 * ├──────┼────────┼──────────────────┼──────────────────────────────┤
 * │  TC1 │  0     │ "dan_cu"         │ N1-True                      │
 * │  TC2 │  5     │ "ho_ngheo"       │ N1-F, N2-True                │
 * │  TC3 │  8     │ "dan_cu"         │ N1-F, N2-F, N3-T, N4-True   │
 * │  TC4 │  15    │ "dan_cu"         │ N1-F, N2-F, N3-T, N4-F,N5-T│
 * │  TC5 │  25    │ "dan_cu"         │ N1-F,N2-F,N3-T,N4-F,N5-F   │
 * │  TC6 │  10    │ "kinh_doanh"     │ N1-F, N2-F, N3-False        │
 * └──────┴────────┴──────────────────┴──────────────────────────────┘
 * Branch Coverage = 12/12 = 100%
 */
public class TienNuocBranchTest {

    @Test(description = "TC01: soM3=0 → tra ve 0 [N1-True]")
    public void TC01_SoM3_Zero() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(0, "dan_cu"), 0.0, 0.01,
            "soM3=0 phai tra ve 0");
    }

    @Test(description = "TC02: ho_ngheo 5m3 → 5*5000=25000 [N2-True]")
    public void TC02_HoNgheo() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(5, "ho_ngheo"), 25000.0, 0.01,
            "Ho ngheo 5m3: 5 x 5000 = 25000");
    }

    @Test(description = "TC03: dan_cu <= 10m3 → 8*7500=60000 [N4-True]")
    public void TC03_DanCu_DuoiMuoi() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(8, "dan_cu"), 60000.0, 0.01,
            "Dan cu 8m3: 8 x 7500 = 60000");
    }

    @Test(description = "TC04: dan_cu 10<m3<=20 → 15*9900=148500 [N5-True]")
    public void TC04_DanCu_MuoiDenHaiMuoi() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(15, "dan_cu"), 148500.0, 0.01,
            "Dan cu 15m3: 15 x 9900 = 148500");
    }

    @Test(description = "TC05: dan_cu > 20m3 → 25*11400=285000 [N5-False]")
    public void TC05_DanCu_TrenHaiMuoi() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(25, "dan_cu"), 285000.0, 0.01,
            "Dan cu 25m3: 25 x 11400 = 285000");
    }

    @Test(description = "TC06: kinh_doanh → 10*22000=220000 [N3-False]")
    public void TC06_KinhDoanh() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(10, "kinh_doanh"), 220000.0, 0.01,
            "Kinh doanh 10m3: 10 x 22000 = 220000");
    }
}
