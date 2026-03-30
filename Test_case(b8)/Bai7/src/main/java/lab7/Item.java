package lab7;

/** BÀI 7 - Item: đại diện 1 sản phẩm trong giỏ hàng */
public class Item {
    private String name;
    private double price;
    public Item(String name, double price) { this.name = name; this.price = price; }
    public double getPrice() { return price; }
    public String getName()  { return name; }
}
