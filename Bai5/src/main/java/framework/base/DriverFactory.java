package framework.base;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

/** Tạo WebDriver theo tên browser. WebDriverManager tự tải driver binary. */
public class DriverFactory {
    private DriverFactory() {}
    public static WebDriver createDriver(String browser) {
        return switch (browser.toLowerCase().trim()) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions o = new ChromeOptions();
                o.addArguments("--disable-infobars","--disable-notifications");
                yield new ChromeDriver(o);
            }
            case "firefox" -> { WebDriverManager.firefoxdriver().setup(); yield new FirefoxDriver(); }
            case "edge"    -> { WebDriverManager.edgedriver().setup();    yield new EdgeDriver(); }
            default -> throw new IllegalArgumentException("Browser không hỗ trợ: " + browser);
        };
    }
}
