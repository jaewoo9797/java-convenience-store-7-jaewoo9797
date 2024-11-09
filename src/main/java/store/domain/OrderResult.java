package store.domain;

public class OrderResult {
    private final Order order;
    private final int bonusQuantity;
    private final PromotionType promotionType;

    public OrderResult(Order order, int bonusQuantity, PromotionType promotionType) {
        this.order = order;
        this.bonusQuantity = bonusQuantity;
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



    @Override
    public String toString() {
        return "OrderResult{" +
                "order=" + order.toString() +
                ", bonusQuantity=" + bonusQuantity +
                ", promotionType=" + promotionType.printPromotionType() +
                '}';
    }
}
