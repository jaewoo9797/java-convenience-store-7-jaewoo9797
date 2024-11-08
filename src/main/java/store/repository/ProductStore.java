package store.repository;

import java.util.List;
import java.util.Optional;
import store.domain.Order;
import store.domain.Product;
import store.domain.PromotionType;

public class ProductStore {

    private final List<Product> productList;

    public ProductStore(List<Product> productList) {
        this.productList = productList;
    }

    public Optional<Product> findPromotionProduct(Order order) {
        return productList.stream()
                .filter(product -> product.checkOrderProductName(order))
                .filter(product -> product.getPromotionType() != PromotionType.NONE)
                .filter(product -> product.checkPromotionDuration(order))
                .findFirst();
    }

    public Optional<Product> findNonPromotionProduct(Order order) {
        return productList.stream()
                .filter(product -> product.checkOrderProductName(order))
                .filter(product -> product.getPromotionType() == PromotionType.NONE)
                .findFirst();
    }
    public void printProductList() {
        this.productList.forEach(System.out::println);
    }
    public int findTotalNonPromoStock(Order order) {
        return productList.stream()
                .filter(product -> product.checkOrderProductName(order))
                .filter(product -> product.getPromotionType() == PromotionType.NONE)
                .mapToInt(Product::getProductStock)
                .sum();
    }
}
