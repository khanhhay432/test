package lab1;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

/**
 * BÀI 1 - Bài tập 1.1: Kiểm thử tiêu đề và URL trang web
 * Website: https://www.saucedemo.com
 *
 * CÁCH CHẠY: mvn test
 * KẾT QUẢ: 4 test PASS
 */
public class TitleTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // WebDriverManager tự tải chromedriver phù hợp — không cần cài tay
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com");
    }

    /**
     * TC01: Kiểm tra tiêu đề trang đúng là "Swag Labs"
     * Expected: getTitle() == "Swag Labs"
     */
    @Test(description = "TC01: Kiem thu tieu de trang chu - phai la 'Swag Labs'")
    public void testTitle() {
        String expectedTitle = "Swag Labs";
        String actualTitle   = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle,
            "Tieu de trang khong dung! Expected='" + expectedTitle
            + "' Actual='" + actualTitle + "'");
        System.out.println("[TC01] Title: " + actualTitle + " ✓");
    }

    /**
     * TC02: Kiểm tra URL hợp lệ — phải chứa "saucedemo"
     */
    @Test(description = "TC02: Kiem thu URL trang chu - phai chua 'saucedemo'")
    public void testURL() {
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("saucedemo"),
            "URL khong hop le! URL hien tai: " + actualUrl);
        System.out.println("[TC02] URL: " + actualUrl + " ✓");
    }

    /**
     * TC03: Kiểm tra page source có chứa nội dung quan trọng
     * Page source phải chứa "Swag Labs" và "username"
     */
    @Test(description = "TC03: Kiem thu page source chua noi dung can thiet")
    public void testPageSource() {
        String source = driver.getPageSource();
        Assert.assertTrue(source.contains("Swag Labs"),
            "Page source phai chua 'Swag Labs'");
        Assert.assertTrue(source.contains("username"),
            "Page source phai chua field 'username'");
        Assert.assertTrue(source.contains("password"),
            "Page source phai chua field 'password'");
        System.out.println("[TC03] Page source hop le ✓");
    }

    /**
     * TC04: Kiểm tra form đăng nhập có hiển thị hay không
     * Các element: username field, password field, login button phải visible
     */
    @Test(description = "TC04: Kiem thu form dang nhap hien thi day du")
    public void testLoginFormDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Username field phải visible
        WebElement usernameField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        Assert.assertTrue(usernameField.isDisplayed(),
            "Username field phai hien thi");

        // Password field phải visible
        WebElement passwordField = driver.findElement(By.id("password"));
        Assert.assertTrue(passwordField.isDisplayed(),
            "Password field phai hien thi");

        // Login button phải visible và clickable
        WebElement loginBtn = driver.findElement(By.id("login-button"));
        Assert.assertTrue(loginBtn.isDisplayed(),
            "Login button phai hien thi");
        Assert.assertTrue(loginBtn.isEnabled(),
            "Login button phai duoc phep click");

        System.out.println("[TC04] Form dang nhap hien thi day du ✓");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
