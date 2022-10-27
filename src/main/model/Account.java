package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an Account with an id, name, and current balance (in dollars)
public class Account implements Writable {
    private int accountId;
    private String accountName;
    private double accountBalance;

    // REQUIRES: accountId >= 0 and isn't associated with any other account AND
    //           accountName length is non-zero AND initial balance >= 0
    // EFFECTS: constructs an account with account id set to given id; account name set to given name;
    //          account balance set to given initialBalance
    public Account(Integer id, String name, double initialBalance) {
        accountId = id;
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

    // EFFECTS: returns string representation of this account
    public String toString() {
        return accountId + ":" + accountName + ":" + accountBalance;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", accountId);
        json.put("name", accountName);
        json.put("balance", accountBalance);
        return json;
    }
}
