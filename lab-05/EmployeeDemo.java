abstract class Employee {
    public String name;
    public int id;

    Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public abstract double monthlysalary();
}

class FullTime extends Employee {
    public int fixed;

    FullTime(int fixed, String name, int id) {
        super(name, id);
        this.fixed = fixed;
    }

    @Override
    public double monthlysalary() {
        return fixed;
    }
}

class PartTime extends Employee {
    public int hrs;
    public int rate;

    PartTime(int hrs, int rate, String name, int id) {
        super(name, id);
        this.hrs = hrs;
        this.rate = rate;
    }

    @Override
    public double monthlysalary() {
        return hrs * rate;
    }
}

class Intern extends Employee {
    int stipend;

    Intern(int stipend, String name, int id) {
        super(name, id);
        this.stipend = stipend;
    }

    @Override
    public double monthlysalary() {
        return stipend;
    }
}

public class EmployeeDemo {
    public static void main(String[] args) {

        Employee[] employees = {
            new FullTime(50000, "nandini", 1),
            new PartTime(20, 200, "devanshi", 2),
            new Intern(10000, "panvi", 3)
        };

        for (Employee e : employees) {
            System.out.println(
                "Name: " + e.name +
                " ID: " + e.id +
                " Monthly Salary: " + e.monthlysalary()
            );

            if (e instanceof Intern) {
                System.out.println("Intern Employee");
            }
        }
    }
}