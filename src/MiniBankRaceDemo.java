
import model.Account;

public class MiniBankRaceDemo {
    private static final int THREADS = 10;
    private static final int DEPOSITS_PER_THREAD = 1000;
    private static final long AMOUNT = 1;

    private static Account createAccount() {
        return new Account("Practical 9 Test", 0) {
            @Override
            public double interestRate() {
                return 0;
            }

            @Override
            public boolean canWithdraw(long amount) {
                return amount > 0 && amount <= getBalance();
            }
        };
    }

    private static void runTest(Account account, boolean unsafe)
            throws InterruptedException {
        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            AccountWorker worker = new AccountWorker(
                    account,
                    DEPOSITS_PER_THREAD,
                    AMOUNT,
                    unsafe
            );

            threads[i] = new Thread(worker, "BankWorker-" + (i + 1));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Final balance: Rs. " + account.getBalance());
        System.out.println("Expected balance: Rs. "
                + (THREADS * DEPOSITS_PER_THREAD * AMOUNT));
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("===== WITHOUT SYNCHRONIZATION =====");
        Account unsafeAccount = createAccount();
        runTest(unsafeAccount, true);

        System.out.println();
        System.out.println("===== WITH SYNCHRONIZATION =====");
        Account safeAccount = createAccount();
        runTest(safeAccount, false);
    }
}
