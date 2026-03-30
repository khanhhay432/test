package tests;
import framework.base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

/**
 * BÀI 1 - Kiểm chứng BasePage + BaseTest hoạt động đúng.
 *
 * CÁCH CHẠY:
 *   mvn test                    → dev, explicit.wait=15
 *   mvn test -Denv=staging      → staging, explicit.wait=20
 *
 * KẾT QUẢ MONG ĐỢI:
 *   TC01-TC04: PASS
 *   TC05: FAIL có ý thức → kiểm tra file ảnh trong target/screenshots/
 *   TC06: PASS (3 thread chạy song song không lỗi)
 */
public class Bai1_BaseTest_Verify extends BaseTest {

    @Test(description = "TC01: BaseTest mở đúng URL từ config-dev.properties")
    public void TC01_BaseUrlLoaded() {
        String url = getDriver().getCurrentUrl();
        System.out.println("[TC01] URL: " + url);
        Assert.assertTrue(url.contains("saucedemo"), "URL phải chứa 'saucedemo'");
    }

    @Test(description = "TC02: waitAndClick - click login button không có dữ liệu → lỗi")
    public void TC02_WaitAndClick() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        // Đây là waitAndClick() trong BasePage
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button"))).click();
        boolean err = getDriver().findElement(By.cssSelector("[data-test='error']")).isDisplayed();
        Assert.assertTrue(err, "Phải hiện lỗi sau click Login không nhập dữ liệu");
        System.out.println("[TC02] waitAndClick ✓");
    }

    @Test(description = "TC03: waitAndType - nhập text vào input, clear trước khi type")
    public void TC03_WaitAndType() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        field.clear();
        field.sendKeys("standard_user");
        Assert.assertEquals(field.getAttribute("value"), "standard_user",
            "Giá trị input phải là 'standard_user'");
        System.out.println("[TC03] waitAndType ✓");
    }

    @Test(description = "TC04: isElementVisible - trả về false (không throw exception) khi element không tồn tại")
    public void TC04_IsElementVisible_NoException() {
        // Element TỒN TẠI → true
        boolean loginVisible = getDriver().findElement(By.id("login-button")).isDisplayed();
        Assert.assertTrue(loginVisible, "Login button phải visible");
        // Element KHÔNG TỒN TẠI → false (không throw exception)
        boolean fakeVisible = false;
        try {
            getDriver().findElement(By.id("ELEMENT_KHONG_TON_TAI")).isDisplayed();
            fakeVisible = true;
        } catch (NoSuchElementException e) { fakeVisible = false; }
        Assert.assertFalse(fakeVisible, "Element không tồn tại phải trả về false");
        System.out.println("[TC04] isElementVisible ✓");
    }

    @Test(description = "TC05: [CỐ Ý FAIL] Kiểm tra screenshot tự động tạo khi test fail")
    public void TC05_ScreenshotOnFail_INTENTIONAL() {
        // Sau khi chạy: kiểm tra target/screenshots/TC05_ScreenshotOnFail_INTENTIONAL_*.png
        System.out.println("[TC05] Test này cố ý FAIL để tạo screenshot → kiểm tra target/screenshots/");
        Assert.fail("CỐ Ý FAIL - Kiểm tra file ảnh trong target/screenshots/");
    }

    @Test(description = "TC06: ThreadLocal - mỗi thread có WebDriver riêng (chạy với parallel thread-count=3)")
    public void TC06_ThreadLocalDriver() {
        long threadId = Thread.currentThread().getId();
        WebDriver driver = getDriver();
        Assert.assertNotNull(driver, "Thread "+threadId+" phải có driver không null");
        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo"),
            "Thread "+threadId+" phải ở saucedemo.com");
        System.out.println("[TC06] Thread "+threadId+" | driver="+driver.getClass().getSimpleName()+" ✓");
    }
}
