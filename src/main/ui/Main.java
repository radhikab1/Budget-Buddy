package ui;

import java.io.FileNotFoundException;

// Sources Used:
//
// https://github.students.cs.ubc.ca/CPSC210/TellerApp
// Used to determine fields of the accounts class (e.g. how to assign Ids to accounts), and
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
public class Main {
    public static void main(String[] args) {
        try {
            new FinancialManagementApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
