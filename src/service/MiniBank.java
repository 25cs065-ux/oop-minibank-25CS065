package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import model.Account;
import model.BankInfo;
import persistence.Repository;
import persistence.StatePersister;
import persistence.ReportGenerator;
import persistence.TransactionProcessor;
import exception.BankException;

public class MiniBank {
    private static final Scanner sc = new Scanner(System.in);
    private static final Path DATA_FILE = Path.of("data/accounts.dat");
    private static final Path LOG_FILE = Path.of("data/transactions.log");
    private static final Path REPORT_FILE = Path.of("data/end-of-day-report.txt");

    public static void main(String[] args) {
        BankInfo bank = new BankInfo("MiniBank", "Main Branch");
        Repository<Account> repository = new Repository<>();
        BankService service = new BankService(repository, LOG_FILE);

        System.out.println("Welcome to " + bank.name() + " - " + bank.branch());

        if (Files.exists(DATA_FILE)) {
            try {
                service.loadAccounts(StatePersister.load(DATA_FILE));
                System.out.println("Saved accounts loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Unable to load saved accounts: " + e.getMessage());
            }
        }

        boolean running = true;

        while (running) {
            System.out.println("\n========== MINIBANK MENU ==========");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Account Statement");
            System.out.println("6. List All Accounts");
            System.out.println("7. Search by Owner Name");
            System.out.println("8. Generate End-of-Day Report");
            System.out.println("9. Process Batch Transactions");
            System.out.println("10. Save and Exit");
            System.out.print("Enter your choice: ");

            int choice = (int) readLong();

            try {
                switch (choice) {
                    case 1 -> openAccount(service);
                    case 2 -> deposit(service);
                    case 3 -> withdraw(service);
                    case 4 -> transfer(service);
                    case 5 -> statement(service);
                    case 6 -> listAccounts(service);
                    case 7 -> searchAccounts(service);
                    case 8 -> generateReport();
                    case 9 -> processBatch(service);
                    case 10 -> {
                        StatePersister.save(service.getAccounts(), DATA_FILE);
                        System.out.println("Accounts saved successfully.");
                        System.out.println("Goodbye! Thank you for using MiniBank.");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (BankException | IOException e) {
                System.out.println("Operation failed: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        sc.close();
    }

    private static void openAccount(BankService service) throws BankException, IOException {
        System.out.print("Enter owner name: ");
        String name = sc.nextLine();

        System.out.print("Enter account type (SAVINGS/CURRENT/FIXED): ");
        String type = sc.nextLine().trim().toUpperCase();

        System.out.print("Enter opening balance: Rs. ");
        long balance = readLong();

        long limit = 0;

        if (type.equals("SAVINGS")) {
            System.out.print("Enter minimum balance: Rs. ");
            limit = readLong();
        } else if (type.equals("CURRENT")) {
            System.out.print("Enter overdraft limit: Rs. ");
            limit = readLong();
        }

        Account account = service.openAccount(name, type, balance, limit);

        System.out.println("Account opened successfully!");
        System.out.println(account);
    }

    private static void deposit(BankService service) throws BankException, IOException {
        System.out.print("Enter account number: ");
        String number = sc.nextLine().trim();

        System.out.print("Enter deposit amount: Rs. ");
        long amount = readLong();

        service.deposit(number, amount);
        System.out.println("Deposit successful.");
    }

    private static void withdraw(BankService service) throws BankException, IOException {
        System.out.print("Enter account number: ");
        String number = sc.nextLine().trim();

        System.out.print("Enter withdrawal amount: Rs. ");
        long amount = readLong();

        service.withdraw(number, amount);
        System.out.println("Withdrawal successful.");
    }

    private static void transfer(BankService service) throws BankException, IOException {
        System.out.print("Enter source account number: ");
        String from = sc.nextLine().trim();

        System.out.print("Enter destination account number: ");
        String to = sc.nextLine().trim();

        System.out.print("Enter transfer amount: Rs. ");
        long amount = readLong();

        service.transfer(from, to, amount);
        System.out.println("Transfer completed.");
    }

    private static void statement(BankService service) throws BankException {
        System.out.print("Enter account number: ");
        String number = sc.nextLine().trim();

        System.out.println("\n" + service.statement(number));
    }

    private static void listAccounts(BankService service) {
        ArrayList<Account> accounts = service.listAccounts();

        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }

        System.out.println("\nAll Accounts:");

        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    private static void searchAccounts(BankService service) {
        System.out.print("Enter owner name: ");
        String name = sc.nextLine().trim();

        ArrayList<Account> accounts = service.searchByOwnerName(name);

        if (accounts.isEmpty()) {
            System.out.println("No accounts found for this owner.");
            return;
        }

        System.out.println("\nMatching Accounts:");

        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    private static void generateReport() throws IOException {
        String report = ReportGenerator.generate(
            Path.of("data"),
            REPORT_FILE
        );

        System.out.println(report);
        System.out.println("Report saved to: " + REPORT_FILE);
    }

    private static void processBatch(BankService service) throws InterruptedException {
        ArrayList<Account> accounts = service.listAccounts();

        if (accounts.isEmpty()) {
            System.out.println("No accounts available for batch processing.");
            return;
        }

        System.out.print("Enter number of transactions: ");
        int count = (int) readLong();

        if (count <= 0) {
            System.out.println("Transaction count must be positive.");
            return;
        }

        TransactionProcessor processor = new TransactionProcessor();

        for (Account account : accounts) {
            processor.addAccount(account);
        }

        for (int i = 1; i <= count; i++) {
            System.out.println("\nTransaction " + i);
            System.out.print("Enter type (DEPOSIT/WITHDRAW): ");
            String type = sc.nextLine().trim().toUpperCase();

            System.out.print("Enter account number: ");
            String number = sc.nextLine().trim();

            System.out.print("Enter amount: Rs. ");
            long amount = readLong();

            if (processor.getAccount(number) == null) {
                System.out.println("Account not found. Transaction skipped.");
                continue;
            }

            if (!type.equals("DEPOSIT") && !type.equals("WITHDRAW")) {
                System.out.println("Invalid transaction type. Transaction skipped.");
                continue;
            }

            processor.submit(() -> {
                try {
                    if (type.equals("DEPOSIT")) {
                        service.deposit(number, amount);
                    } else {
                        service.withdraw(number, amount);
                    }

                    System.out.println(
                        type + " of Rs. " + amount + " successful for " + number
                    );
                } catch (Exception e) {
                    System.out.println("Transaction failed: " + e.getMessage());
                }
            });
        }

        processor.stop();
        System.out.println("\nAll batch transactions processed.");
    }

    private static long readLong() {
        while (true) {
            String value = sc.nextLine().trim();

            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}