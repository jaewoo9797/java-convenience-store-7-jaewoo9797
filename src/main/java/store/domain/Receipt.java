package store.domain;

import java.util.List;
import store.io.OutputView;

public class Receipt {
    private final List<OrderResult> orderResults;
    private int totalAmount;
    private int totalPromotionDiscount;
    private int membershipDiscount;

    public Receipt(List<OrderResult> orderResults) {
        this.orderResults = orderResults;
        this.totalAmount = 0;
        this.totalPromotionDiscount = 0;
        this.membershipDiscount = 0;
    }

    public void applyMemberShipDiscount() {
        if (OutputView.printMemberShipSalePrompt()) {
            calculateMemberShipDiscount();
        }
    }

    private void calculateMemberShipDiscount() {
        for (OrderResult orderResult : orderResults) {
            if (orderResult.getPromotionType() == PromotionType.NONE) {
                int discount = (int) (orderResult.getProductPrice() * orderResult.getOrderCount() * 0.3);
                this.membershipDiscount += Math.min(discount, 8000);
            }
        }
    }

    // 총 구매액과 행사할인 계산
    public void calculateTotals() {
        for (OrderResult orderResult : orderResults) {
            int orderTotal = orderResult.getOrderCount() * orderResult.getProductPrice();
            int promotionDiscount = orderResult.getBonusQuantity() * orderResult.getProductPrice();
            totalAmount += orderTotal;
            totalPromotionDiscount += promotionDiscount;
        }
    }

    public int getTotalOrderCount() {
        return orderResults.stream().mapToInt(OrderResult::getOrderCount).sum();
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getTotalPromotionDiscount() {
        return totalPromotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public List<OrderResult> getOrderResults() {
        return orderResults;
    }

}
