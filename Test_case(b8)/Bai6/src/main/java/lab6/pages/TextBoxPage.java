package lab6.pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

/**
 * BÀI 6 - Bài tập 6.2: TextBoxPage (Page Object Model)
 * Website: https://demoqa.com/text-box
 */
public class TextBoxPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "userName")        private WebElement nameField;
    @FindBy(id = "userEmail")       private WebElement emailField;
    @FindBy(id = "currentAddress")  private WebElement currentAddressField;
    @FindBy(id = "submit")          private WebElement submitBtn;
    @FindBy(id = "output")          private WebElement outputSection;

    public TextBoxPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void fillAndSubmit(String name, String email, String address) {
        if (name != null && !name.isEmpty())
            wait.until(ExpectedConditions.visibilityOf(nameField)).sendKeys(name);
        if (email != null && !email.isEmpty())
            emailField.sendKeys(email);
        if (address != null && !address.isEmpty())
            currentAddressField.sendKeys(address);
        // Scroll đến nút submit (nằm dưới viewport)
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView(true);", submitBtn);
        submitBtn.click();
    }

    public boolean isOutputDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(outputSection)).isDisplayed();
        } catch (Exception e) { return false; }
    }

    public String getOutputText() {
        try { return outputSection.getText(); }
        catch (Exception e) { return ""; }
    }
}
