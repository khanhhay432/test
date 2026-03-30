package lab2;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * BÀI 2 - Bài tập 2.2: DriverFactory với ThreadLocal
 *
 * ThreadLocal<WebDriver> đảm bảo mỗi thread có driver RIÊNG.
 * Tuyệt đối KHÔNG dùng static WebDriver — gây race condition khi parallel.
 *
 * CƠ CHẾ:
 *   Thread 1: initDriver() → tlDriver.set(driver1) → getDriver() = driver1
 *   Thread 2: initDriver() → tlDriver.set(driver2) → getDriver() = driver2
 *   (2 driver hoàn toàn độc lập, không ảnh hưởng nhau)
 */
public class DriverFactory {

    // ThreadLocal: mỗi thread có bản sao biến riêng
    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    /**
     * Khởi tạo WebDriver cho thread hiện tại.
     * @param browser "chrome" hoặc "firefox"
     */
    public static void initDriver(String browser) {
        WebDriver driver;
        switch (browser.toLowerCase().trim()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default: // chrome
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        tlDriver.set(driver);
        System.out.println("[DriverFactory] Thread " + Thread.currentThread().getId()
            + " khởi tạo " + browser + " driver");
    }

    /** Lấy driver của thread hiện tại */
    public static WebDriver getDriver() { return tlDriver.get(); }

    /**
     * Đóng driver và xóa khỏi ThreadLocal.
     * PHẢI gọi remove() để tránh memory leak khi chạy song song.
     */
    public static void quitDriver() {
        WebDriver d = tlDriver.get();
        if (d != null) {
            d.quit();
            tlDriver.remove(); // QUAN TRỌNG: tránh memory leak
            System.out.println("[DriverFactory] Thread "
                + Thread.currentThread().getId() + " đã quit driver");
        }
    }
}
