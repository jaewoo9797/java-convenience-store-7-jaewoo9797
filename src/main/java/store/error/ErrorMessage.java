package store.error;

public enum ErrorMessage {
    INPUT_ERROR_MESSAGE("잘못된 입력입니다. 다시 입력해 주세요."),
    ORDER_ERROR_MESSAGE("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NON_EXIST_PRODUCT_ERROR_MESSAGE("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    INSUFFICIENT_STOCK_ERROR("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INPUT_FORMAT_ERROR_MESSAGE("잘못된 입력입니다. 다시 입력해 주세요.")
    ;
    private final String errorMessage;

    private ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return "[ERROR] " + errorMessage;
    }
}
