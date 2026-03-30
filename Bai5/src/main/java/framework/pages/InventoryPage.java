package framework.pages;
import framework.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import java.util.List;

/** BÀI 2 - InventoryPage: Page Object trang danh sách sản phẩm */
public class InventoryPage extends BasePage {

    @FindBy(css = ".inventory_list")          private WebElement inventoryList;
    @FindBy(css = ".shopping_cart_badge")     private WebElement cartBadge;
    @FindBy(css = ".inventory_item button")   private List<WebElement> addToCartButtons;
    @FindBy(css = ".shopping_cart_link")      private WebElement cartLink;

    public InventoryPage(WebDriver driver) { super(driver); }

    public boolean isLoaded() { return isElementVisible(By.cssSelector(".inventory_list")); }

    public InventoryPage addFirstItemToCart() {
        waitAndClick(addToCartButtons.get(0)); return this;
    }

    public InventoryPage addItemByName(String name) {
        for (WebElement item : driver.findElements(By.cssSelector(".inventory_item"))) {
            if (item.findElement(By.cssSelector(".inventory_item_name"))
                    .getText().equalsIgnoreCase(name)) {
                waitAndClick(item.findElement(By.cssSelector("button")));
                return this;
            }
        }
        throw new RuntimeException("[InventoryPage] Không tìm thấy: '" + name + "'");
    }

    public int getCartItemCount() {
        try { return Integer.parseInt(cartBadge.getText().trim()); }
        catch (Exception e) { return 0; }  // badge không hiện = giỏ rỗng → 0
    }

    public CartPage goToCart() { waitAndClick(cartLink); return new CartPage(driver); }
}
