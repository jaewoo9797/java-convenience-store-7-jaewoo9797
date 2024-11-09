package store.io;

import camp.nextstep.edu.missionutils.Console;

public class OutputView {

    public static void printInsufficientStock(String productName, int orderCount) {
        System.out.printf("현재 %s %d개는 재고가 부족합니다.%n", productName, orderCount);
    }

    public static boolean printInsufficientPromotionStock(String productName, int remainingCount) {
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n", productName, remainingCount);
        return InputView.confirmFromUser();
    }

    // 보너스 아이템 추가 여부 확인 메시지
    public static boolean printBonusItemPrompt(String productName) {
        System.out.printf("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n", productName);
        return InputView.confirmFromUser();
    }
}
