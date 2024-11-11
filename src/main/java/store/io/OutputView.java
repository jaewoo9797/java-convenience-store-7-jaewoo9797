package store.io;


import java.util.List;
import store.domain.OrderResult;
import store.domain.Receipt;

public class OutputView {

    public static boolean printInsufficientPromotionStock(String productName, int remainingCount) {
        System.out.printf("%n현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n", productName, remainingCount);
        return InputView.confirmFromUser();
    }

    public static boolean printBonusItemPrompt(String productName) {
        System.out.printf("%n현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n", productName);
        return InputView.confirmFromUser();
    }

    public static boolean printMemberShipSalePrompt() {
        System.out.println("\n멤버십 할인을 받으시겠습니까? (Y/N)");
        return InputView.confirmFromUser();
    }

    public static void printReceipt(Receipt receipt) {
        printReceiptHeader();
        printOrderDetails(receipt.getOrderResults());
        printBonusItems(receipt.getOrderResults());
        printReceiptFooter(receipt);
    }

    private static void printReceiptHeader() {
        System.out.println("\n==============W 편의점================");
        System.out.printf("%s\t\t\t\t%s\t%s\n", "상품명", "수량", "금액");
    }

    private static void printOrderDetails(List<OrderResult> orderResults) {
        for (OrderResult result : orderResults) {
            String productName = result.getProductName();
            int orderCount = result.getOrderCount();
            int totalPrice = orderCount * result.getProductPrice();

            System.out.printf("%s\t\t\t\t%d\t\t%,d\n", productName, orderCount, totalPrice);
        }
    }

    private static void printBonusItems(List<OrderResult> orderResults) {
        System.out.println("============증\t\t정================");
        for (OrderResult result : orderResults) {
            if (result.getBonusQuantity() > 0) {
                System.out.printf("%s\t\t\t\t%d\n", result.getProductName(), result.getBonusQuantity());
            }
        }
    }

    private static void printReceiptFooter(Receipt receipt) {
        int totalOrderCount = receipt.getTotalOrderCount();
        int totalAmount = receipt.getTotalAmount();
        int totalPromotionDiscount = receipt.getTotalPromotionDiscount();
        int membershipDiscount = receipt.getMembershipDiscount();
        int finalAmount = totalAmount - totalPromotionDiscount - membershipDiscount;
        consolePrintReceiptFooter(totalOrderCount, totalAmount, totalPromotionDiscount, membershipDiscount,
                finalAmount);
    }

    private static void consolePrintReceiptFooter(int totalOrderCount, int totalAmount, int totalPromotionDiscount,
                                                  int membershipDiscount, int finalAmount) {
        System.out.println("======================================");
        System.out.printf("총구매액\t\t\t%d\t\t%,d\n", totalOrderCount, totalAmount);
        System.out.printf("행사할인\t\t\t\t\t-%,d\n", totalPromotionDiscount);
        System.out.printf("멤버십할인\t\t\t\t\t-%,d\n", membershipDiscount);
        System.out.printf("내실돈\t\t\t\t\t\t%,d\n\n", finalAmount);
    }

    public static void printWelcomeGreeting() {
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n");
    }
}
