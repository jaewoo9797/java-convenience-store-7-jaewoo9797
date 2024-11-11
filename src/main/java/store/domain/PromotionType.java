package store.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import store.error.ErrorMessage;

public enum PromotionType {
    NONE("null",
            null,
            null,
            1) {
        @Override
        public int calculateBonusItems(int orderCount) {
            return 0;
        }
    },
    CARBONATE_TWO_PLUS_ONE("탄산2+1",
            LocalDateTime.of(2024, 1, 1,0,0),
            LocalDateTime.of(2024, 12, 30, 23, 59, 59),
            3
    ) {
        @Override
        public int calculateBonusItems(int orderCount) {
            return orderCount / 3;
        }

        @Override
        public boolean needsAdditionalItemForBonus(int orderCount) {
            return orderCount % 3 == 2;
        }
    },
    FLASH_SALE("반짝할인",
            LocalDateTime.of(2024, 11, 1,0,0),
            LocalDateTime.of(2024, 11, 29, 23, 59, 59),
            2
    ) {
        @Override
        public int calculateBonusItems(int orderCount) {
            return orderCount / 2;
        }

        @Override
        public boolean needsAdditionalItemForBonus(int orderCount) {
            return orderCount % 2 == 1;
        }
    },
    MD_RECOMMENDATION("MD추천상품",
            LocalDateTime.of(2024, 1, 1,0,0),
            LocalDateTime.of(2024, 12, 30, 23, 59, 59),
            2
    ) {
        @Override
        public int calculateBonusItems(int orderCount) {
            return orderCount / 2;
        }

        @Override
        public boolean needsAdditionalItemForBonus(int orderCount) {
            return orderCount % 2 == 1;
        }
    },
    ;
    private final String promotionName;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final int promotionUnitCount;

    private PromotionType(String promotionName, LocalDateTime startDateTime, LocalDateTime endDateTime, int promotionUnitCount) {
        this.promotionName = promotionName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.promotionUnitCount = promotionUnitCount;
    }

    public static PromotionType from(String type) {
        return Arrays.stream(PromotionType.values())
                .filter(p -> p.getPromotionName().equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INPUT_ERROR_MESSAGE.getErrorMessage()));
    }

    private String getPromotionName() {
        return promotionName;
    }

    public String printPromotionType() {
        if (this == NONE) {
            return "";
        }
        return promotionName;
    }

    public abstract int calculateBonusItems(int orderCount);

    public boolean needsAdditionalItemForBonus(int orderCount) {
        return false;
    }

    public boolean isPromotionDuration(LocalDateTime orderTime) {
        if (NONE == this) {
            return false;
        }
        return (orderTime.isEqual(startDateTime) || orderTime.isAfter(startDateTime)) &&
                (orderTime.isEqual(endDateTime) || orderTime.isBefore(endDateTime));
    }

    public int getPromotionUnitCount() {
        return promotionUnitCount;
    }
}
