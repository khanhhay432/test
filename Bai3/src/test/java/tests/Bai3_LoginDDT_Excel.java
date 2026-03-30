package tests;
import framework.base.BaseTest;
import framework.pages.*;
import framework.utils.ExcelReader;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * BÀI 3 - Data-Driven Testing với Excel (3 sheet).
 *
 * FILE DATA: src/test/resources/testdata/login_data.xlsx
 *   - Sheet "SmokeCases"    (3 dòng):  happy path, groups="smoke"
 *   - Sheet "NegativeCases" (5 dòng):  đăng nhập thất bại, groups="regression"
 *   - Sheet "BoundaryCases" (4 dòng):  dữ liệu biên, groups="regression"
 *
 * KHI THÊM DÒNG MỚI VÀO EXCEL → TEST TỰ CHẠY THÊM, KHÔNG SỬA JAVA.
 * TÊN TEST TRONG REPORT = description từ cột cuối của Excel.
 */
public class Bai3_LoginDDT_Excel extends BaseTest {

    private static final String EXCEL = "src/test/resources/testdata/login_data.xlsx";

    // ===== SHEET 1: SmokeCases (happy path) =====
    @DataProvider(name = "smokeCases")
    public Object[][] getSmokeCases() { return ExcelReader.getData(EXCEL, "SmokeCases"); }

    @Test(dataProvider = "smokeCases", groups = {"smoke","regression"},
          description = "DDT-Smoke: Đăng nhập hợp lệ từ Excel")
    public void testSmoke(String username, String password, String expectedUrl, String desc) {
        System.out.println("[Smoke] " + desc + " | user=" + username);
        LoginPage lp = new LoginPage(getDriver());
        InventoryPage ip = lp.login(username, password);
        Assert.assertTrue(ip.isLoaded(), "["+desc+"] Trang inventory chưa load");
        Assert.assertTrue(getDriver().getCurrentUrl().contains(expectedUrl),
            "["+desc+"] URL không chứa: " + expectedUrl);
    }

    // ===== SHEET 2: NegativeCases (đăng nhập thất bại) =====
    @DataProvider(name = "negativeCases")
    public Object[][] getNegativeCases() { return ExcelReader.getData(EXCEL, "NegativeCases"); }

    @Test(dataProvider = "negativeCases", groups = {"regression"},
          description = "DDT-Negative: Đăng nhập thất bại từ Excel")
    public void testNegative(String username, String password, String expectedErr, String desc) {
        System.out.println("[Negative] " + desc + " | user='" + username + "'");
        LoginPage lp = new LoginPage(getDriver());
        lp.loginExpectingFailure(username, password);
        Assert.assertTrue(lp.isErrorDisplayed(), "["+desc+"] Phải hiện lỗi");
        Assert.assertTrue(lp.getErrorMessage().contains(expectedErr),
            "["+desc+"] Lỗi không khớp. Expected='"+expectedErr+"' Actual='"+lp.getErrorMessage()+"'");
    }

    // ===== SHEET 3: BoundaryCases (dữ liệu biên) =====
    @DataProvider(name = "boundaryCases")
    public Object[][] getBoundaryCases() { return ExcelReader.getData(EXCEL, "BoundaryCases"); }

    @Test(dataProvider = "boundaryCases", groups = {"regression"},
          description = "DDT-Boundary: Dữ liệu biên từ Excel")
    public void testBoundary(String username, String password, String expectedErr, String desc) {
        System.out.println("[Boundary] " + desc);
        LoginPage lp = new LoginPage(getDriver());
        lp.loginExpectingFailure(username, password);
        Assert.assertTrue(lp.isErrorDisplayed(),
            "["+desc+"] Phải hiện lỗi với dữ liệu biên");
    }
}
