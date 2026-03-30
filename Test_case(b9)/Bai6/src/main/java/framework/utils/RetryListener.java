package framework.utils;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import java.lang.reflect.*;

/**
 * BÀI 6 - RetryListener: Áp dụng RetryAnalyzer tự động cho TẤT CẢ @Test.
 *
 * Không cần thêm retryAnalyzer=RetryAnalyzer.class vào từng @Test.
 * Chỉ cần đăng ký trong testng.xml:
 *   <listeners>
 *     <listener class-name="framework.utils.RetryListener"/>
 *   </listeners>
 *
 * Mỗi lần TestNG scan một @Test annotation → transform() được gọi
 * → tự động gán RetryAnalyzer.
 */
public class RetryListener implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
