package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import store.error.ErrorMessage;

public class Order {
    private String ProductName;
    private int orderCount;
    private LocalDate createdDate;

    public Order(String productName, String orderCount, LocalDate createdDate) {
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
            throw new IllegalArgumentException(ErrorMessage.INPUT_ERROR_MESSAGE.getErrorMessage());
        }
        this.createdDate = DateTimes.now().toLocalDate();
    }

    public String getProductName() {
        return ProductName;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void plusOrderCount() {
        orderCount++;
    }

    public void decreaseOrderCount(int decreaseCount) {
        this.orderCount -= decreaseCount;
    }


    public void updateOrderCount(int adjustedOrderCount) {
        this.orderCount = adjustedOrderCount;
    }
}
