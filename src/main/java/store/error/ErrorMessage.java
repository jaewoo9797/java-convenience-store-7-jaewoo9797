package store.error;

public enum ErrorMessage {
    INPUT_ERROR_MESSAGE("잘못된 입력입니다. 다시 입력해 주세요.")
    ;
    private final String errorMessage;

    private ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return "[ERROR] " + errorMessage;
    }
}
