package tests.legacy;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

/**
 * BÀI 7 - NaiveTest: Code CŨ Lab 1 & 2 TRƯỚC KHI REFACTOR.
 * ════════════════════════════════════════════════════════════
 * ĐÂY LÀ ANTIPATTERN — CHỈ ĐỂ SO SÁNH TRƯỚC/SAU REFACTOR
 * ════════════════════════════════════════════════════════════
 *
 * 6 VẤN ĐỀ CỦA CODE NÀY (Bài 7 yêu cầu fix hết):
 *   [1] new ChromeDriver() trực tiếp → không ThreadLocal → không chạy song song
 *   [2] Thread.sleep(2000) → chờ mù, lãng phí thời gian
 *   [3] Hardcode URL "https://www.saucedemo.com" → không đổi môi trường được
 *   [4] Hardcode username/password trong test
 *   [5] driver.findElement(By.id("user-name")) lặp lại ở MỌI test → khi UI đổi sửa hàng chục chỗ
 *   [6] Không có Page Object → logic trộn lẫn với assertion
 *   [7] Không chụp ảnh khi fail → không debug được
 */
public class NaiveTest {

    private WebDriver driver; // [1] KHÔNG ThreadLocal → crash khi parallel

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();           // [1] Tạo driver trực tiếp
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com"); // [3] Hardcode URL
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
        // [7] KHÔNG chụp ảnh khi fail
    }

    @Test(description = "[NAIVE] Đăng nhập thành công")
    public void testLoginSuccess() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user"); // [4][5] hardcode data + locator
        driver.findElement(By.id("password")).sendKeys("secret_sauce");   // [4][5]
        driver.findElement(By.id("login-button")).click();
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); } // [2] Thread.sleep
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Test(description = "[NAIVE] Sai mật khẩu")
    public void testLoginWrongPassword() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user"); // [5] By.id lặp lại lần 2
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.id("login-button")).click();
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); } // [2]
        String error = driver.findElement(By.cssSelector("[data-test='error']")).getText();
        Assert.assertTrue(error.contains("Username and password do not match"));
    }

    @Test(description = "[NAIVE] Thêm sản phẩm vào giỏ")
    public void testAddToCart() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user"); // [5] lặp lại lần 3
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); } // [2]
        driver.findElements(By.cssSelector(".inventory_item button")).get(0).click();
        String badge = driver.findElement(By.cssSelector(".shopping_cart_badge")).getText();
        Assert.assertEquals(badge, "1");
    }
}
