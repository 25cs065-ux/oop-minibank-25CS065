
class MyResource implements AutoCloseable {
    public MyResource() {
        System.out.println("Resource opened.");
    }

    public void performTask() {
        System.out.println("Performing task.");
    }

    @Override
    public void close() {
        System.out.println("Resource closed.");
    }
}

public class AutoCloseableDemo {
    public static void main(String[] args) {
        try (MyResource resource = new MyResource()) {
            resource.performTask();
            throw new RuntimeException("Original task error.");
        } catch (RuntimeException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
    }
}
