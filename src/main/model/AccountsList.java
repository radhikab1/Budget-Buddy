package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents an accounts list having a collection of Accounts
public class AccountsList implements Writable {
    private String name;
    private List<Account> accounts;

    // EFFECTS: constructs an account with a name and empty list of accounts
    public AccountsList(String name) {
        this.name = name;
        accounts = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds account to accounts. Logs an event indicating that account has been added.
    public void addAccount(Account account) {
        EventLog.getInstance().logEvent(new Event("Added Account: Account Id: " + account.getAccountId()
                + ", Account Name: " + account.getAccountName() + ",Account Balance: " + account.getAccountBalance()));
        accounts.add(account);
    }

    //MODIFIES: this
    //EFFECTS: removes account from accounts.  Logs an event indicating that account has been removed.
    public void removeAccount(Account account) {
        EventLog.getInstance().logEvent(new Event("Removed Account: Account Id: " + account.getAccountId()
                + ", Account Name: " + account.getAccountName() + ",Account Balance: " + account.getAccountBalance()));
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

    public String getName() {
        return name;
    }

    // EFFECTS: returns number of accounts in this accounts list
    public int numAccounts() {
        return accounts.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("accounts", accountsToJson());
        return json;
    }

    // EFFECTS: returns accounts in this accounts list as a JSON array
    private JSONArray accountsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Account a: accounts) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }
}
