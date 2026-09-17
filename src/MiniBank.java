import java.util.Scanner;

public class MiniBank {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        BankInfo bank = new BankInfo("MiniBank", "Main Branch");
        System.out.println("Welcome to " + bank.name() + " - " + bank.branch());

        Account[] accounts = new Account[3];

        accounts[0] = new SavingsAccount("Khushi", 1000, 500);
        accounts[1] = new CurrentAccount("Rahul", 1000, 500);
        accounts[2] = new FixedDepositAccount("Priya", 2000);

        accounts[0].deposit(500);
        accounts[0].withdraw(200);

        accounts[1].deposit(1000);
        accounts[1].withdraw(300);

        accounts[2].deposit(500);
        accounts[2].withdraw(700);

        System.out.println("\nAccount Balances:");
        for (int i = 0; i < accounts.length; i++) {
            System.out.println(
                accounts[i].getOwnerName() + " : " + accounts[i].getBalance()
            );
        }

        // Runtime polymorphism
        System.out.println("\nInterest Rates:");
        for (Account account : accounts) {
            System.out.println(
                account.getOwnerName() + " : " + account.interestRate() + "%"
            );

            // Pattern instanceof check
            if (account instanceof FixedDepositAccount fd) {
                System.out.println(
                    fd.getOwnerName() + " has a Fixed Deposit Account."
                );
            }
        }

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