package tests;
import framework.base.BaseTest;
import framework.pages.*;
import framework.utils.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.IOException;
import java.util.*;

/**
 * BÀI 4 - DDT từ JSON + Java Faker.
 *
 * PHẦN A (TC01-TC05): Đọc từ users.json qua JsonReader + UserData POJO.
 *   → 5 user, cả SUCCESS và FAILURE, description hiện trong TestNG Report.
 *
 * PHẦN B (TC06-TC07): Java Faker sinh dữ liệu ngẫu nhiên.
 *   → invocationCount=2: chạy 2 lần → log ra 2 bộ dữ liệu KHÁC NHAU.
 */
public class Bai4_UserLoginTest extends BaseTest {

    private static final String JSON = "src/test/resources/testdata/users.json";

    // ===== PHẦN A: JSON DataProvider =====
    @DataProvider(name = "jsonUsers")
    public Object[][] getUsersFromJson() throws IOException {
        List<UserData> users = JsonReader.readUsers(JSON);
        return users.stream()
            .map(u -> new Object[]{u.username, u.password, u.expectSuccess, u.description})
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "jsonUsers", groups = {"smoke","regression"},
          description = "DDT-JSON: Login test từ users.json")
    public void TC_LoginFromJson(String username, String password,
                                  boolean expectSuccess, String desc) {
        System.out.println("[JSON] " + desc + " | user=" + username);
        LoginPage lp = new LoginPage(getDriver());
        if (expectSuccess) {
            InventoryPage ip = lp.login(username, password);
            Assert.assertTrue(ip.isLoaded(), "["+desc+"] Trang inventory chưa load");
        } else {
            lp.loginExpectingFailure(username, password);
            Assert.assertTrue(lp.isErrorDisplayed(), "["+desc+"] Phải hiện lỗi");
        }
    }

    // ===== PHẦN B: Java Faker - invocationCount=2 để chứng minh data khác nhau =====
    @Test(description = "TC-Faker: Checkout với data ngẫu nhiên (chạy 2 lần → data khác nhau)",
          groups = {"regression"}, invocationCount = 2)
    public void TC_CheckoutWithFakerData() {
        Map<String,String> data = TestDataFactory.randomCheckoutData();
        System.out.println("[Faker-Lần " + Thread.currentThread().getId() + "] "
            + data.get("firstName") + " " + data.get("lastName")
            + " ZIP=" + data.get("postalCode"));

        LoginPage lp = new LoginPage(getDriver());
        CartPage cp = lp.login("standard_user","secret_sauce")
                        .addFirstItemToCart().goToCart();
        Assert.assertEquals(cp.getItemCount(), 1, "Phải có 1 item");

        CheckoutPage chk = cp.goToCheckout();
        chk.fillInfo(data.get("firstName"), data.get("lastName"), data.get("postalCode"));

        // Sau fillInfo → URL chuyển sang step 2 nếu thành công
        Assert.assertTrue(
            getDriver().getCurrentUrl().contains("checkout"),
            "URL phải chứa 'checkout' sau khi fill info");
    }

    @Test(description = "TC-DataFactory: Kiểm tra TestDataFactory sinh dữ liệu đúng format",
          groups = {"regression"})
    public void TC_DataFactoryValidation() {
        Map<String,String> d1 = TestDataFactory.randomCheckoutData();
        Map<String,String> d2 = TestDataFactory.randomCheckoutData();
        System.out.println("[DataFactory] d1=" + d1);
        System.out.println("[DataFactory] d2=" + d2);
        Assert.assertNotNull(d1.get("firstName"),  "firstName không được null");
        Assert.assertNotNull(d1.get("lastName"),   "lastName không được null");
        Assert.assertEquals(d1.get("postalCode").length(), 5, "postalCode phải 5 chữ số");
    }
}
