package lab7;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.*;

/**
 * BÀI 7 - Bài tập 7.1 + 7.2: OrderProcessor Basis Path + Allure
 *
 * CC = 9 → 9 Basis Path test cases
 *
 * ĐƯỜNG CƠ SỞ:
 *   P1: D1-True → Exception (giỏ hàng rỗng)
 *   P2: D1-F, D2-F, D5-F, D6-F, D7-T, D8-T → Không coupon, không member, ship online
 *   P3: D1-F, D2-F, D5-F, D6-F, D7-T, D8-F → Không coupon, không member, ship COD
 *   P4: D1-F, D2-F, D5-F, D6-F, D7-F       → Không coupon, không member, không ship
 *   P5: D1-F, D2-T, D3-T, D5-F, D6-F, D7-T, D8-T → SALE10, không member
 *   P6: D1-F, D2-T, D3-F, D4-T, ... → SALE20
 *   P7: D1-F, D2-T, D3-F, D4-F → Coupon không hợp lệ → Exception
 *   P8: D1-F, D2-F, D5-T, D7-T, D8-T → GOLD member
 *   P9: D1-F, D2-F, D6-T, D7-T, D8-T → PLATINUM member
 */
@Epic("Lab 8 - White Box Testing")
@Feature("Order Processor - Basis Path Testing")
public class OrderProcessorTest {

    private OrderProcessor processor;

    @BeforeClass
    public void init() { processor = new OrderProcessor(); }

    private List<Item> items(double... prices) {
        List<Item> list = new ArrayList<>();
        for (double p : prices) list.add(new Item("Item", p));
        return list;
    }

    @Test(description = "P1: Gio hang rong → IllegalArgumentException")
    @Story("UC-D1: Gio hang rong")
    @Severity(SeverityLevel.CRITICAL)
    public void testP1_EmptyCart() {
        Assert.assertThrows(IllegalArgumentException.class,
            () -> processor.calculateTotal(null, "", "NONE", "COD"),
            "Gio hang null phai throw exception");
        Assert.assertThrows(IllegalArgumentException.class,
            () -> processor.calculateTotal(new ArrayList<>(), "", "NONE", "COD"),
            "Gio hang rong phai throw exception");
    }

    @Test(description = "P2: Khong coupon, khong member, ship online (total<500k)")
    @Story("UC-D2-F-D7-T-D8-T: Ship online")
    @Severity(SeverityLevel.NORMAL)
    public void testP2_NoCoupon_NoMember_ShipOnline() {
        // subtotal=200k < 500k → ship online +30k = 230k
        double result = processor.calculateTotal(
            items(200_000), "", "NONE", "BANK_TRANSFER");
        Assert.assertEquals(result, 230_000.0, 0.01,
            "200k + 30k ship online = 230000");
    }

    @Test(description = "P3: Khong coupon, khong member, ship COD (total<500k)")
    @Story("UC-D7-T-D8-F: Ship COD")
    @Severity(SeverityLevel.NORMAL)
    public void testP3_NoCoupon_NoMember_ShipCOD() {
        // subtotal=200k < 500k → COD +20k = 220k
        double result = processor.calculateTotal(
            items(200_000), "", "NONE", "COD");
        Assert.assertEquals(result, 220_000.0, 0.01,
            "200k + 20k COD = 220000");
    }

    @Test(description = "P4: Khong coupon, khong member, total>=500k → mien ship")
    @Story("UC-D7-F: Mien ship")
    @Severity(SeverityLevel.NORMAL)
    public void testP4_NoCoupon_NoMember_FreeShip() {
        // subtotal=600k >= 500k → không cộng ship
        double result = processor.calculateTotal(
            items(600_000), "", "NONE", "COD");
        Assert.assertEquals(result, 600_000.0, 0.01,
            "600k >= 500k: mien phi ship");
    }

    @Test(description = "P5: SALE10 coupon → giam 10%, ship online")
    @Story("UC-D3-T: SALE10 coupon")
    @Severity(SeverityLevel.NORMAL)
    public void testP5_SALE10_ShipOnline() {
        // subtotal=300k, SALE10: -30k = 270k < 500k → +30k ship = 300k
        double result = processor.calculateTotal(
            items(300_000), "SALE10", "NONE", "BANK");
        Assert.assertEquals(result, 300_000.0, 0.01,
            "300k - 10% + 30k ship = 300000");
    }

    @Test(description = "P6: SALE20 coupon → giam 20%")
    @Story("UC-D4-T: SALE20 coupon")
    @Severity(SeverityLevel.NORMAL)
    public void testP6_SALE20() {
        // subtotal=300k, SALE20: -60k = 240k < 500k → COD +20k = 260k
        double result = processor.calculateTotal(
            items(300_000), "SALE20", "NONE", "COD");
        Assert.assertEquals(result, 260_000.0, 0.01,
            "300k - 20% + 20k COD = 260000");
    }

    @Test(description = "P7: Coupon khong hop le → IllegalArgumentException")
    @Story("UC-D4-F: Coupon invalid")
    @Severity(SeverityLevel.CRITICAL)
    public void testP7_InvalidCoupon() {
        Assert.assertThrows(IllegalArgumentException.class,
            () -> processor.calculateTotal(items(300_000), "INVALID", "NONE", "COD"),
            "Coupon sai phai throw exception");
    }

    @Test(description = "P8: GOLD member → giam them 5%")
    @Story("UC-D5-T: GOLD member")
    @Severity(SeverityLevel.NORMAL)
    public void testP8_GoldMember() {
        // subtotal=400k, GOLD: -20k = 380k < 500k → +30k ship = 410k
        double result = processor.calculateTotal(
            items(400_000), "", "GOLD", "BANK");
        Assert.assertEquals(result, 410_000.0, 0.01,
            "400k * 0.95 + 30k = 410000");
    }

    @Test(description = "P9: PLATINUM member → giam them 10%")
    @Story("UC-D6-T: PLATINUM member")
    @Severity(SeverityLevel.NORMAL)
    public void testP9_PlatinumMember() {
        // subtotal=400k, PLATINUM: -40k = 360k < 500k → +30k ship = 390k
        double result = processor.calculateTotal(
            items(400_000), "", "PLATINUM", "BANK");
        Assert.assertEquals(result, 390_000.0, 0.01,
            "400k * 0.90 + 30k = 390000");
    }

    // ===== MC/DC cho D2 && D3 (coupon) =====
    @Test(description = "MC/DC-D2: couponCode null → D2=False")
    @Story("MC/DC Analysis")
    public void testMCDC_D2_Null() {
        double r = processor.calculateTotal(items(200_000), null, "NONE", "COD");
        Assert.assertEquals(r, 220_000.0, 0.01, "coupon null: khong giam gia");
    }

    @Test(description = "MC/DC-D3: SALE10 vs SALE20 → D3 doc lap")
    @Story("MC/DC Analysis")
    public void testMCDC_D3_vs_D4() {
        double r10 = processor.calculateTotal(items(300_000), "SALE10", "NONE", "COD");
        double r20 = processor.calculateTotal(items(300_000), "SALE20", "NONE", "COD");
        Assert.assertTrue(r20 < r10,
            "SALE20 phai giam nhieu hon SALE10: r20=" + r20 + " r10=" + r10);
    }
}
