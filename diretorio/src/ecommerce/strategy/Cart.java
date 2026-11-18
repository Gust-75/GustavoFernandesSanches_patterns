package ecommerce.strategy;
public class Cart {
    private final double subtotal;
    private DiscountStrategy discountStrategy = new NoDiscount();
    public Cart(double subtotal) {
        this.subtotal = subtotal;
    }
    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }
    public double getSubtotal() {
        return subtotal;
    }
    public double total() {
        return discountStrategy.apply(subtotal);
    }
}