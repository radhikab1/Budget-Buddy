package model;

// Represents an Account with an id, name, and current balance
public class Account {
    private static int nextAccountId = 1;
    private int accountId;
    private String accountName;
    private double accountBalance;

    // REQUIRES: accountName is not empty, initial balance >= 0
    // EFFECTS: constructs an account with account id set to a positive integer that isn't associated
    // with any other account; account name is set to given accountName; account balance set to given initialBalance
    public Account(String name, double initialBalance) {
        accountId = nextAccountId++;
        accountName = name;
        accountBalance = initialBalance;
    }

    // REQUIRES: creditAmount >= 0
    // MODIFIES: this
    // EFFECTS: adds creditAmount to accountBalance, then returns accountBalance
    public double makeCredit(double creditAmount) {
        accountBalance = accountBalance + creditAmount;
        return accountBalance;
    }


    // REQUIRES: debtAmount >= 0 AND debtAmount <= getAccountBalance()
    // MODIFIES: this
    // EFFECTS: subtracts debtAmount from accountBalance, then returns accountBalance
    public double makeDebt(double debtAmount) {
        accountBalance = accountBalance - debtAmount;
        return accountBalance;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public double getAccountBalance() {
        return accountBalance;
    }
}
