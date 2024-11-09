package store.domain;

import java.time.LocalDate;
import store.error.ErrorMessage;

public class Product {
    private static final int INSUFFICIENT_STOCK = -1;
    private final String productName;
    private final int productPrice;
    private int productStock;
    private final PromotionType promotionType;

    public Product(String productName, String productPrice, String productQuantity, String promotionType) {
        this.productName = productName;
        try {
            this.productPrice = Integer.parseInt(productPrice);
            this.productStock = Integer.parseInt(productQuantity);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INPUT_ERROR_MESSAGE.getErrorMessage());
        }
        this.promotionType = PromotionType.from(promotionType);
    }

    // 주문 수량과 재고 비교
    public boolean productStockCompareOrderQuantity(int orderCount) {
        return Integer.compare(productStock, orderCount) != INSUFFICIENT_STOCK;
    }

    public boolean checkOrderProductName(Order order) {
        return order.getProductName().equals(productName);
    }

    public int calculateProductBonusItemCount(Order order) {
        return this.promotionType.calculateBonusItems(order.getOrderCount());
    }

    public boolean needsAdditionalItemForBonus(Order order) {
        return this.promotionType.needsAdditionalItemForBonus(order.getOrderCount());
    }

    public void decreasePromoStock(int count) {
        if (productStock < count) {
            throw new IllegalStateException("프로모션 재고가 부족합니다.");
        }
        productStock -= count;
    }

    public void decreaseNonPromoStock(int count) {
        if (productStock < count) {
            throw new IllegalStateException("일반 재고가 부족합니다.");
        }
        productStock -= count;
    }

    @Override
    public String toString() {
        return String.format("- %s %,d원 %d개 %s", productName, productPrice, productStock,
                promotionType.printPromotionType().trim());
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public boolean checkPromotionDuration(Order order) {
        LocalDate orderDate = order.getCreatedDate();
        return promotionType.isPromotionDuration(orderDate);
    }

    public int getPromotionUnitCount() {
        return promotionType.getPromotionUnitCount();
    }
}
