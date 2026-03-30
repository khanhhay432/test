package tests;
import framework.base.BaseTest;
import framework.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * BÀI 2 - LoginTest: 6 test case đăng nhập dùng Page Object.
 * NGUYÊN TẮC: KHÔNG có driver.findElement() hay By.id() trong class này.
 * FLUENT INTERFACE: loginPage.login(u,p).addFirstItemToCart().goToCart()
 */
public class Bai2_LoginTest extends BaseTest {

    @Test(description = "TC01: Đăng nhập hợp lệ → vào trang inventory")
    public void TC01_LoginSuccess() {
        LoginPage lp = new LoginPage(getDriver());
        InventoryPage ip = lp.login("standard_user", "secret_sauce");
        Assert.assertTrue(ip.isLoaded(), "Trang inventory chưa load");
        Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory"), "URL sai");
    }

    @Test(description = "TC02: Sai mật khẩu → hiện thông báo lỗi")
    public void TC02_WrongPassword() {
        LoginPage lp = new LoginPage(getDriver());
        lp.loginExpectingFailure("standard_user", "wrong_pass");
        Assert.assertTrue(lp.isErrorDisplayed(), "Phải hiện lỗi");
        Assert.assertTrue(lp.getErrorMessage().contains("Username and password do not match"));
    }

    @Test(description = "TC03: Tài khoản bị khóa → thông báo locked out")
    public void TC03_LockedUser() {
        LoginPage lp = new LoginPage(getDriver());
        lp.loginExpectingFailure("locked_out_user", "secret_sauce");
        Assert.assertTrue(lp.isErrorDisplayed(), "Phải hiện lỗi locked");
        Assert.assertTrue(lp.getErrorMessage().contains("locked out"));
    }

    @Test(description = "TC04: Username rỗng → thông báo yêu cầu username")
    public void TC04_EmptyUsername() {
        LoginPage lp = new LoginPage(getDriver());
        lp.loginExpectingFailure("", "secret_sauce");
        Assert.assertTrue(lp.isErrorDisplayed());
        Assert.assertTrue(lp.getErrorMessage().contains("Username is required"));
    }

    @Test(description = "TC05: Password rỗng → thông báo yêu cầu password")
    public void TC05_EmptyPassword() {
        LoginPage lp = new LoginPage(getDriver());
        lp.loginExpectingFailure("standard_user", "");
        Assert.assertTrue(lp.isErrorDisplayed());
        Assert.assertTrue(lp.getErrorMessage().contains("Password is required"));
    }

    @Test(description = "TC06: XSS injection → không bị tấn công, hiện lỗi bình thường")
    public void TC06_XssInjection() {
        LoginPage lp = new LoginPage(getDriver());
        lp.loginExpectingFailure("<script>alert(1)</script>", "secret_sauce");
        Assert.assertTrue(lp.isErrorDisplayed(), "Phải hiện lỗi khi nhập XSS");
        Assert.assertTrue(getDriver().getCurrentUrl().contains("saucedemo"), "URL không được thay đổi");
    }
}
