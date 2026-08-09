import java.util.Scanner;

public class MiniBank {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        BankInfo bank = new BankInfo("MiniBank", "Main Branch");
        System.out.println("Welcome to " + bank.name() + " - " + bank.branch());

        boolean running = true;

        while (running) {
            System.out.println("\n1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            String message = switch (choice) {
                case 1 -> "Open Account - to be implemented in a later lab";
                case 2 -> "Deposit - to be implemented in a later lab";
                case 3 -> "Withdraw - to be implemented in a later lab";
                case 4 -> "Transfer - to be implemented in a later lab";
                case 5 -> "Goodbye! Thank you for using MiniBank.";
                default -> "Invalid choice. Please try again.";
            };

            System.out.println(message);

            if (choice == 5) {
                running = false;
            }
        }

        sc.close();
    }
}