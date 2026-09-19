package discount;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        List<Double> prices = Arrays.asList(
                500.0,
                1000.0,
                1500.0,
                2000.0
        );

        System.out.println("===== Discount Engine =====");

        System.out.println("1. Flat 10% Discount");
        System.out.println("2. Flat 20% Discount");
        System.out.println("3. Rs. 100 Discount");
        System.out.println("4. No Discount");

        System.out.print("Choose a discount rule: ");

        int choice = sc.nextInt();

        DiscountRule selectedRule;

        switch (choice) {


            case 1:
                selectedRule = price -> price * 0.90;
                break;

            case 2:
                selectedRule = price -> price * 0.80;
                break;

            case 3:
                selectedRule = price -> Math.max(0, price - 100);
                break;

            case 4:
                selectedRule = price -> price;
                break;

            default:
                System.out.println("Invalid choice.");
                sc.close();
                return;
        }

        DiscountEngine.applyDiscount(prices, selectedRule);

        sc.close();
    }
}