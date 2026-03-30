package framework.pages;
import framework.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

/**
 * BÀI 2 - LoginPage: Page Object trang đăng nhập saucedemo.com
 *
 * NGUYÊN TẮC POM:
 * [1] Mỗi trang web = 1 class Java riêng
 * [2] Locator @FindBy định nghĩa 1 lần — test class KHÔNG gọi findElement()
 * [3] Method trả về Page Object tiếp theo (Fluent Interface)
 * [4] KHÔNG có Assert trong Page Object — chỉ có action
 */
public class LoginPage extends BasePage {

    // ===== LOCATORS - Định nghĩa 1 lần, khi HTML đổi chỉ sửa ở đây =====
    @FindBy(id = "user-name")             private WebElement usernameField;
    @FindBy(id = "password")              private WebElement passwordField;
    @FindBy(id = "login-button")          private WebElement loginButton;
    @FindBy(css = "[data-test='error']")  private WebElement errorMessage;

    public LoginPage(WebDriver driver) { super(driver); }

    /**
     * Đăng nhập thành công → trả về InventoryPage (Fluent Interface).
     * Dùng cho: loginPage.login(u,p).addFirstItemToCart().goToCart()
     */
    public InventoryPage login(String username, String password) {
        waitAndType(usernameField, username);
        waitAndType(passwordField, password);
        waitAndClick(loginButton);
        return new InventoryPage(driver);
    }

    /**
     * Đăng nhập khi biết trước FAIL → ở lại LoginPage.
     * Dùng cho: test case sai pass, bị khóa, username rỗng, v.v.
     */
    public LoginPage loginExpectingFailure(String username, String password) {
        waitAndType(usernameField, username);
        waitAndType(passwordField, password);
        waitAndClick(loginButton);
        return this;
    }

    public String  getErrorMessage()  { return getText(errorMessage); }
    public boolean isErrorDisplayed() { return isElementVisible(By.cssSelector("[data-test='error']")); }
}
