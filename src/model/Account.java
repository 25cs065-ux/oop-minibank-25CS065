
package model;

public abstract class Account implements Transactable, InterestBearing {

    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;
    private static int counter = 1;

    public Account(String ownerName, long openingBalance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    private static String generateAccountNumber() {
        return String.format("AC%04d", counter++);
    }

    @Override
    public void deposit(long amount) {
        if (amount > 0)
            balance += amount;
    }

    @Override
    public boolean withdraw(long amount) {
        if (amount > 0 && canWithdraw(amount)) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // Abstract methods required by Part B
    @Override
    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | " + balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Account))
            return false;

        Account other = (Account) o;

        return accountNumber.equals(other.accountNumber);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(accountNumber);
    }
}