package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import store.error.ErrorMessage;

public class Order {
    private final String ProductName;
    private int orderCount;
    private final LocalDateTime createdDate;

    public Order(String productName, String orderCount, LocalDateTime createdDate) {
        this.ProductName = productName;
        try {
            this.orderCount = Integer.parseInt(orderCount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INPUT_ERROR_MESSAGE.getErrorMessage());
        }
        this.createdDate = createdDate;
    }

    public Order(String productName, String orderCount) {
        this.ProductName = productName;
        try {
            this.orderCount = Integer.parseInt(orderCount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.ORDER_ERROR_MESSAGE.getErrorMessage());
        }
        this.createdDate = DateTimes.now();
    }

    public String getProductName() {
        return ProductName;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void plusOrderCount() {
        orderCount++;
    }

    public void decreaseOrderCount(int decreaseCount) {
        this.orderCount -= decreaseCount;
    }

}
