package lab2;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

/**
 * BÀI 2 - LoginTest: Phân nhóm groups (smoke, regression)
 * Dùng DriverFactory (ThreadLocal) thay vì new ChromeDriver() trực tiếp.
 */
public class LoginTest extends BaseTest {

    private void doLogin(String u, String p) {
        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))).sendKeys(u);
        DriverFactory.getDriver().findElement(By.id("password")).sendKeys(p);
        DriverFactory.getDriver().findElement(By.id("login-button")).click();
    }

    @Test(groups = {"smoke", "regression"},
          description = "TC-L01: Dang nhap hop le → inventory")
    public void testLoginSuccess() {
        doLogin("standard_user", "secret_sauce");
        new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10))
            .until(ExpectedConditions.urlContains("inventory"));
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("inventory"),
            "Phai chuyen sang inventory");
    }

    @Test(groups = {"regression"},
          description = "TC-L02: Sai mat khau → loi")
    public void testLoginWrongPassword() {
        doLogin("standard_user", "sai_pass");
        String err = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-test='error']"))).getText();
        Assert.assertTrue(err.contains("Username and password do not match"),
            "Thong bao loi khong dung: " + err);
    }

    @Test(groups = {"sanity", "regression"},
          description = "TC-L03: User bi khoa → locked out")
    public void testLoginLockedUser() {
        doLogin("locked_out_user", "secret_sauce");
        String err = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-test='error']"))).getText();
        Assert.assertTrue(err.contains("locked out"), "Phai hien thong bao locked: " + err);
    }
}
