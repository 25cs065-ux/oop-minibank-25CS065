package service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;
import persistence.Repository;
import persistence.TransactionLog;
import exception.BankException;
import exception.AccountNotFoundException;
import exception.InvalidAmountException;
import util.Validator;

public class BankService {
    private final Repository<Account> repository;
    private final Path logFile;

    public BankService(Repository<Account> repository, Path logFile) {
        this.repository = repository;
        this.logFile = logFile;
    }

    public Account openAccount(String ownerName, String type, long balance, long limit)
            throws BankException, IOException {

        if (!Validator.isValidOwnerName(ownerName)) {
            throw new BankException("Invalid owner name.");
        }

        if (balance < 0 || limit < 0) {
            throw new InvalidAmountException(balance < 0 ? balance : limit);
        }

        Account account;

        switch (type.toUpperCase()) {
            case "SAVINGS":
                account = new SavingsAccount(ownerName, balance, limit);
                break;
            case "CURRENT":
                account = new CurrentAccount(ownerName, balance, limit);
                break;
            case "FIXED":
                account = new FixedDepositAccount(ownerName, balance);
                break;
            default:
                throw new BankException("Invalid account type.");
        }

        while (repository.findById(account.getAccountNumber()) != null) {
            switch (type.toUpperCase()) {
                case "SAVINGS":
                    account = new SavingsAccount(ownerName, balance, limit);
                    break;
                case "CURRENT":
                    account = new CurrentAccount(ownerName, balance, limit);
                    break;
                default:
                    account = new FixedDepositAccount(ownerName, balance);
            }
        }

        repository.save(account);
        return account;
    }

    public void deposit(String accountNumber, long amount)
            throws BankException, IOException {

        Account account = repository.findById(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }

        account.deposit(amount);
        TransactionLog.append(logFile, "DEPOSIT " + accountNumber + " " + amount);
    }

    public void withdraw(String accountNumber, long amount)
            throws BankException, IOException {

        Account account = repository.findById(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }

        account.withdraw(amount);
        TransactionLog.append(logFile, "WITHDRAW " + accountNumber + " " + amount);
    }

    public void transfer(String from, String to, long amount)
            throws BankException, IOException {

        Account source = repository.findById(from);
        Account destination = repository.findById(to);

        if (source == null) {
            throw new AccountNotFoundException(from);
        }

        if (destination == null) {
            throw new AccountNotFoundException(to);
        }

        source.transfer(destination, amount);

        TransactionLog.append(logFile, "WITHDRAW " + from + " " + amount);
        TransactionLog.append(logFile, "DEPOSIT " + to + " " + amount);
    }

    public String statement(String accountNumber) throws BankException {
        Account account = repository.findById(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }

        return StatementFormatter.buildStatement(account);
    }

    public ArrayList<Account> listAccounts() {
        return repository.findAll();
    }

    public ArrayList<Account> searchByOwnerName(String ownerName) {
        ArrayList<Account> result = new ArrayList<>();

        for (Account account : repository.findAll()) {
            if (account.getOwnerName().equalsIgnoreCase(ownerName)) {
                result.add(account);
            }
        }

        return result;
    }

    public void loadAccounts(Account[] accounts) {
    repository.saveAll(accounts);
    Account.updateCounter(accounts);
}

    public Account[] getAccounts() {
        return repository.findAll().toArray(new Account[0]);
    }
}