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
// https://www.javatpoint.com/java-jframe
// Used to determine functionality of JFrame
//
// https://www.javatpoint.com/java-jpanel, https://www.codejava.net/java-se/swing/jpanel-basic-tutorial-and-examples
// Used to determine functionality of JPanel
//
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
// Used to determine how to perform event handling and add buttons, and how to display pop-up boxes
// (question messages, information messages, error messages)
//
// https://stackoverflow.com/questions/19506769/how-to-center-jlabel-in-jframe-swing
// Used to determine how to center JLabel
//
// https://www.tutorialsfield.com/java-splash-screen-with-progress-bar/
// Used for creating SplashScreen with image, progressbar, and loading message
//
// https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pngkey.com%2Fdetail%2Fu2e6r5q8e6y3o0y3_free-shipping-on-a-per-product-basis-in%2F&psig=AOvVaw3J5rtwrDi_HiQSnUnAx9bM&ust=1668276375258000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCLCS3aLurPsCFQAAAAAdAAAAABAD
// Splash screen image
//
// https://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
// Used to determine how to resize image to fit JLabel
//
// Represents Financial Management app with its GUI
public class FinancialManagementAppGUI extends JFrame {
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 600;

    private JFrame frame;
    private JLabel displayLabel;

    private AccountsList accountsList;

    // EFFECTS: Constructs Financial Management app with an accountsList and adds to a visible JFrame a panel
    // containing menuPanel with buttons that respond to events and a displayPanel that displays the accounts already
    // added to accountsList
    public FinancialManagementAppGUI() {
        accountsList = new AccountsList("Radhika's AccountsList");
        frame = new JFrame("Financial Management App");
        setFrame();
        displayLabel = new JLabel();
        frame.add(createPanel());
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets frame of Financial Management GUI
    public void setFrame() {
        frame.setLayout(new BorderLayout());
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    // EFFECTS: creates a panel containing menuPanel with buttons that respond to events and a displayPanel that
    // displays the accounts already added to accountsList
    public JPanel createPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(createMenuPanel());
        panel.add(createDisplayPanel());
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

    // Represents action to be taken when user wants to make a transaction to the accountsList in the system
    private class MakeTransactionAction extends AbstractAction {

        // EFFECTS: constructs make transaction action
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
