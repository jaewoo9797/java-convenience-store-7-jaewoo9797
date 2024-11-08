package store.domain;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ProductTest {

    private Product createTestProduct() {
        return new Product("콜라", "1000", "10", "탄산2+1");
    }

    @Test
    void 상품_객체_생성_테스트() {
        Product product = createTestProduct();

        assertThat(product).extracting(
                Product::getProductName,
                Product::getProductPrice,
                Product::getProductStock,
                Product::getPromotionType
        ).containsExactly("콜라", 1000, 10, PromotionType.CARBONATE_TWO_PLUS_ONE);
    }


}
