package framework.utils;
import com.github.javafaker.Faker;
import java.util.*;

/**
 * BÀI 4 - TestDataFactory: Tập trung logic sinh dữ liệu ngẫu nhiên.
 * Dùng Java Faker — mỗi lần gọi ra dữ liệu KHÁC NHAU.
 * Tập trung tại đây thay vì scatter khắp test class.
 */
public class TestDataFactory {
    private static final Faker faker = new Faker(new Locale("en-US"));
    private TestDataFactory() {}

    public static String randomFirstName()   { return faker.name().firstName(); }
    public static String randomLastName()    { return faker.name().lastName(); }
    public static String randomPostalCode()  { return faker.number().digits(5); }
    public static String randomEmail()       { return faker.internet().emailAddress(); }
    public static String randomPassword()    { return faker.internet().password(10,16,true,true,true); }

    /**
     * Sinh bộ dữ liệu checkout hoàn chỉnh.
     * Chạy 2 lần → 2 bộ dữ liệu KHÁC NHAU (xem log console để so sánh).
     * @return Map với key: "firstName", "lastName", "postalCode"
     */
    public static Map<String,String> randomCheckoutData() {
        String first  = randomFirstName();
        String last   = randomLastName();
        String postal = randomPostalCode();
        System.out.println("[Faker] Checkout data sinh ra: "
            + first + " " + last + " | ZIP=" + postal);
        return Map.of("firstName", first, "lastName", last, "postalCode", postal);
    }
}
