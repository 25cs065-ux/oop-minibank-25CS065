import model.Account;
import model.CurrentAccount;
import model.Customer;
import model.FixedDepositAccount;
import model.SavingsAccount;
import service.StatementFormatter;
import util.Command;
import util.CommandParser;
import util.Validator;
import util.AnnotationValidator;
import exception.BankException;
import exception.InsufficientFundsException;
import service.BankSession;
import persistence.StatePersister;
import persistence.TransactionLog;
import persistence.ReportGenerator;
import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

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

        Account[] accounts = {a1, a2, a3};

        System.out.println();
        System.out.println("Interest Rates:");

        for (Account account : accounts) {
            System.out.println(
                account.getOwnerName() + " -> " + account.interestRate() + "%"
            );
        }

        if (a3 instanceof FixedDepositAccount) {
            System.out.println();
            System.out.println("a3 is a Fixed Deposit Account.");
        }

        System.out.println();
System.out.println("Withdrawal Tests:");

try {
    System.out.println("Savings withdrawal: " + a1.withdraw(2000));
} catch (BankException e) {
    System.out.println(e.getMessage());
}

try {
    System.out.println("Current withdrawal: " + a2.withdraw(7000));
} catch (BankException e) {
    System.out.println(e.getMessage());
}

try {
    System.out.println("Fixed Deposit withdrawal: " + a3.withdraw(1000));
} catch (BankException e) {
    System.out.println(e.getMessage());
}

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

        System.out.println("Mobile valid: " + Validator.isValidMobile("9876543210"));
        System.out.println("Mobile invalid: " + Validator.isValidMobile("12345"));
        System.out.println("Email valid: " + Validator.isValidEmail("riya@example.com"));
        System.out.println("Email invalid: " + Validator.isValidEmail("riya@"));
        System.out.println("PAN valid: " + Validator.isValidPan("ABCDE1234F"));
        System.out.println("PAN invalid: " + Validator.isValidPan("ABC123"));
        System.out.println("IFSC valid: " + Validator.isValidIfsc("SBIN0001234"));
        System.out.println("IFSC invalid: " + Validator.isValidIfsc("SBIN123"));

        Command command = CommandParser.parse("DEPOSIT AC0001 500");

        System.out.println();
        System.out.println("Command type: " + command.type());
        System.out.println("Account number: " + command.accountNumber());
        System.out.println("Amount: " + command.amount());
        System.out.println();
        System.out.println(StatementFormatter.buildStatement(a1));

        System.out.println();
        System.out.println("Annotation Validation:");

        Account invalidAccount = new Account("Test User", -100) {
            @Override
            public double interestRate() {
                return 0;
            }

            @Override
            public boolean canWithdraw(long amount) {
                return false;
            }
        };

        String[] invalidErrors = AnnotationValidator.validate(invalidAccount);

        for (String error : invalidErrors) {
            System.out.println(error);
        }

        Account validAccount = new SavingsAccount("Valid User", 1000, 500);

        String[] validErrors = AnnotationValidator.validate(validAccount);

        if (validErrors.length == 0) {
            System.out.println("Valid account: no validation errors");
        } else {
            for (String error : validErrors) {
                System.out.println(error);
            }
        }
    System.out.println();
System.out.println("Exception and Transfer Tests:");

Account source = new SavingsAccount("Test Source", 1000, 0);
Account destination = new SavingsAccount("Test Destination", 500, 0);

try {
    source.withdraw(5000);
} catch (InsufficientFundsException e) {
    System.out.println(e.getMessage());
    System.out.println("Shortfall field: Rs. " + e.getShortfall());
} catch (BankException e) {
    System.out.println(e.getMessage());
}

try {
    source.transfer(destination, 300);
} catch (BankException e) {
    System.out.println(e.getMessage());
}

System.out.println("Source balance: Rs. " + source.getBalance());
System.out.println("Destination balance: Rs. " + destination.getBalance());

try (BankSession session = new BankSession()) {
    System.out.println("Performing a banking operation.");
}
System.out.println();
System.out.println("PART B: PERSISTENCE AND REPORTING");

try {
    Path stateFile = Path.of("data", "accounts.dat");
    StatePersister.save(accounts, stateFile);

    Account[] loadedAccounts = StatePersister.load(stateFile);

    System.out.println("Accounts saved and loaded successfully:");

    for (Account account : loadedAccounts) {
        System.out.println(account);
    }

    Path logsFolder = Path.of("data", "logs");
    Path logFile = logsFolder.resolve("transactions.log");

    TransactionLog.append(logFile, "DEPOSIT AC0001 500");
    TransactionLog.append(logFile, "WITHDRAW AC0002 200");

    Path reportFile = Path.of("data", "end-of-day-report.txt");
    String report = ReportGenerator.generate(logsFolder, reportFile);

    System.out.println();
    System.out.println(report);
    System.out.println("Report saved to: " + reportFile);
} catch (IOException | ClassNotFoundException e) {
    System.out.println("Persistence error: " + e.getMessage());
}
}
}
