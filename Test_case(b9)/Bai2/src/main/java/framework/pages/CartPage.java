package framework.pages;
import framework.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import java.util.*;
import java.util.stream.Collectors;

/** BÀI 2 - CartPage: Page Object trang giỏ hàng */
public class CartPage extends BasePage {

    @FindBy(css = ".cart_item")               private List<WebElement> cartItems;
    @FindBy(css = ".cart_item button[id^='remove']") private List<WebElement> removeButtons;
    @FindBy(css = ".inventory_item_name")     private List<WebElement> itemNameEls;
    @FindBy(id = "checkout")                  private WebElement checkoutButton;

    public CartPage(WebDriver driver) { super(driver); }

    /** Số item trong giỏ — trả về 0 nếu rỗng, KHÔNG throw exception */
    public int getItemCount() {
        try { return cartItems.size(); } catch (Exception e) { return 0; }
    }

    public CartPage removeFirstItem() {
        if (!removeButtons.isEmpty()) waitAndClick(removeButtons.get(0));
        else System.out.println("[CartPage] Giỏ rỗng, không có gì để xóa");
        return this;
    }

    public CheckoutPage goToCheckout() { waitAndClick(checkoutButton); return new CheckoutPage(driver); }

    public List<String> getItemNames() {
        return itemNameEls.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }

    public boolean isLoaded() { return isElementVisible(By.cssSelector(".cart_list")); }
}
