package framework.utils;
import framework.config.ConfigReader;
import org.openqa.selenium.*;
import java.io.*; import java.nio.file.*; import java.time.*; import java.time.format.*;

/** Chụp ảnh màn hình khi test FAIL. Lưu: target/screenshots/{testName}_{timestamp}.png */
public class ScreenshotUtil {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private ScreenshotUtil() {}
    public static String capture(WebDriver driver, String testName) {
        String dir  = ConfigReader.getInstance().getScreenshotPath();
        new File(dir).mkdirs();
        String path = dir + testName + "_" + LocalDateTime.now().format(FMT) + ".png";
        try {
            File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            Files.copy(src.toPath(), Paths.get(path));
            System.out.println("[Screenshot] Đã lưu: " + path);
        } catch (IOException e) { System.err.println("[Screenshot] Lỗi: " + e.getMessage()); }
        return path;
    }
    public static byte[] captureAsBytes(WebDriver driver) {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }
}
