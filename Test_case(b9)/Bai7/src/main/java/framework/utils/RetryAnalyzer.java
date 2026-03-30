package framework.utils;
import framework.config.ConfigReader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * BÀI 6 - RetryAnalyzer: Tự động chạy lại test bị fail.
 *
 * MỤC ĐÍCH: Phân biệt "lỗi thật" (bug code) với "lỗi thoáng qua"
 * (mạng chậm, server lag, race condition giao diện).
 *
 * CẤU HÌNH: retry.count trong config.properties
 *   dev:     retry.count=1 → thử lại tối đa 1 lần
 *   staging: retry.count=2 → thử lại tối đa 2 lần
 *
 * CÁCH HOẠT ĐỘNG:
 *   retry() trả về true  → TestNG chạy lại test
 *   retry() trả về false → TestNG đánh dấu FAIL, dừng
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;  // đếm số lần đã retry

    @Override
    public boolean retry(ITestResult result) {
        int max = ConfigReader.getInstance().getRetryCount();
        if (retryCount < max) {
            retryCount++;
            System.out.println("[RetryAnalyzer] Chạy lại lần " + retryCount + "/"
                             + max + " cho: " + result.getName());
            return true;   // chạy lại
        }
        System.out.println("[RetryAnalyzer] Hết lần retry ("+max+"). FAIL: "+result.getName());
        return false;      // dừng, đánh dấu FAIL
    }
}
