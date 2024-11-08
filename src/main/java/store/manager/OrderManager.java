package store.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.domain.Order;
import store.domain.OrderResult;
import store.domain.Product;
import store.error.ErrorMessage;
import store.io.InputView;
import store.io.OutputView;
import store.repository.ProductStore;

public class OrderManager {

    private final ProductStore productStore;
    private final List<OrderResult> orderResultList = new ArrayList<>();

    public void printOrderResultList () {
        orderResultList.forEach(System.out::println);
    }

    public OrderManager(ProductStore productStore) {
        this.productStore = productStore;
    }

    public void processOrder(Order order) {
        Product product = checkOrder(order);
        int bonusItems = calculateBonusItems(order, product);
        checkStockAvailability(order, product);

        int promoUsage = calculatePromoUsage(order, product);
        int finalOrderCount = handleRemainingStock(order, product, promoUsage);

        saveOrderResult(order, product, finalOrderCount, bonusItems);
    }

    // 재고 확인
    private void checkStockAvailability(Order order, Product product) {
        int totalStock = calculateTotalStock(order, product);
        if (order.getOrderCount() > totalStock) {
            OutputView.printInsufficientStock(order.getProductName(), order.getOrderCount());
            throw new IllegalStateException("재고가 부족합니다.");
        }
    }

    // 프로모션 재고 사용량 계산
    private int calculatePromoUsage(Order order, Product product) {
        int promoStock = product.getProductStock();
        int maxPromoItems = (promoStock / 3) * 3; // 탄산2+1 프로모션의 최대 적용 개수 계산
        return Math.min(order.getOrderCount(), maxPromoItems);
    }

    // 총 재고 계산
    private int calculateTotalStock(Order order, Product product) {
        int promoStock = product.getProductStock();
        int nonPromoStock = productStore.findTotalNonPromoStock(order);
        return promoStock + nonPromoStock;
    }

    // 재고확인
    public Product checkOrder(Order order) {
        Optional<Product> promotionProduct = productStore.findPromotionProduct(order);
        return promotionProduct.orElseGet(() -> productStore.findNonPromotionProduct(order)
                .orElseThrow(() -> new IllegalArgumentException(
                        ErrorMessage.NON_EXIST_PRODUCT_ERROR_MESSAGE.getErrorMessage())));
    }

    // 보너스 아이템 계산
    public int calculateBonusItems(Order order, Product product) {
        int promoStock = product.getProductStock();
        int bonusItems = product.calculateProductBonusItemCount(order);

        if (product.needsAdditionalItemForBonus(order) && InputView.confirmBonusItem(order.getProductName())) {
            if (order.getOrderCount() + 1 <= promoStock) {
                order.plusOrderCount();
                bonusItems++;
            }
        }
        return bonusItems;
    }


    // 사용자에게 일반 결제 안내
    private void notifyUserOfRegularPrice(int remainingCount, Product product) {
        OutputView.printInsufficientPromotionStock(product.getProductName(), remainingCount);
    }

    // 잔여 주문 처리
    private int handleRemainingStock(Order order, Product product, int promoUsage) {
        int remainingOrderCount = order.getOrderCount() - promoUsage;

        if (remainingOrderCount <= 0) {
            product.decreasePromoStock(promoUsage);
            return order.getOrderCount();
        }

        notifyUserOfRegularPrice(remainingOrderCount, product);
        return handleRemainingOrder(order, product, promoUsage, remainingOrderCount);
    }

    // 일반 결제 처리
    private int handleRemainingOrder(Order order, Product promoProduct, int promoUsage, int remainingCount) {
        if (InputView.confirmPurchaseWithoutPromotion()) {
            return processRegularPurchase(order, promoProduct, promoUsage, remainingCount);
        }
        return processPromoOnlyPurchase(order, promoProduct, promoUsage);
    }

    // 일반 결제 처리
    private int processRegularPurchase(Order order, Product promoProduct, int promoUsage, int remainingCount) {
        promoProduct.decreasePromoStock(promoUsage);

        Product nonPromoProduct = findNonPromoProduct(order);
        if (nonPromoProduct.getProductStock() < remainingCount) {
            OutputView.printInsufficientStock(order.getProductName(), remainingCount);
            throw new IllegalStateException("일반 상품 재고가 부족합니다.");
        }

        nonPromoProduct.decreaseNonPromoStock(remainingCount);
        return order.getOrderCount();
    }

    private int processPromoOnlyPurchase(Order order, Product promoProduct, int promoUsage) {
        promoProduct.decreasePromoStock(promoUsage);
        order.updateOrderCount(promoUsage);
        return promoUsage;
    }

    // 일반 재고 감소
    private void decreaseNonPromoStock(Order order, int count) {
        Product nonPromoProduct = findNonPromoProduct(order);
        nonPromoProduct.decreaseNonPromoStock(count);
    }

    // 일반 상품 찾기
    private Product findNonPromoProduct(Order order) {
        return productStore.findNonPromotionProduct(order)
                .orElseThrow(() -> new IllegalStateException("일반 상품 재고가 부족합니다."));
    }


    // 주문 결과 저장
    private void saveOrderResult(Order order, Product product, int orderCount, int bonusItems) {
        int totalPrice = orderCount * product.getProductPrice();
        OrderResult result = new OrderResult(order, bonusItems, totalPrice);
        orderResultList.add(result);
    }

    public static void main(String[] args) {

    }
}
