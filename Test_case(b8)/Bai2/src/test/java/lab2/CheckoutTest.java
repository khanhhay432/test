package lab2;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

/** BÀI 2 - CheckoutTest: Kiểm thử checkout */
public class CheckoutTest extends BaseTest {

    private void loginAndAddItem() {
        WebDriverWait w = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));
        w.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")))
            .sendKeys("standard_user");
        DriverFactory.getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        DriverFactory.getDriver().findElement(By.id("login-button")).click();
        w.until(ExpectedConditions.urlContains("inventory"));
        DriverFactory.getDriver()
            .findElements(By.cssSelector(".inventory_item button")).get(0).click();
        DriverFactory.getDriver()
            .findElement(By.cssSelector(".shopping_cart_link")).click();
    }

    @Test(groups = {"smoke", "regression"},
          description = "TC-K01: Click Checkout → chuyen sang trang checkout")
    public void testGoToCheckout() {
        loginAndAddItem();
        DriverFactory.getDriver().findElement(By.id("checkout")).click();
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("checkout"),
            "Phai chuyen sang trang checkout");
    }

    @Test(groups = {"regression"},
          description = "TC-K02: Bo trong First Name → hien loi")
    public void testCheckoutEmptyFirstName() {
        loginAndAddItem();
        DriverFactory.getDriver().findElement(By.id("checkout")).click();
        new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10))
            .until(ExpectedConditions.urlContains("checkout-step-one"));
        DriverFactory.getDriver().findElement(By.id("last-name")).sendKeys("Nguyen");
        DriverFactory.getDriver().findElement(By.id("postal-code")).sendKeys("70000");
        DriverFactory.getDriver().findElement(By.id("continue")).click();
        String err = DriverFactory.getDriver()
            .findElement(By.cssSelector("[data-test='error']")).getText();
        Assert.assertTrue(err.contains("First Name"), "Phai hien loi First Name: " + err);
    }
}
