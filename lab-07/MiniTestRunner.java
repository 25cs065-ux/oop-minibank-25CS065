import java.lang.reflect.Method;

public class MiniTestRunner {
    public static void main(String[] args) {
        SampleTests tests = new SampleTests();
        int count = 0;

        for (Method method : tests.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Run.class) && method.getParameterCount() == 0) {
                try {
                    method.invoke(tests);
                    count++;
                } catch (Exception e) {
                    System.out.println("Test failed: " + method.getName());
                }
            }
        }

        System.out.println("Total tests run: " + count);
    }
}