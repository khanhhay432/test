package framework.base;
import framework.config.ConfigReader;
import framework.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.time.Duration;

/**
 * BÀI 1 - BaseTest: Quản lý vòng đời WebDriver.
 * ThreadLocal<WebDriver>: mỗi thread có driver riêng → an toàn khi parallel="methods".
 * @BeforeMethod: tạo driver, mở URL.
 * @AfterMethod: chụp ảnh nếu FAIL, đóng driver.
 */
public abstract class BaseTest {
    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    protected WebDriver getDriver() { return tlDriver.get(); }

    @Parameters({"browser","env"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser, @Optional("dev") String env) {
        System.setProperty("env", env);
        ConfigReader.reset();
        WebDriver driver = DriverFactory.createDriver(browser);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(
            Duration.ofSeconds(ConfigReader.getInstance().getImplicitWait()));
        driver.get(ConfigReader.getInstance().getBaseUrl());
        tlDriver.set(driver);
        System.out.println("[BaseTest] setUp | browser="+browser+" | env="+env
            +" | thread="+Thread.currentThread().getId());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        WebDriver driver = getDriver();
        if (driver != null) {
            if (result.getStatus() == ITestResult.FAILURE) {
                String p = ScreenshotUtil.capture(driver, result.getName());
                System.out.println("[BaseTest] FAIL → Screenshot: " + p);
            }
            driver.quit();
            tlDriver.remove(); // QUAN TRỌNG: tránh memory leak khi chạy song song
            System.out.println("[BaseTest] tearDown | " + result.getName()
                + " | " + (result.getStatus()==1?"PASS ✓":"FAIL ✗"));
        }
    }
}
