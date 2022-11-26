package ui.gui;

import model.Account;
import model.AccountsList;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents Financial Management app with its GUI
public class FinancialManagementAppGUI extends JFrame {
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 600;

    private JFrame frame;
    private JLabel displayLabel;

    private AccountsList accountsList;

    // EFFECTS: Constructs Financial Management app with an accountsList and adds to a visible JFrame a panel
    // containing menuPanel with buttons that respond to events and a scroll pane with displayPanel that displays
    // the accounts already added to accountsList
    public FinancialManagementAppGUI() {
        accountsList = new AccountsList("Radhika's AccountsList");
        frame = new JFrame("Financial Management App");
        setFrame();
        displayLabel = new JLabel();
        frame.add(createPanel());
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets frame of Financial Management GUI. Prints log of events when window is closed.
    public void setFrame() {
        frame.setLayout(new BorderLayout());
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event next: EventLog.getInstance()) {
                    System.out.println(next.toString() + "\n");
                }
                System.exit(0);
            }
        });
    }

    // EFFECTS: creates a panel containing menuPanel with buttons that respond to events and a scroll pane with
    // displayPanel that displays the accounts already added to accountsList
    public JPanel createPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(createMenuPanel());
        JScrollPane scroller = new JScrollPane(createDisplayPanel());
        panel.add(scroller);
        return panel;
    }

    // EFFECTS: returns visible menu panel with a label and buttons that respond to events
    public JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(new GridLayout(6, 1));
        JLabel label = new JLabel("MENU", SwingConstants.CENTER);
        menuPanel.add(label);
        menuPanel.setVisible(true);

        JButton addAccountButton = new JButton(new AddAccountAction());
        JButton removeAccountButton = new JButton(new RemoveAccountAction());
        JButton makeTransactionButton = new JButton(new MakeTransactionAction());
        JButton saveDataButton = new JButton(new SaveDataAction());
        JButton loadDataButton = new JButton(new LoadDataAction());

        menuPanel.add(addAccountButton);
        menuPanel.add(removeAccountButton);
        menuPanel.add(makeTransactionButton);
        menuPanel.add(saveDataButton);
        menuPanel.add(loadDataButton);

        return menuPanel;
    }

    // Represents action to be taken when user wants to add an account to accountsList in the system
    private class AddAccountAction extends AbstractAction {

        // EFFECTS: constructs add account action
        AddAccountAction() {
            super("Add Account");
        }

        // EFFECTS: prompts user to enter accountId, accountName, and accountBalance of account to add to accountsList
        // via pop-up box. If accountId already exists in accountsList, gives pop-up box error message, otherwise adds
        // account to accountsList and gives pop-up box confirmation message.
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
            setDisplayLabel();
        }
    }

    // Represents action to be taken when user wants to remove account from accountsList in the system
    private class RemoveAccountAction extends AbstractAction {

        // EFFECTS: constructs remove account action
        RemoveAccountAction() {
            super("Remove Account");
        }

        // EFFECTS: prompts user to enter accountId of account to remove from accountsList via pop-up box.
        // If accountId does not exist in accountsList, gives pop-up box error message, otherwise removes
        // account from accountsList and gives pop-up box confirmation message.
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
            setDisplayLabel();
        }
    }

    // Represents action to be taken when user wants to make a transaction to the accountsList in the system
    private class MakeTransactionAction extends AbstractAction {

        // EFFECTS: constructs make transaction action
        MakeTransactionAction() {
            super("Make Transaction");
        }

        // EFFECTS: prompts user to enter accountId of account to make transaction to via pop-up box.
        // If accountId exists, prompts user to choose to make credit or debt via pop-up box then updates displayLabel,
        // otherwise gives pop-up box error message that accountId does not exist.
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
                setDisplayLabel();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Invalid account id entered. Account id doesn't exist!", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        // REQUIRES: account != null
        // EFFECTS: prompts user to select whether they want to make a credit or debt to the given account,
        //          and processes the transaction (credit or debt) as chosen by the user,
        //          gives pop-up error message if credit or debt not chosen
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
        // EFFECTS: prompts user to enter the amount to credit; if amount < 0.0 gives pop -up error message, otherwise
        // makes credit to given account and gives pop-up confirmation message
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
        // EFFECTS: prompts user to enter the amount to debt; if debt < 0.0 or if debt > getAccountBalance()
        // gives pop-up error message, otherwise makes debt to given account and gives pop-up confirmation message
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

    // Represents action to be taken when user wants to save data to file in the system
    private class SaveDataAction extends AbstractAction {
        private static final String JSON_STORE = "./data/accountslist.json";
        private JsonWriter jsonWriter;
        private JsonReader jsonReader;

        // EFFECTS: constructs save data action with JsonWriter and JsonReader
        SaveDataAction() {
            super("Save Data");
            jsonWriter = new JsonWriter(JSON_STORE);
            jsonReader = new JsonReader(JSON_STORE);
        }

        // EFFECTS: saves the accounts list to file
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

    // Represents action to be taken when user wants to load data from file in the system
    private class LoadDataAction extends AbstractAction {
        private static final String JSON_STORE = "./data/accountslist.json";
        private JsonWriter jsonWriter;
        private JsonReader jsonReader;

        // EFFECTS: constructs load data action with JsonWriter and JsonReader
        LoadDataAction() {
            super("Load Data");
            jsonWriter = new JsonWriter(JSON_STORE);
            jsonReader = new JsonReader(JSON_STORE);
        }

        // EFFECTS: loads the accounts list from file
        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                accountsList = jsonReader.read();
                System.out.println("Loaded " + accountsList.getName() + " from " + JSON_STORE);
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
            setDisplayLabel();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates display panel to display id, name, and balance of accounts already added to accountsList,
    // otherwise if no accounts are added to accountsList, notifies that there are no existing accounts
    public JPanel createDisplayPanel() {
        JPanel displayPanel = new JPanel(new GridLayout(1, 1));

        setDisplayLabel();
        displayPanel.add(displayLabel);
        displayPanel.setVisible(true);

        return displayPanel;
    }

    // EFFECTS: creates label for displayPanel with id, name, and balance of accounts already added to accountsList,
    // otherwise if no accounts are added to accountsList, notifies that there are no existing accounts.
    public void setDisplayLabel() {
        String displayPanelText;

        if (accountsList.getAccounts().size() == 0) {
            displayPanelText = "\nThere are no existing accounts.";
        } else {
            displayPanelText = "<html> The following are existing accounts:";
            for (Account account: accountsList.getAccounts()) {
                displayPanelText += "<br>Account ID: " + account.getAccountId() + ", Account Name: "
                        + account.getAccountName() + ", Account Balance: " + account.getAccountBalance();
            }
            displayPanelText += "</html>";
        }

        displayLabel.setText(displayPanelText);
    }

    // EFFECTS: runs main method with splash screen and financial management app GUI
    public static void main(String[] args) {
        new SplashScreen();
        new FinancialManagementAppGUI();
    }
}
