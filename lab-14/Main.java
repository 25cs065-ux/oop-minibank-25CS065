
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ItemService service = new ItemService();

        while (true) {
            System.out.println("\n===== ITEM MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Display Items");
            System.out.println("4. Count Items");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter item name: ");
                        service.addItem(sc.nextLine());
                        System.out.println("Item added successfully.");
                        break;
                    case 2:
                        System.out.print("Enter item name: ");
                        boolean removed = service.removeItem(sc.nextLine());
                        System.out.println(removed ? "Item removed successfully." : "Item not found.");
                        break;
                    case 3:
                        for (Item item : service.getItems()) {
                            System.out.println(item);
                        }
                        break;
                    case 4:
                        System.out.println("Total Items: " + service.getCount());
                        break;
                    case 5:
                        sc.close();
                        return;
                    default:
                        service.performAction("INVALID", "");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
