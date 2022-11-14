package ui.gui;

import model.Account;
import model.AccountsList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Sources Used:
//
// https://github.students.cs.ubc.ca/CPSC210/TellerApp
// Used to determine fields of the accounts class, and
// some functionality of the UI (such as how to run the program and process user input/commands,
// how to use the scanner, how to select a specific account, and how to display a menu)
//
// https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters
// Used to determine how to lookup an account by a field (such as the account Id)
//
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Used for JsonReader and JsonWriter and their tests, Writable, Main,
// and loadAccountsList() and saveAccountsList() in FinancialManagementApp
//
// Represents main class. Executes main method and instantiates FinancialManagementApp
public class FinancialManagementAppGUI extends JFrame {
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 600;

    private JFrame frame;

    private AccountsList accountsList;

    public FinancialManagementAppGUI() {
        frame = new JFrame("Financial Management App");
        setFrame();
    }

    public void setFrame() {
        frame.setLayout(new BorderLayout());
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(addMenuPanel());

        frame.setVisible(true);

        accountsList = new AccountsList("Radhika's AccountsList");
    }

    public JPanel addMenuPanel() {
        JPanel menuPanel = new JPanel(new GridLayout(7, 1));
        JLabel label = new JLabel("MENU", SwingConstants.CENTER);
        menuPanel.add(label);
        menuPanel.setVisible(true);

        JButton viewAccountsButton = new JButton(new ViewAccountsAction());
        JButton makeTransactionButton = new JButton(new MakeTransactionAction());
        JButton addAccountButton = new JButton(new AddAccountAction());
        JButton removeAccountButton = new JButton(new RemoveAccountAction());
        JButton saveDataButton = new JButton(new SaveDataAction());
        JButton loadDataButton = new JButton(new LoadDataAction());

        menuPanel.add(viewAccountsButton);
        menuPanel.add(makeTransactionButton);
        menuPanel.add(addAccountButton);
        menuPanel.add(removeAccountButton);
        menuPanel.add(saveDataButton);
        menuPanel.add(loadDataButton);

        return menuPanel;
    }

    private class ViewAccountsAction extends AbstractAction {
        ViewAccountsAction() {
            super("View Accounts");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (accountsList.getAccounts().size() == 0) {
                JOptionPane.showMessageDialog(null,
                        "There are no existing accounts.", "System Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String message = "The following are existing accounts: ";
                for (Account account : accountsList.getAccounts()) {
                    message = message + "\nAccount ID: " + account.getAccountId() + ", Account Name: "
                            + account.getAccountName() + ", Account Balance: " + account.getAccountBalance();
                }
                JOptionPane.showMessageDialog(null, message, "View Accounts",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    private class MakeTransactionAction extends AbstractAction {

        MakeTransactionAction() {
            super("Make Transaction");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            Account selectedAccount = null;
            int accountId = Integer.parseInt((JOptionPane.showInputDialog(null,
                    "Enter Account Id:", "Enter Account Id", JOptionPane.QUESTION_MESSAGE)));
            if ((accountsList.getAccounts().size() > 0) && (accountsList.getAccountIds().contains(accountId))) {
                for (Account account : accountsList.getAccounts()) {
                    if (accountId == account.getAccountId()) {
                        selectedAccount = account;
                    }
                }
                chooseCreditOrDebt(selectedAccount);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Invalid account id entered. Account id doesn't exist!", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        // REQUIRES: account != null
        // MODIFIES: this
        // EFFECTS: prompts user to select whether they want to make a credit or debt to the given account,
        //          and processes the transaction (credit or debt) as chosen by the user
        private void chooseCreditOrDebt(Account account) {
            String selection = (JOptionPane.showInputDialog(null,
                    "Select Transaction Type (c for credit/d for debt):", "Select Transaction Type",
                    JOptionPane.QUESTION_MESSAGE));
            if (selection.equals("c") || selection.equals("d")) {
                if (selection.equals("c")) {
                    makeCredit(account);
                } else {
                    makeDebt(account);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Invalid selection!", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        // REQUIRES: account != null
        // MODIFIES: this
        // EFFECTS: prompts user to enter the amount to credit and makes credit to given account
        private void makeCredit(Account account) {
            double amount = Double.parseDouble(JOptionPane.showInputDialog(null,
                    "Enter credit amount:", "Enter Amount",
                    JOptionPane.QUESTION_MESSAGE));
            if (amount >= 0.0) {
                account.makeCredit(amount);
                JOptionPane.showMessageDialog(null,
                        "Transaction Processed!",
                        "Confirmation Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Invalid amount credit cannot be negative!", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        // REQUIRES: account != null
        // MODIFIES: this
        // EFFECTS: prompts user to enter the amount to debt and makes debt to given account
        private void makeDebt(Account account) {
            double amount = Double.parseDouble(JOptionPane.showInputDialog(null,
                    "Enter debt amount:", "Enter Amount",
                    JOptionPane.QUESTION_MESSAGE));
            if (amount >= 0.0 && amount <= account.getAccountBalance()) {
                account.makeDebt(amount);
                JOptionPane.showMessageDialog(null,
                        "Transaction Processed!",
                        "Confirmation Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Invalid amount, debt amount cannot be negative and can't be more than balance!",
                        "System Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class AddAccountAction extends AbstractAction {

        AddAccountAction() {
            super("Add Account");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            int accountId = Integer.parseInt((JOptionPane.showInputDialog(null,
                    "Enter Account Id:", "Enter Account Id", JOptionPane.QUESTION_MESSAGE)));
            String accountName = (JOptionPane.showInputDialog(null,
                    "Enter Account Name:", "Enter Account Name", JOptionPane.QUESTION_MESSAGE));
            double initialBalance = Double.parseDouble((JOptionPane.showInputDialog(null,
                    "Enter Initial Account Balance:", "Enter Initial Account Balance",
                    JOptionPane.QUESTION_MESSAGE)));

            if (initialBalance < 0.0) {
                JOptionPane.showMessageDialog(null,
                        "Invalid initial balance, initial balance cannot be zero!\"", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if (accountsList.getAccountIds().contains(accountId)) {
                JOptionPane.showMessageDialog(null,
                        "Invalid account id entered. Account id already exists!", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                Account account = new Account(accountId, accountName, initialBalance);
                accountsList.addAccount(account);
                JOptionPane.showMessageDialog(null,
                        "Account Added!", "Confirmation Message",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class RemoveAccountAction extends AbstractAction {

        RemoveAccountAction() {
            super("Remove Account");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            int accountId = Integer.parseInt((JOptionPane.showInputDialog(null,
                    "Enter account id of account to remove", "Enter Account Id of Account To Remove",
                    JOptionPane.QUESTION_MESSAGE)));
            if ((accountsList.getAccounts().size() > 0) && (accountsList.getAccountIds().contains(accountId))) {
                for (Account account : accountsList.getAccounts()) {
                    if (accountId == account.getAccountId()) {
                        accountsList.removeAccount(account);
                        break;
                    }
                }
                JOptionPane.showMessageDialog(null,
                        "Account Removed!", "Confirmation Message",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Invalid account id entered. Account id doesn't exist!", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class SaveDataAction extends AbstractAction {
        private static final String JSON_STORE = "./data/accountslist.json";
        private JsonWriter jsonWriter;
        private JsonReader jsonReader;

        SaveDataAction() {
            super("Save Data");
            jsonWriter = new JsonWriter(JSON_STORE);
            jsonReader = new JsonReader(JSON_STORE);
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                jsonWriter.open();
                jsonWriter.write(accountsList);
                jsonWriter.close();
                System.out.println("Saved " + accountsList.getName() + " to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    private class LoadDataAction extends AbstractAction {
        private static final String JSON_STORE = "./data/accountslist.json";
        private JsonWriter jsonWriter;
        private JsonReader jsonReader;

        LoadDataAction() {
            super("Load Data");
            jsonWriter = new JsonWriter(JSON_STORE);
            jsonReader = new JsonReader(JSON_STORE);
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                accountsList = jsonReader.read();
                System.out.println("Loaded " + accountsList.getName() + " from " + JSON_STORE);
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    public static void main(String[] args) {
        new SplashScreen();
        new FinancialManagementAppGUI();
    }
}
