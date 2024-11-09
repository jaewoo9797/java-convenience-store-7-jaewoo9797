package store.io;

import camp.nextstep.edu.missionutils.Console;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.domain.Product;

public class InputView {
    private static final String AGREEMENT_LETTER = "Y";
    private static final String RESOURCE_ADRRESS = "src/main/resources/products.md";

    public static List<Product> initProductStore() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RESOURCE_ADRRESS))) {
            br.readLine(); // Skip header line
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
            System.err.printf("잘못된 데이터 형식: %s%n", line);
            throw new IllegalArgumentException("상품 데이터 형식이 잘못되었습니다.");
        }
        return new Product(info[0], info[1], info[2], info[3]);
    }

    public static boolean confirmFromUser() {
        return Console.readLine().trim().equalsIgnoreCase(AGREEMENT_LETTER);
    }

}
