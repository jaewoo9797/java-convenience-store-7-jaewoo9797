package store;


import store.domain.Order;
import store.io.InputView;
import store.manager.OrderManager;
import store.repository.ProductStore;

public class Application {
    public static void main(String[] args) {
        ProductStore productStore = new ProductStore(InputView.initProductStore());
        OrderManager orderManager = new OrderManager(productStore);

        System.out.println("=== 테스트 케이스 1 ===");
        productStore.printProductList();
        orderManager.processOrder(new Order("콜라", "5"));
        orderManager.printOrderResultList();
        System.out.println();
        System.out.println("=== 테스트 케이스 2 ===");
        productStore.printProductList();
        orderManager.processOrder(new Order("콜라", "10"));
        orderManager.printOrderResultList();
        System.out.println();
        System.out.println("=== 테스트 케이스 3 ===");
        productStore.printProductList();

        orderManager.processOrder(new Order("콜라", "10"));

        System.out.println("=== 테스트 케이스 4 ===");
        orderManager.processOrder(new Order("사이다", "3"));

        System.out.println("=== 테스트 케이스 5 ===");
        orderManager.processOrder(new Order("물", "8"));
    }
}
