package store.domain;

public class OrderResult {
    private final Order order;
    private final int bonusQuantity;
    private final int productPrice;

    private final PromotionType promotionType;

    public OrderResult(Order order, int bonusQuantity, int productPrice, PromotionType promotionType) {
        this.order = order;
        this.bonusQuantity = bonusQuantity;
        this.productPrice = productPrice;
        this.promotionType = promotionType;
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

    public int getProductPrice() {
        return productPrice;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    @Override
    public String toString() {
        return "OrderResult{" +
                "order=" + order.toString() +
                ", bonusQuantity=" + bonusQuantity +
                ", productPrice=" + productPrice +
                ", promotionType=" + promotionType.printPromotionType() +
                '}';
    }
}
