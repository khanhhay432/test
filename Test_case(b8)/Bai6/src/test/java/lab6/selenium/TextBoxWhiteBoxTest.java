package lab6.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import lab6.pages.TextBoxPage;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * BÀI 6 - Bài tập 6.2: White-Box + Selenium cho demoqa.com/text-box
 *
 * CFG PHÂN TÍCH FORM:
 *   N1: name có giá trị không?
 *   N2: email hợp lệ không? (format xxx@xxx.xxx)
 *   N3: address có giá trị không?
 *   → Submit → Output section hiển thị
 *
 * TEST CASES TỪ CFG:
 *   TC1: Điền đầy đủ tất cả → output hiển thị
 *   TC2: Chỉ name, không email, không address → output
 *   TC3: Email sai format → không submit hoặc lỗi
 *   TC4: Tất cả rỗng → output không hiển thị đúng cách
 *   TC5: Ký tự đặc biệt trong name → test boundary
 */
public class TextBoxWhiteBoxTest {

    WebDriver driver;
    TextBoxPage page;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/text-box");
        page = new TextBoxPage(driver);
    }

    @Test(description = "TC01: Dien day du thong tin → output hien thi")
    public void TC01_FullInfo() {
        page.fillAndSubmit("Nguyen Van A", "test@example.com", "123 Nguyen Hue Q1");
        Assert.assertTrue(page.isOutputDisplayed(),
            "Output phai hien thi sau khi dien day du thong tin");
        System.out.println("[TC01] Output: " + page.getOutputText());
    }

    @Test(description = "TC02: Chi name, khong email/address → output hien thi voi ten")
    public void TC02_OnlyName() {
        page.fillAndSubmit("Tran Thi B", "", "");
        Assert.assertTrue(page.isOutputDisplayed(),
            "Output phai hien thi khi chi co name");
        Assert.assertTrue(page.getOutputText().contains("Tran Thi B"),
            "Output phai chua ten da nhap");
    }

    @Test(description = "TC03: Email sai format → output field email khong hien hoac trong")
    public void TC03_InvalidEmail() {
        // Email sai format: demoqa vẫn submit nhưng output sẽ không hiện email
        page.fillAndSubmit("Test User", "email_sai_format", "");
        // Sau submit với email sai → output không hiển thị email hợp lệ
        String output = page.getOutputText();
        System.out.println("[TC03] Output khi email sai: " + output);
        // Test rằng output hiển thị nhưng không chứa email sai
        Assert.assertFalse(output.contains("email_sai_format"),
            "Output khong duoc chua email sai format");
    }

    @Test(description = "TC04: Name co ky tu dac biet → boundary test")
    public void TC04_SpecialChars() {
        page.fillAndSubmit("Nguyễn Văn Ân @#$", "valid@test.com", "Địa chỉ 123");
        Assert.assertTrue(page.isOutputDisplayed(),
            "Output phai hien thi du co ky tu dac biet");
    }

    @Test(description = "TC05: Name rat dai (boundary) → test do dai toi da")
    public void TC05_LongName() {
        String longName = "A".repeat(200);
        page.fillAndSubmit(longName, "test@test.com", "");
        // Kiểm tra page không crash
        Assert.assertNotNull(driver.getCurrentUrl(), "Trang khong duoc crash");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
