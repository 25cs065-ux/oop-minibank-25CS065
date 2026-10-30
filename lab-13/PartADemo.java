public class PartADemo {
    public static void main(String[] args) {

        Box<Integer> box1 = new Box<>();
        box1.set(100);
        System.out.println("Box Integer: " + box1.get());

        Box<String> box2 = new Box<>();
        box2.set("Java Generics");
        System.out.println("Box String: " + box2.get());

        Pair<Integer, String> pair = new Pair<>(101, "Khushi");
        System.out.println("Pair First: " + pair.getFirst());
        System.out.println("Pair Second: " + pair.getSecond());

        Integer[] numbers = {10, 20, 30};
        String[] names = {"Java", "Python", "C++"};

        System.out.println("First Number: " + GenericArray.first(numbers));
        System.out.println("First Name: " + GenericArray.first(names));

        Integer[] integers = {10, 20, 30};
        Double[] doubles = {10.5, 20.5, 30.5};
        Long[] longs = {100L, 200L, 300L};

        System.out.println("Integer Average: " + BoundedStats.average(integers));
        System.out.println("Double Average: " + BoundedStats.average(doubles));
        System.out.println("Long Average: " + BoundedStats.average(longs));

        GenericStack<Integer> intStack = new GenericStack<>(5);
        intStack.push(10);
        intStack.push(20);
        intStack.push(30);

        System.out.println("Integer Stack Pop: " + intStack.pop());

        GenericStack<String> stringStack = new GenericStack<>(5);
        stringStack.push("Java");
        stringStack.push("Python");

        System.out.println("String Stack Pop: " + stringStack.pop());
    }
}