package model;

import java.util.ArrayList;
import java.util.List;

// Represents a list of Accounts
public class Accounts {
    private List<Account> accounts;

    public Accounts() {
        accounts = new ArrayList<>();
    }

    //REQUIRES: account != null
    //MODIFIES: this
    //EFFECTS: adds account to accounts
    public void addAccount(Account account) {
        accounts.add(account);
    }

    //REQUIRES: account != null
    //MODIFIES: this
    //EFFECTS: removes account from accounts
    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    //EFFECTS: returns a list of the Ids of each account in accounts
    public List<Integer> getAccountIds() {
        List<Integer> accountIds = new ArrayList<>();
        for (Account account: getAccounts()) {
            accountIds.add(account.getAccountId());
        }
        return accountIds;
    }
}
