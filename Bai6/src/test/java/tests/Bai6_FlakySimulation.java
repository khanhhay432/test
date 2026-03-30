package tests;
import framework.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * BÀI 6 - FlakySimulationTest: Mô phỏng test không ổn định + kiểm chứng RetryAnalyzer.
 *
 * KỊCH BẢN:
 *   testFlakyScenario() fail 2 lần đầu, pass lần thứ 3.
 *   Với retry.count=2 → TestNG chạy lại 2 lần → kết quả cuối: PASS.
 *
 * CÁCH KIỂM CHỨNG:
 *   1) retry.count=2 (trong config-dev.properties):
 *      → Lần 1: FAIL → RETRY
 *      → Lần 2: FAIL → RETRY
 *      → Lần 3: PASS ✓
 *      → TestNG Report: 1 test PASS (với 2 lần retry)
 *
 *   2) Đổi retry.count=0 (trong config-dev.properties):
 *      → Lần 1: FAIL → dừng ngay
 *      → TestNG Report: 1 test FAIL
 *      → So sánh 2 kết quả để thấy sự khác biệt
 */
public class Bai6_FlakySimulation extends BaseTest {

    /** Biến static: persist qua các lần retry trong cùng JVM. Reset khi mvn test mới. */
    private static int callCount = 0;

    @Test(description = "TC-Flaky: Fail 2 lần đầu, pass lần thứ 3 (cần retry.count=2)")
    public void testFlakyScenario() {
        callCount++;
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║ [FlakyTest] Lần chạy thứ: " + callCount + " / 3       ║");
        System.out.println("╚═══════════════════════════════════════╝");

        if (callCount <= 2) {
            // Mô phỏng lỗi thoáng qua (mạng chậm, server lag)
            System.out.println("[FlakyTest] Mô phỏng lỗi tạm thời lần " + callCount);
            Assert.fail("Mô phỏng lỗi mạng/server lag — lần " + callCount
                      + ". RetryAnalyzer sẽ chạy lại.");
        }

        // Lần 3: pass
        System.out.println("[FlakyTest] ✅ PASS ở lần " + callCount);
        Assert.assertTrue(true, "Test pass ở lần thứ " + callCount);
    }

    @Test(description = "TC-AlwaysPass: Kiểm chứng Retry không ảnh hưởng test bình thường")
    public void testAlwaysPass() {
        System.out.println("[AlwaysPass] Test luôn pass — retry không được trigger");
        Assert.assertTrue(true, "Test này luôn PASS");
    }
}
