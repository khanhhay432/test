package lab2;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

/** BÀI 2 - CartTest: Kiểm thử giỏ hàng */
public class CartTest extends BaseTest {

    private void login() {
        WebDriverWait w = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));
        w.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")))
            .sendKeys("standard_user");
        DriverFactory.getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        DriverFactory.getDriver().findElement(By.id("login-button")).click();
        w.until(ExpectedConditions.urlContains("inventory"));
    }

    @Test(groups = {"smoke", "regression"},
          description = "TC-C01: Them san pham vao gio → badge hien so 1")
    public void testAddToCart() {
        login();
        DriverFactory.getDriver()
            .findElements(By.cssSelector(".inventory_item button")).get(0).click();
        String badge = DriverFactory.getDriver()
            .findElement(By.cssSelector(".shopping_cart_badge")).getText();
        Assert.assertEquals(badge, "1", "Badge phai hien so 1");
    }

    @Test(groups = {"regression"},
          description = "TC-C02: Vao gio hang → hien thi dung so luong")
    public void testCartCount() {
        login();
        DriverFactory.getDriver()
            .findElements(By.cssSelector(".inventory_item button")).get(0).click();
        DriverFactory.getDriver()
            .findElement(By.cssSelector(".shopping_cart_link")).click();
        int count = DriverFactory.getDriver()
            .findElements(By.cssSelector(".cart_item")).size();
        Assert.assertEquals(count, 1, "Gio hang phai co 1 item");
    }
}
