import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import model.Account;
import model.SavingsAccount;
import exception.BankException;

public class MiniBankDeadlockDemo {
    static void unsafeTransfer(Account from, Account to,
                               CountDownLatch ready, CountDownLatch start) {
        synchronized (from) {
            ready.countDown();

            try {
                start.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            synchronized (to) {
                try {
                    from.withdraw(100);
                    to.deposit(100);
                    System.out.println("Unsafe transfer completed");
                } catch (BankException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    static void safeTransfer(Account from, Account to) {
        Account first;
        Account second;

        if (from.getAccountNumber().compareTo(to.getAccountNumber()) < 0) {
            first = from;
            second = to;
        } else {
            first = to;
            second = from;
        }

        synchronized (first) {
            synchronized (second) {
                try {
                    from.withdraw(100);
                    to.deposit(100);
                    System.out.println("Safe transfer completed: "
                            + from.getAccountNumber() + " -> " + to.getAccountNumber());
                } catch (BankException e) {
                    System.out.println("Transfer failed: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Account a = new SavingsAccount("Account A", 1000, 0);
        Account b = new SavingsAccount("Account B", 1000, 0);

        CountDownLatch ready = new CountDownLatch(2);
        CountDownLatch start = new CountDownLatch(1);

        Thread t1 = new Thread(() -> unsafeTransfer(a, b, ready, start));
        Thread t2 = new Thread(() -> unsafeTransfer(b, a, ready, start));

        t1.setDaemon(true);
        t2.setDaemon(true);

        t1.start();
        t2.start();

        ready.await();
        start.countDown();

        t1.join(1000);
        t2.join(1000);

        if (t1.isAlive() && t2.isAlive()) {
            System.out.println("Deadlock detected in unsafe transfers");
        } else {
            System.out.println("Deadlock did not occur in this run");
        }

        Account x = new SavingsAccount("Account X", 1000, 0);
        Account y = new SavingsAccount("Account Y", 1000, 0);

        Thread t3 = new Thread(() -> safeTransfer(x, y));
        Thread t4 = new Thread(() -> safeTransfer(y, x));

        t3.start();
        t4.start();

        t3.join();
        t4.join();

        System.out.println("Account X balance: Rs. " + x.getBalance());
        System.out.println("Account Y balance: Rs. " + y.getBalance());
        System.out.println("Fixed transfers completed");
    }
}