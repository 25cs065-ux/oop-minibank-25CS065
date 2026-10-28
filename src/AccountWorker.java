import model.Account;
import exception.InvalidAmountException;

public class AccountWorker implements Runnable {
    private final Account account;
    private final int times;
    private final long amount;
    private final boolean unsafe;

    public AccountWorker(Account account, int times, long amount) {
        this(account, times, amount, false);
    }

    public AccountWorker(Account account, int times, long amount, boolean unsafe) {
        this.account = account;
        this.times = times;
        this.amount = amount;
        this.unsafe = unsafe;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started");

        for (int i = 0; i < times; i++) {
            try {
                if (unsafe) {
                    account.depositWithoutSynchronization(amount);
                } else {
                    account.deposit(amount);
                }
            } catch (InvalidAmountException e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        System.out.println(Thread.currentThread().getName() + " completed");
    }
}
