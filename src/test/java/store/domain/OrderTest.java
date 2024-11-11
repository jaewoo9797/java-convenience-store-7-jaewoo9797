package store.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.ValueSources;
import store.error.ErrorMessage;

public class OrderTest {


    @Test
    void 주문_생성_테스트() {
        String productName = "사이다";
        Order order = new Order(productName, "1");
        assertThat(order.getProductName())
                .as("기대값 {사이다}와 현재값 {%s}가 일치하지 않습니다.", productName)
                .isEqualTo("사이다");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "a"})
    void 주문_생성_실패_테스트(String element) {

        assertThatIllegalArgumentException().isThrownBy(() -> new Order("사이다", element))
                .withMessage(ErrorMessage.ORDER_ERROR_MESSAGE.getErrorMessage());
    }
}
