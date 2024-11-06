package store.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import store.error.ErrorMessage;

public class OrderTest {


    @Test
    void 주문_생성_테스트() {
        //given
        String productName = "사이다";
        String orderCount = "1";
        LocalDate createdDate = LocalDate.now();
        //when
        Order order = new Order(productName, orderCount, createdDate);
        int parseIntOrderCount = Integer.parseInt(orderCount);
        //then
        assertThat(order.getProductName())
                .as("기대값 {사이다}와 현재값 {%s}가 일치하지 않습니다.", productName)
                .isEqualTo("사이다");
        assertThat(order.getOrderCount()).isEqualTo(parseIntOrderCount);
        assertThat(order.getCreatedDate()).isEqualTo(createdDate);
    }

    @Test
    void 주문_생성_실패_테스트() {
        // given
        String productName = "사이다";
        String orderCount = "a";
        LocalDate createdDate = LocalDate.now();

        //then
        assertThatIllegalArgumentException().isThrownBy(() -> new Order(productName, orderCount, createdDate))
                .withMessage(ErrorMessage.INPUT_ERROR_MESSAGE.getErrorMessage());
    }
}
