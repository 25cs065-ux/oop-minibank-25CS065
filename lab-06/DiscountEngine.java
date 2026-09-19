package discount;

import java.util.List;

public class DiscountEngine {

    public static void applyDiscount(
            List<Double> prices,
            DiscountRule rule) {

        System.out.println("\nDiscounted Prices:");

        for (double price : prices) {

            double discountedPrice = rule.apply(price);

            System.out.printf(
                    "Original: Rs. %.2f -> Final: Rs. %.2f%n",
                    price,
                    discountedPrice
            );
        }
    }
}