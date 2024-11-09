package store.domain;

import java.time.LocalDate;
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
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 12, 31),
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
            LocalDate.of(2024, 11, 1),
            LocalDate.of(2024, 11, 30),
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
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 12, 31),
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
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int promotionUnitCount;

    private PromotionType(String promotionName, LocalDate startDate, LocalDate endDate, int promotionUnitCount) {
        this.promotionName = promotionName;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public boolean isPromotionDuration(LocalDate orderTime) {
        if (NONE == this) {
            return false;
        }
        return (orderTime.isEqual(startDate) || orderTime.isAfter(startDate)) &&
                (orderTime.isEqual(endDate) || orderTime.isBefore(endDate));
    }

    public int getPromotionUnitCount() {
        return promotionUnitCount;
    }
}
