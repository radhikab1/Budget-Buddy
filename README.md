# My Personal Project: A Financial Management Tool

## About the Application
This purpose of this application is to track one's *budget* and manage one's *finances*. Specifically, the application 
will allow its user to create a new account and add it to a list of accounts. The user will then be able to view the 
list of all accounts in the application and there total **account balance**. The user will also be able to select an 
account and remove it from the list of all accounts in the application. Furthermore, the user will have an option to 
make transactions (credits/debts) to a certain account which updates their account balance.

This application will help serve students attending school, working adults, or even the senior population. Many of us 
are occupied with several tasks and responsibilities on a daily basis, such that it becomes challenging to keep track of
everything, including our finances. This application will help serve such people by helping keep record of their daily 
transactions and account balance. Furthermore, many people have **multiple accounts** in various financial institution; 
another added benefit of this application is that it will allow them to view their transaction records and account 
balances from various financial institutions in a collective manner - something previous applications miss out on. Most 
importantly, this project is of interest to me as it will help promote *financial literacy* and *financial security* for
the public as they will be able to view their daily transactions and account balance in an **accessible 
manner**, and it will allow them to form budgeting strategies around that information. As a student occupied with 
several responsibilities and tasks, I understand how difficult, yet how important it is to keep track of such 
information, which is what motivates me to create this application.

## User Stories
The following are some **user stories** to describe how someone can use this application:
- As a user, I want to be able to view the list of accounts/login IDs in my budget tracking tool with their Ids, names,
  and respective balances
- As a user, I want to be able to add a new account with a specific login ID, name, and balance to the list of accounts
- As a user, I want to be able to remove an existing account from the list of accounts
- As a user, I want to be able to select a specific account and add a credit to that account
- As a user, I want to be able to select a specific account and add a debt to that account
- As a user, I want to be able to save my accounts list to file.
- As a user, I want to be able to load my accounts list from file.

## Future Changes
- To increase cohesion in my project and to make responsibilities of classes more specific, I would refactor the private 
class MakeTransactionAction by splitting it up into two different classes with one that performs a credit and another 
that performs a debt. In addition to making the functionalities of classes more specific, this would make the code easier to 
understand.
- To reduce duplication in the code, I would extract common behaviour related to some of the dialog boxes (e.g. the 
"Transaction Processed" confirmation message in the makeCredit() and makeDebt() methods in the 
FinancialManagementAppGUI) into a common method.
- To make the createMenuPanel() method in the FinancialManagementApp easier to understand, I would extract the code that
creates new buttons and deals with the event handling into a new method.
- Another way I would refactor the code is by including exception handling of certain cases such as when the user enters
an incorrect type for a certain value, when the user inputs a negative dollar amount as the initial balance, if the user
enters a negative amount to make a transaction with, or if the user enters an amount greater than the balance to make a 
debt. This would reduce duplications in certain methods by extracting behaviour related to what to do when an exceptional case 
is encountered and would improve functionality of the code by preventing it from crashing in exceptional cases.
- An additional refactoring I would perform is renaming the AccountsList class to something more relevant and 
descriptive rather than simply having a name that states it is a list of accounts.

# Sources Used:

- https://github.students.cs.ubc.ca/CPSC210/TellerApp
  <br>Used to determine fields of the accounts class, and
  some functionality of the UI (such as how to run the program and process user input/commands,
  how to use the scanner, how to select a specific account, and how to display a menu)

- https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters
  <br>Used to determine how to lookup an account by a field (such as the account Id)

- https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
  <br>Used for JsonReader and JsonWriter and their tests, Writable, Main,
  and loadAccountsList() and saveAccountsList() in FinancialManagementApp

- https://www.javatpoint.com/java-jframe
  <br>Used to determine functionality of JFrame

- https://www.javatpoint.com/java-jpanel, https://www.codejava.net/java-se/swing/jpanel-basic-tutorial-and-examples
  <br>Used to determine functionality of JPanel

- https://stackoverflow.com/questions/41172472/how-to-make-jpanel-scrollable
  <br>Used to determine how to create a scrollPane

- https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
  <br>Used to determine how to perform event handling and add buttons, and how to display pop-up boxes
  (question messages, information messages, error messages)

- https://stackoverflow.com/questions/19506769/how-to-center-jlabel-in-jframe-swing
  <br>Used to determine how to center JLabel

- https://www.tutorialsfield.com/java-splash-screen-with-progress-bar/
  <br>Used for creating SplashScreen with image, progressbar, and loading message

- https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pngkey.com%2Fdetail%2Fu2e6r5q8e6y3o0y3_free-shipping-on-a-per-product-basis-in%2F&psig=AOvVaw3J5rtwrDi_HiQSnUnAx9bM&ust=1668276375258000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCLCS3aLurPsCFQAAAAAdAAAAABAD
  <br>Splash screen image

- https://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
  <br>Used to determine how to resize image to fit JLabel

- https://stackoverflow.com/questions/17456401/how-to-update-a-jlabel-text
  <br>Used to determine how to update JLabel

- https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
  <br>Used EventLog, Event classes. Derived test cases for Event/EventLog from EventLogTest. 
  Used to determine how to log events and how to print log of events to screen.

- https://kodejava.org/how-do-i-handle-a-window-closing-event/
  <br>https://stackoverflow.com/questions/60516720/java-how-to-print-message-when-a-jframe-is-closed
  <br>Used to determine how to create a window listener and how to perform action when window is closed.
