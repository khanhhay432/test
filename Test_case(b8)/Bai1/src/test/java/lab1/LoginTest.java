package lab1;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

/**
 * BÀI 1 - Bài tập 1.2: Kiểm thử Form Đăng Nhập saucedemo.com
 *
 * 5 TEST CASE:
 *   TC01: Đăng nhập hợp lệ → vào /inventory.html
 *   TC02: Sai mật khẩu → thông báo lỗi
 *   TC03: Username rỗng → "Username is required"
 *   TC04: Password rỗng → "Password is required"
 *   TC05: User bị khóa → "Sorry, this user has been locked out"
 *
 * QUY TẮC:
 *   - Dùng Explicit Wait (WebDriverWait) — KHÔNG Thread.sleep()
 *   - Mỗi Assert có thông báo lỗi rõ ràng (tham số thứ 3)
 *   - @BeforeMethod mở browser mới, @AfterMethod đóng
 */
public class LoginTest {

    WebDriver driver;
    WebDriverWait wait;

    private static final String BASE_URL   = "https://www.saucedemo.com";
    private static final String VALID_USER = "standard_user";
    private static final String VALID_PASS = "secret_sauce";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        // Explicit Wait 10 giây — không Thread.sleep()
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /** Helper: nhập username + password + click login */
    private void doLogin(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")))
            .sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }

    /** Helper: lấy text thông báo lỗi */
    private String getErrorMessage() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(By.cssSelector("[data-test='error']")))
            .getText();
    }

    /**
     * TC01: Đăng nhập với user/pass hợp lệ → chuyển sang /inventory.html
     */
    @Test(description = "TC01: Dang nhap hop le - phai chuyen sang /inventory.html",
          groups = {"smoke", "regression"})
    public void testLoginSuccess() {
        doLogin(VALID_USER, VALID_PASS);

        // Chờ URL đổi sang inventory
        wait.until(ExpectedConditions.urlContains("inventory"));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory"),
            "TC01 FAIL: Sau dang nhap hop le phai chuyen sang /inventory.html. URL hien tai: " + currentUrl);
        System.out.println("[TC01] Login thanh cong → " + currentUrl + " ✓");
    }

    /**
     * TC02: Nhập sai mật khẩu → thông báo lỗi xuất hiện
     */
    @Test(description = "TC02: Sai mat khau - hien thong bao loi",
          groups = {"regression"})
    public void testLoginWrongPassword() {
        doLogin(VALID_USER, "sai_mat_khau_123");

        String error = getErrorMessage();
        Assert.assertTrue(error.contains("Username and password do not match"),
            "TC02 FAIL: Thong bao loi sai mat khau khong dung. Actual: " + error);
        System.out.println("[TC02] Loi sai mat khau: " + error + " ✓");
    }

    /**
     * TC03: Bỏ trống username → "Username is required"
     */
    @Test(description = "TC03: Username trong - hien 'Username is required'",
          groups = {"regression"})
    public void testLoginEmptyUsername() {
        doLogin("", VALID_PASS);

        String error = getErrorMessage();
        Assert.assertTrue(error.contains("Username is required"),
            "TC03 FAIL: Phai hien 'Username is required'. Actual: " + error);
        System.out.println("[TC03] Loi username trong: " + error + " ✓");
    }

    /**
     * TC04: Bỏ trống password → "Password is required"
     */
    @Test(description = "TC04: Password trong - hien 'Password is required'",
          groups = {"regression"})
    public void testLoginEmptyPassword() {
        doLogin(VALID_USER, "");

        String error = getErrorMessage();
        Assert.assertTrue(error.contains("Password is required"),
            "TC04 FAIL: Phai hien 'Password is required'. Actual: " + error);
        System.out.println("[TC04] Loi password trong: " + error + " ✓");
    }

    /**
     * TC05: User bị khóa → "Sorry, this user has been locked out"
     */
    @Test(description = "TC05: User bi khoa - hien thong bao locked out",
          groups = {"sanity", "regression"})
    public void testLoginLockedUser() {
        doLogin("locked_out_user", VALID_PASS);

        String error = getErrorMessage();
        Assert.assertTrue(error.contains("Sorry, this user has been locked out"),
            "TC05 FAIL: Phai hien thong bao locked out. Actual: " + error);
        System.out.println("[TC05] User bi khoa: " + error + " ✓");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
