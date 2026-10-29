import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DeadlockDemo {
    static class Account {
        int balance;
        final int accountNumber;
        final Object lock = new Object();

        Account(int accountNumber, int balance) {
            this.accountNumber = accountNumber;
            this.balance = balance;
        }
    }

    static void unsafeTransfer(Account from, Account to, CountDownLatch ready, CountDownLatch start) {
        synchronized (from.lock) {
            ready.countDown();
            try {
                start.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            synchronized (to.lock) {
                from.balance -= 100;
                to.balance += 100;
                System.out.println("Unsafe transfer completed");
            }
        }
    }

    static void safeTransfer(Account from, Account to) {
        Account first = from.accountNumber < to.accountNumber ? from : to;
        Account second = from.accountNumber < to.accountNumber ? to : from;

        synchronized (first.lock) {
            synchronized (second.lock) {
                from.balance -= 100;
                to.balance += 100;
                System.out.println("Safe transfer completed: " + from.accountNumber + " to " + to.accountNumber);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(1, 1000);
        Account b = new Account(2, 1000);

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
            System.out.println("Deadlock detected");
        } else {
            System.out.println("Deadlock did not occur in this run");
        }

        Account x = new Account(1, 1000);
        Account y = new Account(2, 1000);

        Thread t3 = new Thread(() -> safeTransfer(x, y));
        Thread t4 = new Thread(() -> safeTransfer(y, x));

        t3.start();
        t4.start();

        t3.join();
        t4.join();

        System.out.println("Account 1 balance: " + x.balance);
        System.out.println("Account 2 balance: " + y.balance);
        System.out.println("Fixed transfers completed");
    }
}