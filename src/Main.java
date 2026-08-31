public class Main {
    public static void main(String[] args) {

        Account a1 = new Account("Riya", 4000);
        Account a2 = new Account("Aman", 4000);

        System.out.println(a1);

        System.out.println("a1 equals a2 -> " + a1.equals(a2));

        System.out.println("a1 equals a1 -> " + a1.equals(a1));

       
        Object obj = a1;
        System.out.println("obj instanceof Account -> " + (obj instanceof Account));

        Customer c = new Customer(
                "Riya",
                "riya@example.com",
                "9876543210"
        );

        c.setAddress(
                new Customer.Address(
                        "12 MG Road",
                        "Nadiad",
                        "387001"
                )
        );

        System.out.println(c + " lives in " + c.getAddress().getCity());

        try {
            Customer copy = c.clone();

            System.out.println("Original Customer: " + c);
            System.out.println("Cloned Customer: " + copy);

        } catch (CloneNotSupportedException e) {
            System.out.println("Customer cannot be cloned.");
        }
    }
}
