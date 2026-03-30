package framework.base;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * BÀI 1 - BasePage: Lớp cha cho tất cả Page Object.
 * Đóng gói 7 thao tác WebDriver với Explicit Wait tích hợp.
 * KHÔNG dùng Thread.sleep() - dùng WebDriverWait.
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /** METHOD 1: Chờ element clickable rồi click - tránh ElementNotInteractableException */
    protected void waitAndClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    /** METHOD 2: Xóa nội dung cũ, gõ text mới - tránh dữ liệu bị nối thêm */
    protected void waitAndType(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    /** METHOD 3: Lấy text đã trim whitespace */
    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText().trim();
    }

    /** METHOD 4: Kiểm tra visibility - KHÔNG throw exception nếu element không tồn tại.
     *  Xử lý cả StaleElementReferenceException khi DOM re-render sau AJAX. */
    protected boolean isElementVisible(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false; // trả về false thay vì crash
        }
    }

    /** METHOD 5: Cuộn trang đến element - xử lý element nằm ngoài viewport */
    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /** METHOD 6: Chờ document.readyState == "complete" - dùng sau điều hướng */
    protected void waitForPageLoad() {
        wait.until(driver ->
            ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
    }

    /** METHOD 7: Lấy giá trị HTML attribute (href, value, placeholder, data-*) */
    protected String getAttribute(WebElement element, String attr) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getAttribute(attr);
    }
}
