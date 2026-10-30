
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import model.Account;
import model.SavingsAccount;
import exception.BankException;

public class TransactionProcessor {
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    private final ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();

    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }

        accounts.put(account.getAccountNumber(), account);
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public void submit(Runnable task) {
        executor.execute(task);
    }

    public void stop() throws InterruptedException {
        executor.shutdown();

        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
            executor.shutdownNow();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Account account = new SavingsAccount("Khushi", 5000, 0);

        TransactionProcessor processor = new TransactionProcessor();
        processor.addAccount(account);

        for (int i = 1; i <= 10; i++) {
            int id = i;
            long amount = 100;

            processor.submit(() -> {
                Account sharedAccount = processor.getAccount(account.getAccountNumber());

                try {
                    if (id % 2 == 0) {
                        sharedAccount.deposit(amount);
                        System.out.println(Thread.currentThread().getName()
                                + " deposited Rs. " + amount + " | Transaction " + id);
                    } else {
                        sharedAccount.withdraw(amount);
                        System.out.println(Thread.currentThread().getName()
                                + " withdrew Rs. " + amount + " | Transaction " + id);
                    }
                } catch (BankException e) {
                    System.out.println("Transaction " + id + " failed: " + e.getMessage());
                }
            });
        }

        processor.stop();

        System.out.println("Final balance: Rs. " + processor.getAccount(account.getAccountNumber()).getBalance());
        System.out.println("All transactions completed");
    }
}
