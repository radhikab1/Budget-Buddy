package ui;

import model.Account;
import model.Accounts;

import java.util.Scanner;

// Financial management application
public class FinancialManagementApp {
    private Scanner input;
    private Accounts accounts;

    // EFFECTS: constructs an empty accounts list then runs the financial management application
    public FinancialManagementApp() {
        accounts = new Accounts();
        runFinancialManagement();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runFinancialManagement() {
        boolean keepGoing = true;
        String command = null;

        input = new Scanner(System.in);
        input.useDelimiter("\n");

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nThank you for using the Financial Management App! See you next time!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("v")) {
            printAccountsAndBalance();
        } else if (command.equals("t")) {
            makeTransaction();
        } else if (command.equals("a")) {
            addAccount();
        } else if (command.equals("r")) {
            removeAccount();
        } else {
            System.out.println("\nYour selection was invalid! Please try again...");
        }
    }

    //EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nPlease select from the following options:");
        System.out.println("\tv -> View accounts");
        System.out.println("\tt -> Select an account to make a transaction (credit or debt)");
        System.out.println("\ta -> Add an account");
        System.out.println("\tr -> Remove an account");
        System.out.println("\tq -> Quit");
    }


    // EFFECTS: prints out statement telling the user there are no accounts if there aren't any,
    //          otherwise prints out all accounts with their Ids, names, and balances
    private void printAccountsAndBalance() {
        if (accounts.getAccounts().size() == 0) {
            System.out.println("\nThere are no existing accounts.");
        } else {
            System.out.println("\nThe following are existing accounts:");
            for (Account account: accounts.getAccounts()) {
                System.out.println("Account ID: " + account.getAccountId() + ", Account Name: "
                        + account.getAccountName() + ", Account Balance: " + account.getAccountBalance());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: prints list of all accounts with their Ids, names, and balances, and if accounts isn't empty,
    //          prompts user to select an account to make a credit or debt to, select whether they want to
    //          make a credit or debt, and select the amount to credit or debt,
    //          then make the transaction (credit or debt)
    private void makeTransaction() {
        printAccountsAndBalance();
        if (accounts.getAccounts().size() > 0) {
            chooseCreditOrDebt(selectAccount());
        }
    }

    // EFFECTS: prompts user to select an account by its Id and returns it
    private Account selectAccount() {
        Integer selection = 0;

        while (!(accounts.getAccountIds().contains(selection))) {
            System.out.println("\nPlease enter an account ID to select an account: ");
            selection = Integer.parseInt(input.next());
        }
        return lookupAccountById(selection);
    }

    // REQUIRES: accountID > 1
    // EFFECTS: looks up account in accounts by given accountId and returns the respective account
    private Account lookupAccountById(Integer accountId) {
        Account accountToLookup = null;
        for (Account account: accounts.getAccounts()) {
            if (account.getAccountId() == accountId) {
                accountToLookup = account;
            }
        }
        return accountToLookup;
    }

    // REQUIRES: account != null
    // MODIFIES: this
    // EFFECTS: prompts user to select whether they want to make a credit or debt to the given account,
    //          and processes the transaction (credit or debt) as chosen by the user
    private void chooseCreditOrDebt(Account account) {
        String selection = "";

        while (!(selection.equals("c") || selection.equals("d"))) {
            System.out.println("c for credit");
            System.out.println("d for debt");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("c")) {
            makeCredit(account);
        } else {
            makeDebt(account);
        }
    }

    // REQUIRES: account != null
    // MODIFIES: this
    // EFFECTS: prompts user to enter the amount to credit and makes credit to given account
    private void makeCredit(Account account) {
        System.out.println("\nEnter credit amount: ");
        double amount = input.nextDouble();

        if (amount >= 0.0) {
            account.makeCredit(amount);
        } else {
            System.out.println("\nInvalid amount, credit cannot be negative...");
        }
    }

    // REQUIRES: account != null
    // MODIFIES: this
    // EFFECTS: prompts user to enter the amount to debt and makes debt to given account
    private void makeDebt(Account account) {
        System.out.println("\nEnter debt amount: ");
        double amount = input.nextDouble();

        if (amount >= 0.0 && amount <= account.getAccountBalance()) {
            account.makeDebt(amount);
        } else {
            System.out.println("\nInvalid amount, debt amount cannot be negative and can't be more than balance...");
        }
    }

    // MODIFIES: this
    // EFFECTS:
    private void addAccount() {
        System.out.println("\nEnter name of account to add: ");
        String name = input.next();
        System.out.println("\nEnter initial balance on account: ");
        double initialBalance = input.nextDouble();

        if (initialBalance >= 0.0) {
            Account account = new Account(name, initialBalance);
            accounts.addAccount(account);
        } else {
            System.out.println("\nInvalid initial balance, initial balance cannot be zero!");
        }
    }

    // MODIFIES: this
    // EFFECTS: prints list of accounts with their Ids, names, and balances, and if accounts isn't empty,
    //          removes account by selecting account by given Id
    private void removeAccount() {
        printAccountsAndBalance();
        if (accounts.getAccounts().size() > 0) {
            accounts.removeAccount(selectAccount());
        }
    }
}
