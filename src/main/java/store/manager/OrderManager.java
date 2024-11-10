package store.manager;

import static store.error.ErrorMessage.INSUFFICIENT_STOCK_ERROR;
import static store.error.ErrorMessage.NON_EXIST_PRODUCT_ERROR_MESSAGE;
import static store.io.OutputView.printBonusItemPrompt;

import java.util.ArrayList;
import java.util.List;
import store.domain.Order;
import store.domain.OrderResult;
import store.domain.Product;

import store.domain.PromotionType;
import store.domain.Receipt;
import store.error.ErrorMessage;
import store.io.OutputView;
import store.repository.ProductStore;

public class OrderManager {

    private final ProductStore productStore;
    private final List<OrderResult> orderResultList = new ArrayList<>();

    public void printOrderResultList() {
        orderResultList.forEach(System.out::println);
    }

    public OrderManager(ProductStore productStore) {
        this.productStore = productStore;
    }

    // 주문 로직
    public void processOrder(Order order) {
        checkStockAvailabilityAtOrderTime(order);
        Product product = checkOrderByProductName(order);
        calculateBonusItemCount(product, order);
    }

    // 주문 시점에서 재고 확인
    private void checkStockAvailabilityAtOrderTime(Order order) {
        int totalStock = productStore.getTotalStockByProductName(order);
        if (totalStock < order.getOrderCount()) {
            throw new IllegalArgumentException(INSUFFICIENT_STOCK_ERROR.getErrorMessage());
        }
    }

    // 재고를 확인 후 충분하다면 받아온 Product객체에서 주문을 처리할 수 있다.
    private void calculateBonusItemCount(Product product, Order order) {
        // 처음에 찾은 Product에서 재고가 부족할 경우 어떻게 처리하지 ?
        if (!checkStockAvailability(product, order)) {
            // 함수 만들자.
            handleInsufficientStock(product, order);
            return;
        }
        // 재고가 충분하다면, 어떻게 처리할까?
        handleSufficientStock(product, order);
    }

    // 재고가 충분할 겨우 처리할 메서드
    private void handleSufficientStock(Product product, Order order) {
        int bonusItemCount = product.calculateProductBonusItemCount(order.getOrderCount());
        if (shouldAddBonusItem(product, order)) {
            applyBonusItemAndReduceStock(product, order, bonusItemCount);
            return;
        }
        if (product.getPromotionType() != PromotionType.NONE && !isExactPromotionOrder(product, order)) {
            handlePartialPromotionOrder(product, order, bonusItemCount);
            return;
        }
        product.decreaseNonPromoStock(order.getOrderCount());
        orderResultList.add(new OrderResult(order, bonusItemCount, product.getProductPrice(), product.getPromotionType()));
    }

    private boolean isExactPromotionOrder(Product product, Order order) {
        int promotionUnitCount = product.getPromotionUnitCount();
        return order.getOrderCount() % promotionUnitCount == 0;
    }

    // 보너스 아이템을 추가할 수 있는지 확인하는 메서드
    private boolean shouldAddBonusItem(Product product, Order order) {
        return product.needsAdditionalItemForBonus(order)
                && canFulfillPromotionOrder(product, order)
                && printBonusItemPrompt(product.getProductName());
    }

    private void applyBonusItemAndReduceStock(Product product, Order order, int bonusItemCount) {
        bonusItemCount++;
        order.plusOrderCount();

        product.decreasePromoStock(order.getOrderCount());

        orderResultList.add(new OrderResult(order, bonusItemCount, product.getProductPrice(), product.getPromotionType()));
    }

    // 재고가 충분 && 보너스 상품 제공하기엔 부족
    private void handlePartialPromotionOrder(Product product, Order order, int bonusItemCount) {
        int remainCount = order.getOrderCount() - (bonusItemCount * product.getPromotionUnitCount());
        if (OutputView.printInsufficientPromotionStock(order.getProductName(), remainCount)) {
            product.decreasePromoStock(order.getOrderCount());
            orderResultList.add(new OrderResult(order, bonusItemCount, product.getProductPrice(), product.getPromotionType()));
        }
    }

    // 조건 ( orderCount 숫자가 재고 +1 보다 커버리면 false
    private boolean canFulfillPromotionOrder(Product product, Order order) {
        return product.productStockCompareOrderQuantity(order.getOrderCount() + 1);
    }

    // 재고가 부족할 경우 처리할 메서드
    private void handleInsufficientStock(Product product, Order order) {
        int maxOrderFromPromotionStock = product.maxOrderFromPromotionStock(order.getOrderCount());
        int bonusItemCount = product.calculateProductBonusItemCount(maxOrderFromPromotionStock);
        int remainingOrderCount = order.getOrderCount() - maxOrderFromPromotionStock;
        int promotionUnitCount = order.getOrderCount() - product.getPromotionUnitCount() * bonusItemCount;
        Product nonPromotionProduct = findNonPromotionProduct(order);

        if (OutputView.printInsufficientPromotionStock(order.getProductName(), promotionUnitCount)) {
            reduceStocks(product, nonPromotionProduct, maxOrderFromPromotionStock, remainingOrderCount);
            saveOrderResult(order, bonusItemCount, product);
        }
    }

    private void saveOrderResult(Order order, int bonusItemCount, Product product) {
        orderResultList.add(new OrderResult(order, bonusItemCount, product.getProductPrice(), product.getPromotionType()));
    }

    private void reduceStocks(Product product, Product nonPromotionProduct, int promoStockUsed, int nonPromoStockUsed) {
        product.decreasePromoStock(promoStockUsed);
        nonPromotionProduct.decreaseNonPromoStock(nonPromoStockUsed);
    }

    // Product객체 안에서 재고를 확인하고 비교한다.
    private boolean checkStockAvailability(Product product, Order order) {
        return product.productStockCompareOrderQuantity(order.getOrderCount());
    }

    // 주문 상품 이름으로 먼저 Product 객체 찾기
    private Product checkOrderByProductName(Order order) {
        return productStore.findPromotionProduct(order)
                .orElseGet(() -> productStore.findNonPromotionProduct(order)
                        .orElseThrow(
                                () -> new IllegalArgumentException(NON_EXIST_PRODUCT_ERROR_MESSAGE.getErrorMessage())));
    }

    private Product findNonPromotionProduct(Order order) {
        return productStore.findNonPromotionProduct(order)
                .orElseThrow(() -> new IllegalArgumentException(
                        ErrorMessage.INPUT_ERROR_MESSAGE.getErrorMessage()));
    }

    public List<OrderResult> getOrderResultList() {
        return orderResultList;
    }

    public void printAllProductInfo() {
        productStore.printProductList();
    }

    public void printFinalReceipt() {
        Receipt receipt = new Receipt(orderResultList);
        receipt.calculateTotals();
        receipt.applyMemberShipDiscount();
        OutputView.printReceipt(receipt);
        orderResultList.clear();
    }
}
