package tests;
import framework.base.BaseTest;
import framework.pages.*;
import framework.utils.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.IOException;
import java.util.*;

/**
 * BÀI 7 - RefactoredTest: Code SAU KHI REFACTOR sang POM + DDT Framework.
 *
 * SO SÁNH VỚI NaiveTest (legacy/NaiveTest.java):
 * ┌─────────────────────┬────────────────────────────┬──────────────────────────────┐
 * │ Tiêu chí            │ TRƯỚC (NaiveTest)          │ SAU (RefactoredTest)         │
 * ├─────────────────────┼────────────────────────────┼──────────────────────────────┤
 * │ Khởi tạo driver     │ new ChromeDriver()         │ getDriver() từ BaseTest      │
 * │ Chờ đợi             │ Thread.sleep(2000)         │ Explicit Wait trong BasePage  │
 * │ URL                 │ Hardcode trong code        │ ConfigReader.getBaseUrl()     │
 * │ Locator             │ By.id("user-name") x10 lần │ @FindBy định nghĩa 1 lần     │
 * │ Chạy song song      │ Crash                      │ ThreadLocal → an toàn        │
 * │ Screenshot khi fail │ Không có                   │ Tự động trong BaseTest       │
 * │ Bảo trì UI đổi      │ Sửa hàng chục chỗ         │ Chỉ sửa Page Object          │
 * └─────────────────────┴────────────────────────────┴──────────────────────────────┘
 */
public class Bai7_RefactoredTest extends BaseTest {

    private static final String EXCEL = "src/test/resources/testdata/login_data.xlsx";
    private static final String JSON  = "src/test/resources/testdata/users.json";

    // ===== TEST 1: Đăng nhập - Refactored (so sánh với NaiveTest.testLoginSuccess) =====
    @Test(description = "[REFACTORED] Đăng nhập thành công - không Thread.sleep, không findElement")
    public void TC01_LoginSuccess_Refactored() {
        LoginPage lp = new LoginPage(getDriver());              // Page Object
        InventoryPage ip = lp.login("standard_user","secret_sauce"); // config tốt hơn, đây demo
        Assert.assertTrue(ip.isLoaded(), "Trang inventory chưa load");
        // KHÔNG Thread.sleep() — BasePage dùng Explicit Wait
    }

    // ===== TEST 2: Fluent Interface - hoàn toàn không có findElement trong test =====
    @Test(description = "[REFACTORED] Fluent chain: login→add→cart (0 findElement trong test)")
    public void TC02_FluentChain_Refactored() {
        CartPage cp = new LoginPage(getDriver())
            .login("standard_user","secret_sauce")
            .addFirstItemToCart()
            .goToCart();
        Assert.assertEquals(cp.getItemCount(), 1, "Giỏ hàng phải có 1 item");
    }

    // ===== TEST 3: DDT từ Excel (data tách ra khỏi code) =====
    @DataProvider(name = "excelSmoke")
    public Object[][] getExcelData() { return ExcelReader.getData(EXCEL,"SmokeCases"); }

    @Test(dataProvider = "excelSmoke",
          description = "[REFACTORED] DDT từ Excel - data không nằm trong Java code")
    public void TC03_DDT_Excel(String user, String pass, String expUrl, String desc) {
        System.out.println("[Excel-DDT] " + desc + " | user=" + user);
        LoginPage lp = new LoginPage(getDriver());
        Assert.assertTrue(lp.login(user, pass).isLoaded(), "["+desc+"] inventory chưa load");
    }

    // ===== TEST 4: DDT từ JSON =====
    @DataProvider(name = "jsonUsers")
    public Object[][] getJsonData() throws IOException {
        List<UserData> u = JsonReader.readUsers(JSON);
        return u.stream().filter(x -> x.expectSuccess)
            .map(x -> new Object[]{x.username, x.password, x.description})
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "jsonUsers",
          description = "[REFACTORED] DDT từ JSON - chỉ test user expectSuccess=true")
    public void TC04_DDT_Json(String user, String pass, String desc) {
        System.out.println("[JSON-DDT] " + desc);
        Assert.assertTrue(new LoginPage(getDriver()).login(user,pass).isLoaded(),
            "["+desc+"] phải vào được inventory");
    }

    // ===== TEST 5: Faker data (Bài 4B tích hợp vào refactored suite) =====
    @Test(description = "[REFACTORED] Faker checkout - dữ liệu khác nhau mỗi lần chạy",
          invocationCount = 2)
    public void TC05_FakerCheckout() {
        Map<String,String> data = TestDataFactory.randomCheckoutData();
        System.out.println("[Faker] " + data);
        CartPage cp = new LoginPage(getDriver())
            .login("standard_user","secret_sauce")
            .addFirstItemToCart().goToCart();
        cp.goToCheckout().fillInfo(
            data.get("firstName"), data.get("lastName"), data.get("postalCode"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("checkout"),
            "Phải ở trang checkout");
    }
}
