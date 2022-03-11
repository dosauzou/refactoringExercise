package redmondn;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import models.*;

import java.util.Date;

public class Menu extends JFrame {

    JFrame f, f1;
    JLabel firstNameLabel, surnameLabel, pPPSLabel, dOBLabel;
    JTextField firstNameTextField, surnameTextField, pPSTextField, dOBTextField;
    JLabel customerIDLabel, passwordLabel;
    JTextField customerIDTextField, passwordTextField;
    Container content;
    Customer customer1;
    JPanel panel2;
    JButton addButton;
    String PPS, firstName, surname, DOB, CustomerID;
    private ArrayList<Customer> customerList = new ArrayList<Customer>();
    private int position = 0;
    private String password;
    private Customer customer = null;
    private CustomerAccount customerAccount = new CustomerAccount();

    public static void main(String[] args) {
        Menu driver = new Menu();
        driver.menuStart();
    }

    public static boolean isNumeric(String str)  // a method that tests if a string is numeric
    {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void menuStart() {
		   /*The menuStart method asks the user if they are a new customer, an existing customer or an admin. It will then start the create customer process
		  if they are a new customer, or will ask them to log in if they are an existing customer or admin.*/

        f = new JFrame("User Type");
        f.setSize(400, 300);
        f.setLocation(200, 200);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        JPanel userTypePanel = new JPanel();
        final ButtonGroup userType = new ButtonGroup();
        JRadioButton radioButton;
        userTypePanel.add(radioButton = new JRadioButton("Existing models.Customer"));
        radioButton.setActionCommand("models.Customer");
        userType.add(radioButton);

        userTypePanel.add(radioButton = new JRadioButton("Administrator"));
        radioButton.setActionCommand("Administrator");
        userType.add(radioButton);

        userTypePanel.add(radioButton = new JRadioButton("New models.Customer"));
        radioButton.setActionCommand("New models.Customer");
        userType.add(radioButton);

        JPanel continuePanel = new JPanel();
        JButton continueButton = new JButton("Continue");
        continuePanel.add(continueButton);

        Container content = f.getContentPane();
        content.setLayout(new GridLayout(2, 1));
        content.add(userTypePanel);
        content.add(continuePanel);


        continueButton.addActionListener(ae -> {
            String user = userType.getSelection().getActionCommand();

            //if user selects NEW CUSTOMER--------------------------------------------------------------------------------------
            if (user.equals("New models.Customer")) {
                f.dispose();
                f1 = new JFrame("Create New models.Customer");
                f1.setSize(400, 300);
                f1.setLocation(200, 200);
                f1.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent we) {
                        System.exit(0);
                    }
                });
                Container content1 = f1.getContentPane();
                content1.setLayout(new BorderLayout());

                firstNameLabel = new JLabel("First Name:", SwingConstants.RIGHT);
                surnameLabel = new JLabel("Surname:", SwingConstants.RIGHT);
                pPPSLabel = new JLabel("PPS Number:", SwingConstants.RIGHT);
                dOBLabel = new JLabel("Date of birth", SwingConstants.RIGHT);
                firstNameTextField = new JTextField(20);
                surnameTextField = new JTextField(20);
                pPSTextField = new JTextField(20);
                dOBTextField = new JTextField(20);
                JPanel panel = new JPanel(new GridLayout(6, 2));
                setDetails(panel);

                panel2 = new JPanel();
                addButton = new JButton("Add");

                addButton.addActionListener(e -> {


                    PPS = pPSTextField.getText();
                    firstName = firstNameTextField.getText();
                    surname = surnameTextField.getText();
                    DOB = dOBTextField.getText();
                    password = "";

                    CustomerID = "ID" + PPS;


                    addButton.addActionListener(e1 -> {
                        f1.dispose();

                        boolean loop = true;
                        while (loop) {
                            password = JOptionPane.showInputDialog(f, "Enter 7 character Password;");

                            if (password.length() != 7)//Making sure password is 7 characters
                            {
                                JOptionPane.showMessageDialog(null, null, "Password must be 7 charatcers long", JOptionPane.OK_OPTION);
                            } else {
                                loop = false;
                            }
                        }


                        ArrayList<CustomerAccount> accounts = new ArrayList<>();
                        Customer customer = new Customer(PPS, surname, firstName, DOB, CustomerID, password, accounts);

                        customerList.add(customer);

                        JOptionPane.showMessageDialog(f, "CustomerID = " + CustomerID + "\n Password = " + password, "models.Customer created.", JOptionPane.INFORMATION_MESSAGE);
                        menuStart();
                        f.dispose();
                    });
                });
                JButton cancel = new JButton("Cancel");
                cancel.addActionListener(e -> {
                    f1.dispose();
                    menuStart();
                });

                panel2.add(addButton);
                panel2.add(cancel);

                content1.add(panel, BorderLayout.CENTER);
                content1.add(panel2, BorderLayout.SOUTH);

                f1.setVisible(true);

            }


            //------------------------------------------------------------------------------------------------------------------

            //if user select ADMIN----------------------------------------------------------------------------------------------
            if (user.equals("Administrator")) {
              //  rename loop to 
                boolean b = true, b1 = true;
                boolean cont = false;
                while (b) {
                    Object adminUsername = JOptionPane.showInputDialog(f, "Enter Administrator Username:");

                    if (!adminUsername.equals("admin"))//search admin list for admin with matching admin username
                    {
                        int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Username. Try again?", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            b = true;
                        } else if (reply == JOptionPane.NO_OPTION) {
                            f1.dispose();
                            b = false;
                            b1 = false;
                            menuStart();
                        }
                    } else {
                        b = false;
                    }
                }

                while (b1) {
                    Object adminPassword = JOptionPane.showInputDialog(f, "Enter Administrator Password;");

                    if (!adminPassword.equals("admin11"))//search admin list for admin with matching admin password
                    {
                        int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Password. Try again?", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {

                        } else if (reply == JOptionPane.NO_OPTION) {
                            f1.dispose();
                            b1 = false;
                            menuStart();
                        }
                    } else {
                        b1 = false;
                        cont = true;
                    }
                }

                if (cont) {
                    f1.dispose();
                    admin();
                }
            }
            //----------------------------------------------------------------------------------------------------------------


            //if user selects CUSTOMER ----------------------------------------------------------------------------------------
            if (user.equals("models.Customer")) {
                boolean loop = true, loop2 = true;
                boolean cont = false;
                boolean found = false;
                Customer customer = null;
                while (loop) {
                    Object customerID = JOptionPane.showInputDialog(f, "Enter models.Customer ID:");

                    for (Customer aCustomer : customerList) {

                        if (aCustomer.getCustomerID().equals(customerID))//search customer list for matching customer ID
                        {
                            found = true;
                            customer = aCustomer;
                        }
                    }

                    if (found == false) {
                        int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            loop = true;
                        } else if (reply == JOptionPane.NO_OPTION) {
                            f.dispose();
                            loop = false;
                            loop2 = false;
                            menuStart();
                        }
                    } else {
                        loop = false;
                    }

                }

                while (loop2) {
                    Object customerPassword = JOptionPane.showInputDialog(f, "Enter models.Customer Password;");

                    if (!customer.getPassword().equals(customerPassword))//check if custoemr password is correct
                    {
                        int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect password. Try again?", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {

                        } else if (reply == JOptionPane.NO_OPTION) {
                            f.dispose();
                            loop2 = false;
                            menuStart();
                        }
                    } else {
                        loop2 = false;
                        cont = true;
                    }
                }

                if (cont) {
                    f.dispose();
                    customer();
                }
            }
            //-----------------------------------------------------------------------------------------------------------------------
        });
        f.setVisible(true);
    }

    private void setDetails(JPanel panel) {
        panel.add(firstNameLabel);
        panel.add(firstNameTextField);
        panel.add(surnameLabel);
        panel.add(surnameTextField);
        panel.add(pPPSLabel);
        panel.add(pPSTextField);
        panel.add(dOBLabel);
        panel.add(dOBTextField);
    }

    public void admin() {
        dispose();

        f = new JFrame("Administrator Menu");
        f.setSize(400, 400);
        f.setLocation(200, 200);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        f.setVisible(true);

        JPanel deleteCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton deleteCustomer = new JButton("Delete models.Customer");
        deleteCustomer.setPreferredSize(new Dimension(250, 20));
        deleteCustomerPanel.add(deleteCustomer);

        JPanel deleteAccountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton deleteAccount = new JButton("Delete Account");
        deleteAccount.setPreferredSize(new Dimension(250, 20));
        deleteAccountPanel.add(deleteAccount);

        JPanel bankChargesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton bankChargesButton = new JButton("Apply Bank Charges");
        bankChargesButton.setPreferredSize(new Dimension(250, 20));
        bankChargesPanel.add(bankChargesButton);

        JPanel interestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton interestButton = new JButton("Apply Interest");
        interestPanel.add(interestButton);
        interestButton.setPreferredSize(new Dimension(250, 20));

        JPanel editCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editCustomerButton = new JButton("Edit existing models.Customer");
        editCustomerPanel.add(editCustomerButton);
        editCustomerButton.setPreferredSize(new Dimension(250, 20));

        JPanel navigatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton navigateButton = new JButton("Navigate models.Customer Collection");
        navigatePanel.add(navigateButton);
        navigateButton.setPreferredSize(new Dimension(250, 20));

        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton summaryButton = new JButton("Display Summary Of All Accounts");
        summaryPanel.add(summaryButton);
        summaryButton.setPreferredSize(new Dimension(250, 20));

        JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton accountButton = new JButton("Add an Account to a models.Customer");
        accountPanel.add(accountButton);
        accountButton.setPreferredSize(new Dimension(250, 20));

        JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton returnButton = new JButton("Exit Admin Menu");
        returnPanel.add(returnButton);

        JLabel label1 = new JLabel("Please select an option");

        content = f.getContentPane();
        content.setLayout(new GridLayout(9, 1));
        setContent(deleteCustomerPanel, bankChargesPanel, interestPanel, editCustomerPanel, navigatePanel, summaryPanel, accountPanel, returnPanel, label1);


        bankChargesButton.addActionListener(ae -> {

            boolean b = true;

            boolean found = false;

            if (customerList.isEmpty()) {
                JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                f.dispose();
                admin();

            } else {
                while (b) {
                    Object customerID = JOptionPane.showInputDialog(f, "models.Customer ID of models.Customer You Wish to Apply Charges to:");

                    for (Customer aCustomer : customerList) {

                        if (aCustomer.getCustomerID().equals(customerID)) {
                            found = true;
                            customer = aCustomer;
                            b = false;
                        }
                    }

                    if (found == false) {
                        b = status(b);
                    } else {
                        //
                        configWindow();


                        JComboBox<String> box = new JComboBox<>();
                        for (int i = 0; i < customer.getAccounts().size(); i++) {


                            box.addItem(customer.getAccounts().get(i).getNumber());
                        }


                        box.getSelectedItem();

                        JPanel boxPanel = new JPanel();
                        boxPanel.add(box);

                        JPanel buttonPanel = new JPanel();
                        JButton continueButton = new JButton("Apply Charge");
                        setContent(boxPanel, buttonPanel, continueButton);
                        JButton returnButton1 = new JButton();


                        if (customer.getAccounts().isEmpty()) {
                            JOptionPane.showMessageDialog(f, "This customer has no accounts! \n The admin must add acounts to this customer.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                            f.dispose();
                            admin();
                        } else {

                            for (int i = 0; i < customer.getAccounts().size(); i++) {
                                if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
                                    customerAccount = customer.getAccounts().get(i);
                                }
                            }

                            continueButton.addActionListener(ae1 -> {
                                String euro = "\u20ac";


                                if (customerAccount instanceof CustomerDepositAccount) {


                                    JOptionPane.showMessageDialog(f, "25" + euro + " deposit account fee aplied.", "", JOptionPane.INFORMATION_MESSAGE);
                                    customerAccount.setBalance(customerAccount.getBalance() - 25);
                                    JOptionPane.showMessageDialog(f, "New balance = " + customerAccount.getBalance(), "Success!", JOptionPane.INFORMATION_MESSAGE);
                                }

                                if (customerAccount instanceof CustomerCurrentAccount) {


                                    JOptionPane.showMessageDialog(f, "15" + euro + " current account fee aplied.", "", JOptionPane.INFORMATION_MESSAGE);
                                    customerAccount.setBalance(customerAccount.getBalance() - 25);
                                    JOptionPane.showMessageDialog(f, "New balance = " + customerAccount.getBalance(), "Success!", JOptionPane.INFORMATION_MESSAGE);
                                }


                                f.dispose();
                                admin();
                            });

                            returnButton1.addActionListener(ae12 -> {
                                f.dispose();
                                menuStart();
                            });

                        }
                    }
                }
            }


        });

        interestButton.addActionListener(ae -> {

            boolean b = true;

            boolean found = false;

            if (customerList.isEmpty()) {
                JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                f.dispose();
                admin();

            } else {
                while (b) {
                    Object customerID = JOptionPane.showInputDialog(f, "models.Customer ID of models.Customer You Wish to Apply Interest to:");

                    for (Customer aCustomer : customerList) {

                        if (aCustomer.getCustomerID().equals(customerID)) {
                            found = true;
                            customer = aCustomer;
                            b = false;
                        }
                    }

                    if (found == false) {
                        b = status(b);
                    } else {
                        configWindow();


                        JComboBox<String> box = new JComboBox<>();
                        for (int i = 0; i < customer.getAccounts().size(); i++) {


                            box.addItem(customer.getAccounts().get(i).getNumber());
                        }


                        box.getSelectedItem();

                        JPanel boxPanel = new JPanel();

                        JLabel label = new JLabel("Select an account to apply interest to:");
                        boxPanel.add(label);
                        boxPanel.add(box);
                        JPanel buttonPanel = new JPanel();
                        JButton continueButton = new JButton("Apply Interest");
                        setContent(boxPanel, buttonPanel, continueButton);


                        if (customer.getAccounts().isEmpty()) {
                            JOptionPane.showMessageDialog(f, "This customer has no accounts! \n The admin must add acounts to this customer.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                            f.dispose();
                            admin();
                        } else {

                            for (int i = 0; i < customer.getAccounts().size(); i++) {
                                if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
                                    customerAccount = customer.getAccounts().get(i);
                                }
                            }

                            continueButton.addActionListener(ae13 -> {
                                String euro = "\u20ac";
                                double interest = 0;
                                boolean loop1 = true;

                                while (loop1) {
                                    String interestString = JOptionPane.showInputDialog(f, "Enter interest percentage you wish to apply: \n NOTE: Please enter a numerical value. (with no percentage sign) \n E.g: If you wish to apply 8% interest, enter '8'");//the isNumeric method tests to see if the string entered was numeric.
                                    if (isNumeric(interestString)) {

                                        interest = Double.parseDouble(interestString);
                                        loop1 = false;

                                        customerAccount.setBalance(customerAccount.getBalance() + (customerAccount.getBalance() * (interest / 100)));

                                        JOptionPane.showMessageDialog(f, interest + "% interest applied. \n new balance = " + customerAccount.getBalance() + euro, "Success!", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(f, "You must enter a numerical value!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                                    }


                                }

                                f.dispose();
                                admin();
                            });

                            returnButton.addActionListener(ae14 -> {
                                f.dispose();
                                menuStart();
                            });

                        }
                    }
                }
            }

        });

        editCustomerButton.addActionListener(ae -> {

            boolean b = true;

            boolean found = false;

            if (customerList.isEmpty()) {
                JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                f.dispose();
                admin();

            } else {

                while (b) {
                    Object customerID = JOptionPane.showInputDialog(f, "Enter models.Customer ID:");

                    for (Customer aCustomer : customerList) {

                        if (aCustomer.getCustomerID().equals(customerID)) {
                            found = true;
                            customer = aCustomer;
                        }
                    }

                    if (found == false) {
                        b = status(b);
                    } else {
                        b = false;
                    }

                }

                f.dispose();

                f.dispose();
                f = new JFrame("Administrator Menu");
                f.setSize(400, 300);
                f.setLocation(200, 200);
                f.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent we) {
                        System.exit(0);
                    }
                });

                setUserLabels();

                JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

                JPanel cancelPanel = new JPanel();

                setTextPanel(textPanel);

                firstNameTextField.setText(customer.getFirstName());
                surnameTextField.setText(customer.getSurname());
                pPSTextField.setText(customer.getPPS());
                dOBTextField.setText(customer.getDOB());
                customerIDTextField.setText(customer.getCustomerID());
                passwordTextField.setText(customer.getPassword());

                //JLabel label1 = new JLabel("Edit customer details below. The save");


                JButton saveButton = new JButton("Save");
                JButton cancelButton = new JButton("Exit");

                cancelPanel.add(cancelButton, BorderLayout.SOUTH);
                cancelPanel.add(saveButton, BorderLayout.SOUTH);
                //	content.removeAll();
                Container content = f.getContentPane();
                content.setLayout(new GridLayout(2, 1));
                content.add(textPanel, BorderLayout.NORTH);
                content.add(cancelPanel, BorderLayout.SOUTH);

                f.setContentPane(content);
                f.setSize(340, 350);
                f.setLocation(200, 200);
                f.setVisible(true);
                f.setResizable(false);

                saveButton.addActionListener(ae112 -> {

                    customer.setFirstName(firstNameTextField.getText());
                    customer.setSurname(surnameTextField.getText());
                    customer.setPPS(pPSTextField.getText());
                    customer.setDOB(dOBTextField.getText());
                    customer.setCustomerID(customerIDTextField.getText());
                    customer.setPassword(passwordTextField.getText());

                    JOptionPane.showMessageDialog(null, "Changes Saved.");
                });

                cancelButton.addActionListener(ae111 -> {
                    f.dispose();

                    admin();
                });
            }
        });

        summaryButton.addActionListener(ae -> {
            f.dispose();


            f = new JFrame("Summary of Transactions");
            f.setSize(400, 700);
            f.setLocation(200, 200);
            f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    System.exit(0);
                }
            });
            f.setVisible(true);

            JLabel label11 = new JLabel("Summary of all transactions: ");

            JPanel returnPanel1 = new JPanel();
            JButton returnButton12 = new JButton("Return");
            returnPanel1.add(returnButton12);

            JPanel textPanel = new JPanel();

            textPanel.setLayout(new BorderLayout());
            JTextArea textArea = new JTextArea(40, 20);
            textArea.setEditable(false);
            textPanel.add(label11, BorderLayout.NORTH);
            textPanel.add(textArea, BorderLayout.CENTER);
            textPanel.add(returnButton12, BorderLayout.SOUTH);

            JScrollPane scrollPane = new JScrollPane(textArea);
            textPanel.add(scrollPane);

            for (int a = 0; a < customerList.size(); a++)//For each customer, for each account, it displays each transaction.
            {
                for (int b = 0; b < customerList.get(a).getAccounts().size(); b++) {
                    customerAccount = customerList.get(a).getAccounts().get(b);
                    for (int c = 0; c < customerList.get(a).getAccounts().get(b).getTransactionList().size(); c++) {

                        textArea.append(customerAccount.getTransactionList().get(c).toString());
                        //Int total = acc.getTransactionList().get(c).getAmount(); //I was going to use this to keep a running total but I couldnt get it  working.

                    }
                }
            }


            textPanel.add(textArea);
            content.removeAll();


            Container content = f.getContentPane();
            content.setLayout(new GridLayout(1, 1));
            //	content.add(label1);
            content.add(textPanel);
            //content.add(returnPanel);

            returnButton12.addActionListener(ae110 -> {
                f.dispose();
                admin();
            });
        });

        navigateButton.addActionListener(ae -> {
            f.dispose();

            if (customerList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
                admin();
            } else {

                JButton first, previous, next, last, cancel;
                JPanel gridPanel, buttonPanel, cancelPanel;

                Container content = getContentPane();

                content.setLayout(new BorderLayout());

                buttonPanel = new JPanel();
                gridPanel = new JPanel(new GridLayout(8, 2));
                cancelPanel = new JPanel();

                setUserLabels();

                first = new JButton("First");
                previous = new JButton("Previous");
                next = new JButton("Next");
                last = new JButton("Last");
                cancel = new JButton("Cancel");

                setCustomer0Details();

                firstNameTextField.setEditable(false);
                surnameTextField.setEditable(false);
                pPSTextField.setEditable(false);
                dOBTextField.setEditable(false);
                customerIDTextField.setEditable(false);
                passwordTextField.setEditable(false);

                setTextPanel(gridPanel);

                buttonPanel.add(first);
                buttonPanel.add(previous);
                buttonPanel.add(next);
                buttonPanel.add(last);

                cancelPanel.add(cancel);

                content.add(gridPanel, BorderLayout.NORTH);
                content.add(buttonPanel, BorderLayout.CENTER);
                content.add(cancelPanel, BorderLayout.AFTER_LAST_LINE);
                first.addActionListener(ae19 -> {
                    position = 0;
                    setCustomer0Details();
                });

                previous.addActionListener(ae18 -> {

                    if (position < 1) {
                        //don't do anything
                    } else {
                        position = position - 1;

                        setCustomerDetails();
                    }
                });

                next.addActionListener(ae17 -> {

                    if (position == customerList.size() - 1) {
                        //don't do anything
                    } else {
                        position = position + 1;

                        setCustomerDetails();
                    }


                });

                last.addActionListener(ae16 -> {

                    position = customerList.size() - 1;

                    setCustomerDetails();
                });

                cancel.addActionListener(ae15 -> {
                    dispose();
                    admin();
                });
                setContentPane(content);
                setSize(400, 300);
                setVisible(true);
            }
        });

        accountButton.addActionListener(ae -> {
            f.dispose();

            if (customerList.isEmpty()) {
                JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                f.dispose();
                admin();
            } else {
                boolean b = true;

                boolean found = false;

                while (b) {
                    Object customerID = JOptionPane.showInputDialog(f, "models.Customer ID of models.Customer You Wish to Add an Account to:");

                    for (Customer aCustomer : customerList) {

                        if (aCustomer.getCustomerID().equals(customerID)) {
                            found = true;
                            customer = aCustomer;
                        }
                    }

                    if (found == false) {
                        b = status(b);
                    } else {
                        b = false;
                        //a combo box in an dialog box that asks the admin what type of account they wish to create (deposit/current)
                        String[] choices = {"Current Account", "Deposit Account"};
                        String account = (String) JOptionPane.showInputDialog(null, "Please choose account type",
                                "Account Type", JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);

                        if (account.equals("Current Account")) {
                            //create current account
                            boolean valid = true;
                            double balance = 0;
                            String number = "C" + (customerList.indexOf(customer) + 1) * 10 + (customer.getAccounts().size() + 1);//this simple algorithm generates the account number
                            ArrayList<AccountTransaction> transactionList = new ArrayList<>();
                            int randomPIN = (int) (Math.random() * 9000) + 1000;
                            String pin = String.valueOf(randomPIN);

                            ATMCard atm = new ATMCard(randomPIN, valid);

                            CustomerCurrentAccount current = new CustomerCurrentAccount(atm, number, balance, transactionList);

                            customer.getAccounts().add(current);
                            JOptionPane.showMessageDialog(f, "Account number = " + number + "\n PIN = " + pin, "Account created.", JOptionPane.INFORMATION_MESSAGE);

                            f.dispose();
                            admin();
                        }

                        if (account.equals("Deposit Account")) {
                            //create deposit account

                            double balance = 0, interest = 0;
                            String number = "D" + (customerList.indexOf(customer) + 1) * 10 + (customer.getAccounts().size() + 1);//this simple algorithm generates the account number
                            ArrayList<AccountTransaction> transactionList = new ArrayList<>();

                            CustomerDepositAccount deposit = new CustomerDepositAccount(interest, number, balance, transactionList);

                            customer.getAccounts().add(deposit);
                            JOptionPane.showMessageDialog(f, "Account number = " + number, "Account created.", JOptionPane.INFORMATION_MESSAGE);

                            f.dispose();
                            admin();
                        }

                    }
                }
            }
        });

        deleteCustomer.addActionListener(ae -> {
            boolean found = true;

            if (customerList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
                dispose();
                admin();
            } else {
                {
                    Object customerID = JOptionPane.showInputDialog(f, "models.Customer ID of models.Customer You Wish to Delete:");

                    for (Customer aCustomer : customerList) {

                        if (aCustomer.getCustomerID().equals(customerID)) {
                            found = true;
                            customer = aCustomer;
                        }
                    }

                    if (found == false) {
                        int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                        } else if (reply == JOptionPane.NO_OPTION) {
                            f.dispose();

                            admin();
                        }
                    } else {
                        if (customer.getAccounts().size() > 0) {
                            JOptionPane.showMessageDialog(f, "This customer has accounts. \n You must delete a customer's accounts before deleting a customer ", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            customerList.remove(customer);
                            JOptionPane.showMessageDialog(f, "models.Customer Deleted ", "Success.", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }


                }
            }
        });

        deleteAccount.addActionListener(ae -> {
            boolean found = true;


            {
                Object customerID = JOptionPane.showInputDialog(f, "models.Customer ID of models.Customer from which you wish to delete an account");

                for (Customer aCustomer : customerList) {

                    if (aCustomer.getCustomerID().equals(customerID)) {
                        found = true;
                        customer = aCustomer;
                    }
                }

                if (!found) {
                    int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                    } else if (reply == JOptionPane.NO_OPTION) {
                        f.dispose();

                        admin();
                    }
                } else {
                    //Here I would make the user select a an account to delete from a combo box. If the account had a balance of 0 then it would be deleted. (I do not have time to do this)
                }


            }
        });
        returnButton.addActionListener(ae -> {
            f.dispose();
            menuStart();
        });
    }

    private void setUserLabels() {
        firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
        surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
        pPPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
        dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
        customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
        passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
        firstNameTextField = new JTextField(20);
        surnameTextField = new JTextField(20);
        pPSTextField = new JTextField(20);
        dOBTextField = new JTextField(20);
        customerIDTextField = new JTextField(20);
        passwordTextField = new JTextField(20);
    }

    private void setCustomer0Details() {
        firstNameTextField.setText(customerList.get(0).getFirstName());
        surnameTextField.setText(customerList.get(0).getSurname());
        pPSTextField.setText(customerList.get(0).getPPS());
        dOBTextField.setText(customerList.get(0).getDOB());
        customerIDTextField.setText(customerList.get(0).getCustomerID());
        passwordTextField.setText(customerList.get(0).getPassword());
    }

    private void setCustomerDetails() {
        firstNameTextField.setText(customerList.get(position).getFirstName());
        surnameTextField.setText(customerList.get(position).getSurname());
        pPSTextField.setText(customerList.get(position).getPPS());
        dOBTextField.setText(customerList.get(position).getDOB());
        customerIDTextField.setText(customerList.get(position).getCustomerID());
        passwordTextField.setText(customerList.get(position).getPassword());
    }

    private void setContent(JPanel deleteCustomerPanel, JPanel bankChargesPanel, JPanel interestPanel, JPanel editCustomerPanel, JPanel navigatePanel, JPanel summaryPanel, JPanel accountPanel, JPanel returnPanel, JLabel label1) {
        content.add(label1);
        content.add(accountPanel);
        content.add(bankChargesPanel);
        content.add(interestPanel);
        content.add(editCustomerPanel);
        content.add(navigatePanel);
        content.add(summaryPanel);
        content.add(deleteCustomerPanel);
        //	content.add(deleteAccountPanel);
        content.add(returnPanel);
    }

    private void setTextPanel(JPanel textPanel) {
        setDetails(textPanel);
        textPanel.add(customerIDLabel);
        textPanel.add(customerIDTextField);
        textPanel.add(passwordLabel);
        textPanel.add(passwordTextField);
    }

    private void configWindow() {
        f.dispose();
        f = new JFrame("Administrator Menu");
        f.setSize(400, 300);
        f.setLocation(200, 200);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        f.setVisible(true);
    }

    private void setContent(JPanel boxPanel, JPanel buttonPanel, JButton continueButton) {
        JButton returnButton1 = new JButton("Return");
        buttonPanel.add(continueButton);
        buttonPanel.add(returnButton1);
        Container content = f.getContentPane();
        content.setLayout(new GridLayout(2, 1));

        content.add(boxPanel);
        content.add(buttonPanel);
    }

    private boolean status(boolean b) {
        int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            b = true;
        } else if (reply == JOptionPane.NO_OPTION) {
            f.dispose();
            b = false;

            admin();
        }
        return b;
    }

    public void customer() {
        windowSettings();
        if (customer1.getAccounts().size() == 0) {
            JOptionPane.showMessageDialog(f, "This customer does not have any accounts yet. \n An admin must create an account for this customer \n for them to be able to use customer functionality. ", "Oops!", JOptionPane.INFORMATION_MESSAGE);
            f.dispose();
            menuStart();
        } else {
            JPanel buttonPanel = new JPanel();
            JPanel boxPanel = new JPanel();
            JPanel labelPanel = new JPanel();

            JLabel label = new JLabel("Select Account:");
            labelPanel.add(label);

            JButton returnButton = new JButton("Return");
            buttonPanel.add(returnButton);
            JButton continueButton = new JButton("Continue");
            buttonPanel.add(continueButton);

            JComboBox<String> box = new JComboBox<String>();
            for (int i = 0; i < customer1.getAccounts().size(); i++) {
                box.addItem(customer1.getAccounts().get(i).getNumber());
            }


            for (int i = 0; i < customer1.getAccounts().size(); i++) {
                if (customer1.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
                    customerAccount = customer1.getAccounts().get(i);
                }
            }


            boxPanel.add(box);
            content = f.getContentPane();
            content.setLayout(new GridLayout(3, 1));
            content.add(labelPanel);
            content.add(boxPanel);
            content.add(buttonPanel);

            returnButton.addActionListener(ae -> {
                f.dispose();
                menuStart();
            });

            continueButton.addActionListener(ae -> {

                f.dispose();

                windowSettings();

                JPanel statementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JButton statementButton = new JButton("Display Bank Statement");
                statementButton.setPreferredSize(new Dimension(250, 20));

                statementPanel.add(statementButton);

                JPanel lodgementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JButton lodgementButton = new JButton("Lodge money into account");
                lodgementPanel.add(lodgementButton);
                lodgementButton.setPreferredSize(new Dimension(250, 20));

                JPanel withdrawalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JButton withdrawButton = new JButton("Withdraw money from account");
                withdrawalPanel.add(withdrawButton);
                withdrawButton.setPreferredSize(new Dimension(250, 20));

                JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                JButton returnButton1 = new JButton("Exit models.Customer Menu");
                returnPanel.add(returnButton1);

                JLabel label1 = new JLabel("Please select an option");

                content = f.getContentPane();
                content.setLayout(new GridLayout(5, 1));
                content.add(label1);
                content.add(statementPanel);
                content.add(lodgementPanel);
                content.add(withdrawalPanel);
                content.add(returnPanel);

                statementButton.addActionListener(ae12 -> {
                    f.dispose();
                    f = new JFrame("models.Customer Menu");
                    f.setSize(400, 600);
                    f.setLocation(200, 200);
                    f.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent we) {
                            System.exit(0);
                        }
                    });
                    f.setVisible(true);

                    JLabel label11 = new JLabel("Summary of account transactions: ");

                    JPanel returnPanel1 = new JPanel();
                    JButton returnButton11 = new JButton("Return");
                    returnPanel1.add(returnButton11);

                    JPanel textPanel = new JPanel();

                    textPanel.setLayout(new BorderLayout());
                    JTextArea textArea = new JTextArea(40, 20);
                    textArea.setEditable(false);
                    textPanel.add(label11, BorderLayout.NORTH);
                    textPanel.add(textArea, BorderLayout.CENTER);
                    textPanel.add(returnButton11, BorderLayout.SOUTH);

                    JScrollPane scrollPane = new JScrollPane(textArea);
                    textPanel.add(scrollPane);

                    for (int i = 0; i < customerAccount.getTransactionList().size(); i++) {
                        textArea.append(customerAccount.getTransactionList().get(i).toString());

                    }

                    textPanel.add(textArea);
                    content.removeAll();


                    Container content = f.getContentPane();
                    content.setLayout(new GridLayout(1, 1));
                    //	content.add(label1);
                    content.add(textPanel);
                    //content.add(returnPanel);

                    returnButton11.addActionListener(ae121 -> {
                        f.dispose();
                        customer();
                    });
                });

                lodgementButton.addActionListener(ae13 -> {
                    boolean b;
                    boolean on = true;
                    double balance = 0;

                    if (customerAccount instanceof CustomerCurrentAccount) {
                        int count = 3;
                        int checkPin = ((CustomerCurrentAccount) customerAccount).getAtm().getPin();
                        b = true;

                        while (b) {
                            if (count == 0) {
                                JOptionPane.showMessageDialog(f, "Pin entered incorrectly 3 times. ATM card locked.", "Pin", JOptionPane.INFORMATION_MESSAGE);
                                ((CustomerCurrentAccount) customerAccount).getAtm().setValid(false);
                                customer();
                                b = false;
                                on = false;
                            }

                            String Pin = JOptionPane.showInputDialog(f, "Enter 4 digit PIN;");
                            int i = Integer.parseInt(Pin);

                            if (on) {
                                if (checkPin == i) {
                                    b = false;
                                    JOptionPane.showMessageDialog(f, "Pin entry successful", "Pin", JOptionPane.INFORMATION_MESSAGE);

                                } else {
                                    count--;
                                    JOptionPane.showMessageDialog(f, "Incorrect pin. " + count + " attempts remaining.", "Pin", JOptionPane.INFORMATION_MESSAGE);
                                }

                            }
                        }


                    }
                    if (on == true) {
                        String balanceTest = JOptionPane.showInputDialog(f, "Enter amount you wish to lodge:");//the isNumeric method tests to see if the string entered was numeric.
                        if (isNumeric(balanceTest)) {

                            balance = Double.parseDouble(balanceTest);


                        } else {
                            JOptionPane.showMessageDialog(f, "You must enter a numerical value!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                        }


                        String euro = "\u20ac";
                        customerAccount.setBalance(customerAccount.getBalance() + balance);
                        // String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                        Date date = new Date();
                        String date2 = date.toString();
                        String type = "Lodgement";
                        double amount = balance;


                        AccountTransaction transaction = new AccountTransaction(date2, type, amount);
                        customerAccount.getTransactionList().add(transaction);

                        JOptionPane.showMessageDialog(f, balance + euro + " added do you account!", "Lodgement", JOptionPane.INFORMATION_MESSAGE);
                        JOptionPane.showMessageDialog(f, "New balance = " + customerAccount.getBalance() + euro, "Lodgement", JOptionPane.INFORMATION_MESSAGE);
                    }

                });

                withdrawButton.addActionListener(ae14 -> {
                    boolean b;
                    boolean on = true;
                    double withdraw = 0;

                    if (customerAccount instanceof CustomerCurrentAccount) {
                        int count = 3;
                        int checkPin = ((CustomerCurrentAccount) customerAccount).getAtm().getPin();
                        b = true;

                        while (b) {
                            if (count == 0) {
                                JOptionPane.showMessageDialog(f, "Pin entered incorrectly 3 times. ATM card locked.", "Pin", JOptionPane.INFORMATION_MESSAGE);
                                ((CustomerCurrentAccount) customerAccount).getAtm().setValid(false);
                                customer();
                                b = false;
                                on = false;
                            }

                            String Pin = JOptionPane.showInputDialog(f, "Enter 4 digit PIN;");
                            int i = Integer.parseInt(Pin);

                            if (on) {
                                if (checkPin == i) {
                                    b = false;
                                    JOptionPane.showMessageDialog(f, "Pin entry successful", "Pin", JOptionPane.INFORMATION_MESSAGE);

                                } else {
                                    count--;
                                    JOptionPane.showMessageDialog(f, "Incorrect pin. " + count + " attempts remaining.", "Pin", JOptionPane.INFORMATION_MESSAGE);

                                }

                            }
                        }


                    }
                    if (on == true) {
                        String balanceTest = JOptionPane.showInputDialog(f, "Enter amount you wish to withdraw (max 500):");//the isNumeric method tests to see if the string entered was numeric.
                        if (isNumeric(balanceTest)) {

                            withdraw = Double.parseDouble(balanceTest);


                        } else {
                            JOptionPane.showMessageDialog(f, "You must enter a numerical value!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                        }
                        if (withdraw > 500) {
                            JOptionPane.showMessageDialog(f, "500 is the maximum you can withdraw at a time.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                            withdraw = 0;
                        }
                        if (withdraw > customerAccount.getBalance()) {
                            JOptionPane.showMessageDialog(f, "Insufficient funds.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                            withdraw = 0;
                        }

                        String euro = "\u20ac";
                        customerAccount.setBalance(customerAccount.getBalance() - withdraw);
                        //recording transaction:
                        //		String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                        Date date = new Date();
                        String date2 = date.toString();

                        String type = "Withdraw";
                        double amount = withdraw;


                        AccountTransaction transaction = new AccountTransaction(date2, type, amount);
                        customerAccount.getTransactionList().add(transaction);


                        JOptionPane.showMessageDialog(f, withdraw + euro + " withdrawn.", "Withdraw", JOptionPane.INFORMATION_MESSAGE);
                        JOptionPane.showMessageDialog(f, "New balance = " + customerAccount.getBalance() + euro, "Withdraw", JOptionPane.INFORMATION_MESSAGE);
                    }


                });

                returnButton1.addActionListener(ae1 -> {
                    f.dispose();
                    menuStart();
                });
            });
        }
    }

    private void windowSettings() {
        f = new JFrame("models.Customer Menu");
        f.setSize(400, 300);
        f.setLocation(200, 200);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        f.setVisible(true);
    }
}

