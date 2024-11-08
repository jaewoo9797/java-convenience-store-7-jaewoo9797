package store.io;

import camp.nextstep.edu.missionutils.Console;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.domain.Product;

public class InputView {

    public static List<Product> initProductStore() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/products.md"))) {
            br.readLine(); // Skip header line
            while (br.ready()) {
                String line = br.readLine();
                products.add(parseProduct(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    private static Product parseProduct(String line) {
        String[] info = line.split(",");
        return new Product(info[0], info[1], info[2], info[3]);
    }

    public static boolean confirmBonusItem(String orderProductName) {
        OutputView.printBonusItemPrompt(orderProductName);
        String input = Console.readLine().trim().toUpperCase();
        return "Y".equals(input);
    }

    public static boolean confirmPurchaseWithoutPromotion() {
        String input = Console.readLine().trim().toUpperCase();
        return "Y".equals(input);
    }

    public static void main(String[] args) {
        for (Product p : initProductStore()) {
            System.out.println(p);
        }

    }
}
