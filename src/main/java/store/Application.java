package store;

import java.time.LocalDate;
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
        Order order = new Order("감자칩","2",LocalDate.of(2024,1,1));
        orderManager.processOrder(order);
        orderManager.printFinalReceipt();
    }

    private static void processOrdersWithRetry(OrderManager orderManager) {
        try {
            List<Order> orders = InputView.inputOrderFromUser();
            orders.forEach(orderManager::processOrder);
            orderManager.printFinalReceipt();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
