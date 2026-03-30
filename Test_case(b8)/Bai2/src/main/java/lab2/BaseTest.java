package lab2;

import org.testng.annotations.*;

/**
 * BÀI 2 - BaseTest: @BeforeMethod/@AfterMethod dùng DriverFactory
 */
public abstract class BaseTest {

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) {
        DriverFactory.initDriver(browser);
        DriverFactory.getDriver().get("https://www.saucedemo.com");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
