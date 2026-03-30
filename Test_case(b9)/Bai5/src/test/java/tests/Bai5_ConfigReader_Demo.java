package tests;
import framework.base.BaseTest;
import framework.config.ConfigReader;
import framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * BÀI 5 - ConfigReader Demo: Framework chạy được trên nhiều môi trường.
 *
 * CÁCH DEMO:
 *   1) mvn test
 *      → Log: [ConfigReader] Đang dùng môi trường: dev
 *      → TC01 PASS: explicit.wait=15
 *
 *   2) mvn test -Denv=staging
 *      → Log: [ConfigReader] Đang dùng môi trường: staging
 *      → TC02 PASS: explicit.wait=20
 *
 *   3) mvn test -Denv=prod
 *      → Runtime Exception: file config-prod.properties không tồn tại
 *
 * NGUYÊN TẮC: KHÔNG hardcode URL hay timeout ở bất kỳ đâu ngoài file .properties
 */
public class Bai5_ConfigReader_Demo extends BaseTest {

    @Test(description = "TC01: Kiểm tra ConfigReader đọc đúng env=dev (explicit.wait=15)")
    public void TC01_DevEnvironment() {
        ConfigReader cfg = ConfigReader.getInstance();
        System.out.println("===========================================");
        System.out.println("[TC01] base.url       = " + cfg.getBaseUrl());
        System.out.println("[TC01] explicit.wait  = " + cfg.getExplicitWait());
        System.out.println("[TC01] implicit.wait  = " + cfg.getImplicitWait());
        System.out.println("[TC01] retry.count    = " + cfg.getRetryCount());
        System.out.println("[TC01] screenshot.path= " + cfg.getScreenshotPath());
        System.out.println("===========================================");

        Assert.assertNotNull(cfg.getBaseUrl(), "base.url không được null");
        Assert.assertTrue(cfg.getBaseUrl().contains("saucedemo"), "URL phải là saucedemo");
        Assert.assertTrue(cfg.getExplicitWait() > 0, "explicit.wait phải > 0");
        Assert.assertEquals(cfg.getExplicitWait(), 15,
            "env=dev phải có explicit.wait=15. Nếu chạy -Denv=staging thì =20");
    }

    @Test(description = "TC02: URL từ config được dùng — không hardcode trong code")
    public void TC02_UrlFromConfig() {
        // BaseTest.setUp() đã gọi driver.get(ConfigReader.getBaseUrl())
        // Kiểm tra driver đang ở đúng URL từ config
        String currentUrl  = getDriver().getCurrentUrl();
        String configUrl   = ConfigReader.getInstance().getBaseUrl();
        System.out.println("[TC02] Config URL : " + configUrl);
        System.out.println("[TC02] Current URL: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("saucedemo"),
            "Driver phải đang ở URL từ config, không hardcode");
    }

    @Test(description = "TC03: Framework chạy bình thường với config — đăng nhập thành công")
    public void TC03_LoginWithConfigData() {
        // Test này dùng URL từ config-dev.properties (không hardcode)
        LoginPage lp = new LoginPage(getDriver());
        lp.login("standard_user", "secret_sauce");
        Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory"),
            "Đăng nhập thành công → vào inventory");
        System.out.println("[TC03] Login thành công với URL từ ConfigReader ✓");
    }

    @Test(description = "TC04: Singleton — getInstance() luôn trả về cùng 1 object")
    public void TC04_SingletonPattern() {
        ConfigReader c1 = ConfigReader.getInstance();
        ConfigReader c2 = ConfigReader.getInstance();
        Assert.assertSame(c1, c2, "Phải là cùng 1 instance (Singleton pattern)");
        System.out.println("[TC04] Singleton ✓ - c1==c2: " + (c1 == c2));
    }
}
