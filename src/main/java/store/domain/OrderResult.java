package store.domain;

public class OrderResult {
    private final Order order;
    private final int bonusQuantity;
    private final int totalPrice;

    public OrderResult(Order order, int bonusQuantity, int totalPrice) {
        this.order = order;
        this.bonusQuantity = bonusQuantity;
        this.totalPrice = totalPrice;
    }

    public String getProductName() {
        return order.getProductName();
    }

    public int getOrderCount() {
        return order.getOrderCount();
    }

    public int getBonusQuantity() {
        return bonusQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "OrderResult{" +
                "order=" + order.toString() +
                ", bonusQuantity=" + bonusQuantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
