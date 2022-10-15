package model;

import java.util.ArrayList;
import java.util.List;

// Represents a list of Accounts (a Bank)
public class Bank {
    private List<Account> bank;

    public Bank() {
        bank = new ArrayList<>();
    }

    //REQUIRES: account != null
    //MODIFIES: this
    //EFFECTS: adds account from accounts collection
    public void addAccount(Account account) {
        bank.add(account);
    }

    //REQUIRES: account != null
    //MODIFIES: this
    //EFFECTS: removes account from accounts collection
    public void removeAccount(Account account) {
        bank.remove(account);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public List<Account> getAccounts() {
        return bank;
    }
}
