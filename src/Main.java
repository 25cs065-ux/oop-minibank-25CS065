public class Main {
    public static void main(String[] args) {

        // Creating different types of accounts
        Account a1 = new SavingsAccount("Riya", 4000, 1000);
        Account a2 = new CurrentAccount("Aman", 4000, 5000);
        Account a3 = new FixedDepositAccount("Neha", 10000);

        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);

        System.out.println();

        System.out.println("a1 equals a2 -> " + a1.equals(a2));

        System.out.println("a1 equals a1 -> " + a1.equals(a1));

        Object obj = a1;
        System.out.println("obj instanceof Account -> " + (obj instanceof Account));

        // Runtime polymorphism using Account array
        Account[] accounts = {a1, a2, a3};

        System.out.println();
        System.out.println("Interest Rates:");

        for (Account account : accounts) {
            System.out.println(
                account.getOwnerName() + " -> " + account.interestRate() + "%"
            );
        }

        // instanceof check
        if (a3 instanceof FixedDepositAccount) {
            System.out.println();
            System.out.println("a3 is a Fixed Deposit Account.");
        }

        // Withdrawal tests
        System.out.println();
        System.out.println("Withdrawal Tests:");

        System.out.println(
            "Savings withdrawal: " + a1.withdraw(2000)
        );

        System.out.println(
            "Current withdrawal: " + a2.withdraw(7000)
        );

        System.out.println(
            "Fixed Deposit withdrawal: " + a3.withdraw(1000)
        );

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

        System.out.println();

        // Validator tests
        System.out.println("Mobile valid: " + Validator.isValidMobile("9876543210"));
        System.out.println("Mobile invalid: " + Validator.isValidMobile("12345"));

        System.out.println("Email valid: " + Validator.isValidEmail("riya@example.com"));
        System.out.println("Email invalid: " + Validator.isValidEmail("riya@"));

        System.out.println("PAN valid: " + Validator.isValidPan("ABCDE1234F"));
        System.out.println("PAN invalid: " + Validator.isValidPan("ABC123"));

        System.out.println("IFSC valid: " + Validator.isValidIfsc("SBIN0001234"));
        System.out.println("IFSC invalid: " + Validator.isValidIfsc("SBIN123"));

        // Command parser test
        Command command = CommandParser.parse("DEPOSIT AC0001 500");

        System.out.println();
        System.out.println("Command type: " + command.type());
        System.out.println("Account number: " + command.accountNumber());
        System.out.println("Amount: " + command.amount());
        System.out.println();
        System.out.println(StatementFormatter.buildStatement(a1));
    }
}