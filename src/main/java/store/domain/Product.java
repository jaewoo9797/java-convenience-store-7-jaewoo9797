package store.domain;

import static store.error.ErrorMessage.INSUFFICIENT_STOCK_ERROR;

import java.time.LocalDate;
import store.error.ErrorMessage;

public class Product {
    private static final int INSUFFICIENT_STOCK = -1;
    private static final int NON_STOCK = 0;
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

    public boolean productStockCompareOrderQuantity(int orderCount) {
        return Integer.compare(productStock, orderCount) != INSUFFICIENT_STOCK;
    }

    public boolean checkOrderProductName(Order order) {
        return order.getProductName().equals(productName);
    }

    public int calculateProductBonusItemCount(int orderCount) {
        return this.promotionType.calculateBonusItems(orderCount);
    }

    public int maxOrderFromPromotionStock( int orderCount) {
        return Math.min(getProductStock(), orderCount);
    }

    public boolean needsAdditionalItemForBonus(Order order) {
        return this.promotionType.needsAdditionalItemForBonus(order.getOrderCount());
    }

    public void decreasePromoStock(int count) {
        if (productStock < count) {
            throw new IllegalArgumentException(INSUFFICIENT_STOCK_ERROR.getErrorMessage());
        }
        productStock -= count;
    }

    @Override
    public String toString() {
        if (productStock == NON_STOCK) {
            return String.format("- %s %,d원 재고 없음 %s", productName, productPrice, promotionType.printPromotionType());
        }
        return String.format("- %s %,d원 %d개 %s", productName, productPrice, productStock,
                promotionType.printPromotionType());
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
