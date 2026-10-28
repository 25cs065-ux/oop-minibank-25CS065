
import java.util.concurrent.atomic.AtomicLong;

public class ArraySumRace {
    static final int SIZE = 1000000;
    static final int THREADS = 8;
    static long total = 0;
    static final AtomicLong atomicTotal = new AtomicLong(0);

    static void runTest(int method) throws InterruptedException {
        long[] numbers = new long[SIZE];

        for (int i = 0; i < SIZE; i++) {
            numbers[i] = i + 1L;
        }

        total = 0;
        atomicTotal.set(0);

        Thread[] threads = new Thread[THREADS];
        long start = System.nanoTime();

        for (int i = 0; i < THREADS; i++) {
            final int from = i * SIZE / THREADS;
            final int to = (i + 1) * SIZE / THREADS;

            threads[i] = new Thread(() -> {
                long localSum = 0;

                for (int j = from; j < to; j++) {
                    localSum += numbers[j];
                }

                if (method == 0) {
                    total += localSum;
                } else if (method == 1) {
                    synchronized (ArraySumRace.class) {
                        total += localSum;
                    }
                } else {
                    atomicTotal.addAndGet(localSum);
                }
            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long end = System.nanoTime();
        long result = method == 2 ? atomicTotal.get() : total;

        System.out.println("Method: " + (method == 0 ? "Unsafe" :
                method == 1 ? "Synchronized" : "AtomicLong"));
        System.out.println("Total: " + result);
        System.out.println("Time in milliseconds: " + (end - start) / 1000000.0);
        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Expected total: " + ((long) SIZE * (SIZE + 1) / 2));
        runTest(0);
        runTest(1);
        runTest(2);
    }
}
