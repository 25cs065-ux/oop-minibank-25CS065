public class Student {
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + "}";
    }
}