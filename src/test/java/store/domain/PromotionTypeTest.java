package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class PromotionTypeTest {
    @Test
    void 프로모션_시작_시간_직전_주문() {
        LocalDateTime orderTime = LocalDateTime.of(2024, 10, 31, 23, 59, 59);
        boolean isPromotionActive = PromotionType.FLASH_SALE.isPromotionDuration(orderTime);
        assertThat(isPromotionActive).isFalse();
    }

    @Test
    void 프로모션_종료_시간_직후_주문() {
        LocalDateTime orderTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        boolean isPromotionActive = PromotionType.CARBONATE_TWO_PLUS_ONE.isPromotionDuration(orderTime);
        assertThat(isPromotionActive).isFalse();
    }

    @Test
    void 프로모션_시작_시간_직후_주문() {
        LocalDateTime orderTime = LocalDateTime.of(2024, 11, 1, 0, 0, 0);
        boolean isPromotionActive = PromotionType.FLASH_SALE.isPromotionDuration(orderTime);
        assertThat(isPromotionActive).isTrue();
    }

    @Test
    void 프로모션_정확한_종료_시간에_주문() {
        LocalDateTime orderTime = LocalDateTime.of(2024, 11, 30, 23, 59, 59);
        boolean isPromotionActive = PromotionType.FLASH_SALE.isPromotionDuration(orderTime);
        assertThat(isPromotionActive).isTrue();
    }


    @Test
    void 프로모션_기간_중간에_주문() {
        LocalDateTime orderTime = LocalDateTime.of(2024, 6, 15, 12, 0);
        boolean isPromotionActive = PromotionType.CARBONATE_TWO_PLUS_ONE.isPromotionDuration(orderTime);
        assertThat(isPromotionActive).isTrue();
    }

    @Test
    void 프로모션_시작_시간에_주문() {
        LocalDateTime orderTime = LocalDateTime.of(2024, 11, 1, 0, 0, 0);
        boolean isPromotionActive = PromotionType.FLASH_SALE.isPromotionDuration(orderTime);
        assertThat(isPromotionActive).isTrue();
    }
}