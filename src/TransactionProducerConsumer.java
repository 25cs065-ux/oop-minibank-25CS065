import model.Account;
import model.SavingsAccount;
import exception.BankException;

public class TransactionProducerConsumer {
    static class TransactionBuffer {
        private final Runnable[] buffer = new Runnable[3];
        private int count;
        private int in;
        private int out;
        private boolean finished;

        synchronized void put(Runnable task) throws InterruptedException {
            while (count == buffer.length) {
                wait();
            }

            buffer[in] = task;
            in = (in + 1) % buffer.length;
            count++;
            notifyAll();
        }

        synchronized Runnable take() throws InterruptedException {
            while (count == 0 && !finished) {
                wait();
            }

            if (count == 0 && finished) {
                return null;
            }

            Runnable task = buffer[out];
            buffer[out] = null;
            out = (out + 1) % buffer.length;
            count--;
            notifyAll();
            return task;
        }

        synchronized void finish() {
            finished = true;
            notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Account account = new SavingsAccount("Khushi", 5000, 0);
        TransactionBuffer buffer = new TransactionBuffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    int id = i;

                    buffer.put(() -> {
                        try {
                            if (id % 2 == 0) {
                                account.deposit(100);
                                System.out.println("Transaction " + id + ": Deposit Rs. 100");
                            } else {
                                account.withdraw(100);
                                System.out.println("Transaction " + id + ": Withdrawal Rs. 100");
                            }
                        } catch (BankException e) {
                            System.out.println("Transaction failed: " + e.getMessage());
                        }
                    });
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                buffer.finish();
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    Runnable task = buffer.take();

                    if (task == null) {
                        break;
                    }

                    task.run();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Final balance: Rs. " + account.getBalance());
        System.out.println("All buffered transactions processed");
    }
}