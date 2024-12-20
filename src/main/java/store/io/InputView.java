package store.io;

import camp.nextstep.edu.missionutils.Console;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.domain.Order;
import store.domain.Product;

public class InputView {
    private static final String AGREEMENT_YES = "Y";
    private static final String AGREEMENT_NO = "N";
    private static final String RESOURCE_ADRRESS = "src/main/resources/products.md";
    private static final String ORDER_INPUT_PATTERN = "^\\[[가-힣a-zA-Z0-9]+-\\d+\\](,\\[[가-힣a-zA-Z0-9]+-\\d+\\])*$";


    public static List<Product> initProductStore() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RESOURCE_ADRRESS))) {
            br.readLine();
            while (br.ready()) {
                String line = br.readLine();
                products.add(parseProduct(line));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    private static Product parseProduct(String line) {
        String[] info = line.split(",");
        if (info.length < 4) {
            throw new IllegalArgumentException("[ERROR] 상품 데이터 형식이 잘못되었습니다.");
        }
        return new Product(info[0], info[1], info[2], info[3]);
    }

    public static boolean confirmFromUser() {
        while (true) {
            String input = Console.readLine().trim().toUpperCase();

            if (input.equals(AGREEMENT_YES)) {
                return true;
            }
            if (input.equals(AGREEMENT_NO)) {
                return false;
            }
            System.out.println("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }

    public static List<Order> inputOrderFromUser() {
        System.out.println("\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String input = Console.readLine();

        if (!isValidOrderInput(input)) {
            throw new IllegalArgumentException("[ERROR] 입력 형식이 잘못되었습니다. 다시 입력해 주세요.");
        }

        return parseOrderInput(input);
    }

    private static boolean isValidOrderInput(String input) {
        return input.matches(ORDER_INPUT_PATTERN);
    }

    private static List<Order> parseOrderInput(String input) {
        List<Order> orders = new ArrayList<>();
        String[] orderItems = input.split(",");

        for (String item : orderItems) {
            // 대괄호 제거 및 상품명과 수량 파싱
            String cleanedItem = item.replaceAll("[\\[\\]]", "");
            String[] parts = cleanedItem.split("-");
            String productName = parts[0];

            orders.add(new Order(productName, parts[1]));
        }

        return orders;
    }

    public static boolean inputOrderMoreToUser() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        boolean result =  confirmFromUser();
        System.out.println();
        return result;
    }

}
