package store;

import java.util.List;
import store.domain.Order;
import store.io.InputView;
import store.io.OutputView;
import store.manager.OrderManager;
import store.repository.ProductStore;

public class Application {
    public static void main(String[] args) {
        OrderManager orderManager = new OrderManager(new ProductStore(InputView.initProductStore()));
        do {
            OutputView.printWelcomeGreeting();
            orderManager.printAllProductInfo();

            processOrdersWithRetry(orderManager);

        } while (InputView.inputOrderMoreToUser());

    }

    // 주문 입력 및 처리를 재시도하는 메서드
    private static void processOrdersWithRetry(OrderManager orderManager) {
        try {
            // 사용자로부터 주문 입력 받기
            List<Order> orders = InputView.inputOrderFromUser();
            orders.forEach(orderManager::processOrder);
            orderManager.printFinalReceipt();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
