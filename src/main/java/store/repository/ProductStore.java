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
                .filter(product -> product.getProductStock() > 0)
                .findFirst();
    }

    public Optional<Product> findNonPromotionProduct(Order order) {
        return productList.stream()
                .filter(product -> product.checkOrderProductName(order))
                .filter(product -> product.getPromotionType() == PromotionType.NONE)
                .findFirst();
    }

    public int getTotalStockByProductName(Order order) {
        return findPromotionProduct(order)
                .map(Product::getProductStock)
                .orElse(0) +
                findNonPromotionProduct(order)
                        .map(Product::getProductStock)
                        .orElse(0);
    }

    public void printProductList() {
        this.productList.forEach(System.out::println);
    }
}
