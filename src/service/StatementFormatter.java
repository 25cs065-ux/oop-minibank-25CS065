package service;

import model.Account;

public class StatementFormatter {

    private StatementFormatter() {
    }

    public static String buildStatement(Account account) {

        return "Account Number: " + account.getAccountNumber()
                + "\nOwner Name: " + account.getOwnerName()
                + "\nBalance: Rs. " + account.getBalance()
                + "\nInterest Rate: " + account.interestRate() + "%";
    }
}