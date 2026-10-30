public class GenericStack<T> {
    private Object[] stack;
    private int top;

    public GenericStack(int size) {
        stack = new Object[size];
        top = -1;
    }

    public void push(T value) {
        if (top == stack.length - 1) {
            System.out.println("Stack is full");
            return;
        }

        stack[++top] = value;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (top == -1) {
            System.out.println("Stack is empty");
            return null;
        }

        T value = (T) stack[top];
        stack[top--] = null;
        return value;
    }

    public boolean isEmpty() {
        return top == -1;
    }
}