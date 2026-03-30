package framework.pages;
import framework.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

/** BÀI 2 - CheckoutPage: Page Object trang nhập thông tin thanh toán */
public class CheckoutPage extends BasePage {

    @FindBy(id = "first-name")  private WebElement firstNameField;
    @FindBy(id = "last-name")   private WebElement lastNameField;
    @FindBy(id = "postal-code") private WebElement postalCodeField;
    @FindBy(id = "continue")    private WebElement continueButton;

    public CheckoutPage(WebDriver driver) { super(driver); }

    public CheckoutPage fillInfo(String first, String last, String postal) {
        waitAndType(firstNameField, first);
        waitAndType(lastNameField, last);
        waitAndType(postalCodeField, postal);
        waitAndClick(continueButton);
        return new CheckoutPage(driver);
    }

    public boolean isLoaded() {
        return isElementVisible(By.id("first-name"))
            || isElementVisible(By.cssSelector(".checkout_summary_container"));
    }
}
