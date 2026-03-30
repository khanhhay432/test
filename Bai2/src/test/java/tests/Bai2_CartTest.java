package tests;
import framework.base.BaseTest;
import framework.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/** BÀI 2 - CartTest: 5 test case giỏ hàng dùng Fluent Interface */
public class Bai2_CartTest extends BaseTest {

    @Test(description = "TC07: Thêm 1 sản phẩm → badge hiện số 1")
    public void TC07_AddOneItem() {
        InventoryPage ip = new LoginPage(getDriver()).login("standard_user","secret_sauce");
        ip.addFirstItemToCart();
        Assert.assertEquals(ip.getCartItemCount(), 1, "Badge phải = 1");
    }

    @Test(description = "TC08: Fluent chain login→add→cart → giỏ có 1 item")
    public void TC08_FluentChain() {
        CartPage cp = new LoginPage(getDriver())
            .login("standard_user","secret_sauce")
            .addFirstItemToCart()
            .goToCart();
        Assert.assertEquals(cp.getItemCount(), 1, "Giỏ hàng phải có 1 item");
        Assert.assertFalse(cp.getItemNames().isEmpty(), "Tên item không được rỗng");
    }

    @Test(description = "TC09: Xóa item → giỏ rỗng, getItemCount() = 0")
    public void TC09_RemoveItem() {
        CartPage cp = new LoginPage(getDriver())
            .login("standard_user","secret_sauce")
            .addFirstItemToCart().goToCart();
        Assert.assertEquals(cp.getItemCount(), 1, "Phải có 1 item trước khi xóa");
        cp.removeFirstItem();
        Assert.assertEquals(cp.getItemCount(), 0, "Giỏ phải rỗng sau xóa");
    }

    @Test(description = "TC10: Giỏ rỗng → getItemCount() = 0, KHÔNG throw exception")
    public void TC10_EmptyCart() {
        CartPage cp = new LoginPage(getDriver())
            .login("standard_user","secret_sauce").goToCart();
        Assert.assertEquals(cp.getItemCount(), 0, "Giỏ rỗng phải trả về 0");
    }

    @Test(description = "TC11: Checkout từ giỏ → chuyển đến trang checkout")
    public void TC11_GoToCheckout() {
        new LoginPage(getDriver())
            .login("standard_user","secret_sauce")
            .addFirstItemToCart().goToCart().goToCheckout();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("checkout"));
    }
}
