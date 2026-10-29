import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class SerializationDemo {
    public static void main(String[] args) {
        Student[] students = {
    new Student(1, "Khushi", "pass123"),
    new Student(2, "Rahul", "pass456"),
    new Student(3, "Priya", "pass789")
};

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("students.dat"))) {
            out.writeObject(students);
            System.out.println("Students saved successfully.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("students.dat"))) {
            Student[] loadedStudents = (Student[]) in.readObject();
            System.out.println("Loaded students:");
            for (Student student : loadedStudents) {
                student.display();
                System.out.println();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}