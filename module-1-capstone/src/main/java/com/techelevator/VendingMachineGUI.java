package com.techelevator;

import com.techelevator.view.*;
import com.techelevator.view.Menu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


public class VendingMachineGUI implements ActionListener {

    private JFrame frame;

    /***** BUTTON TEXT STRING ARRAYS *****/
    private final String[] USER_INPUT_BUTTONS = {"A", "1", "2", "B", "3", "4", "C", "5", "6", "D", "7", "8", "E", "9", "0"};

    private final String[] FEED_MONEY_BUTTONS = {"Insert $1.00", "Insert $5.00", "Insert $10.00"};

    /***** BUTTON LISTS *****/
    private List<JButton> numberButtons = new ArrayList<>();
    private List<JButton> feedMoneyButtonsList = new ArrayList<>();
    private List<JButton> productDisplayButtonsList = new ArrayList<>();

    private JTextField inputDisplay = new JTextField();

    private JButton clearInput = new JButton();


    /***** SUBMIT BUTTONS *****/

    private JButton mainMenuSubmit = new JButton();
    private JButton purchaseMenuSubmit = new JButton();

    // private JButton feedMoneySubmit = new JButton();
    private JButton selectProductSubmit = new JButton();

    /***** GO BACK BUTTONS *****/

    private JButton goBackFromProductDisplayButton = new JButton();
    private JButton goBackFromPurchaseScreenButton = new JButton();

    private JButton goBackFromFeedMoneyScreenButton = new JButton();


    private Map<String, Product> productMap;

    /***** JPANELS *****/

    JPanel feedMoneyButtons = new JPanel();
    JPanel mainMenuQuestions = new JPanel();
    JPanel purchaseMenuQuestions = new JPanel();
    JPanel userInputButtons = new JPanel();

    JPanel feedMoneyPanel = new JPanel();

    JPanel productDisplayPanel = new JPanel();

    JPanel balanceDisplayPanel = new JPanel();

    /***** JLABELS *****/

    JLabel currentBalanceDisplay = new JLabel();

    /***** Creates a menu and vending machine *****/

    Menu menu = new Menu(System.in, System.out);
    VendingMachineCLI vendingMachine = new VendingMachineCLI(menu);

    /***** GUI CONSTRUCTOR *****/

    public VendingMachineGUI(Map<String, Product> productMap) {
        this.productMap = productMap;
        initialize();
    }


    /***** TEXT FIELD FOR USER INPUT *****/
    JTextField userInputTextField;


    /***** GUI BUILDER *****/

    private void initialize() {
        VendingMachineLog.logStart();
        vendingMachine.usingGUI=true;

        frame = new JFrame();
        frame.setLayout(null);
        frame.setTitle("VendoMatic 9001");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 575);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);


        userInputTextField = new JTextField(10);
        userInputTextField.setBounds(270, 15, 100, 60);
        userInputTextField.setBackground(Color.GRAY);
        userInputTextField.setEditable(false);
        userInputTextField.setFont(new Font("Arial", Font.PLAIN, 30));

        updateBalanceDisplay();
        currentBalanceDisplay.setFont(new Font("Arial", Font.BOLD, 18));
        currentBalanceDisplay.setHorizontalAlignment(JLabel.CENTER);
        balanceDisplayPanel.add(currentBalanceDisplay);


        mainMenuSubmit.setText("Enter");
        mainMenuSubmit.setBounds(270, 90, 100, 135);
        mainMenuSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == mainMenuSubmit) {
                    String userInput = userInputTextField.getText();
                    userInputTextField.setText("");
                    runMainMenuChoice(userInput);


                }
            }
        });

        purchaseMenuSubmit.setText("Enter");
        purchaseMenuSubmit.setVisible(false);
        purchaseMenuSubmit.setBounds(270, 90, 100, 135);
        purchaseMenuSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == purchaseMenuSubmit) {
                    String userInput = userInputTextField.getText();
                    userInputTextField.setText("");
                    runPurchaseMenuChoice(userInput);

                }
            }
        });
        selectProductSubmit.setText("Enter");
        selectProductSubmit.setVisible(false);
        selectProductSubmit.setBounds(270, 90, 100, 135);
        selectProductSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = userInputTextField.getText();
                userInputTextField.setText("");
                makePurchase(userInput);
                productDisplayPanel.setVisible(false);
                generateProductDisplay(productMap,true);
                productDisplayPanel.setVisible(true);

            }
        });

        goBackFromProductDisplayButton.setText("Back");
        goBackFromProductDisplayButton.setVisible(false);
        goBackFromProductDisplayButton.setBounds(270, 90, 100, 135);
        goBackFromProductDisplayButton.setVisible(false);
        goBackFromProductDisplayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchGuiMenu(productDisplayPanel, mainMenuQuestions);
                switchGUISubmitButton(goBackFromProductDisplayButton, mainMenuSubmit);
                userInputTextField.setText("");
            }
        });

        goBackFromPurchaseScreenButton.setText("Back");
        goBackFromPurchaseScreenButton.setVisible(false);
        goBackFromPurchaseScreenButton.setBounds(270, 240, 100, 135);
        goBackFromPurchaseScreenButton.setVisible(false);
        goBackFromPurchaseScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchGuiMenu(productDisplayPanel, purchaseMenuQuestions);
                switchGUISubmitButton(selectProductSubmit, purchaseMenuSubmit);
                goBackFromPurchaseScreenButton.setVisible(false);
                userInputTextField.setText("");
            }
        });

        goBackFromFeedMoneyScreenButton.setText("Back");
        goBackFromFeedMoneyScreenButton.setVisible(false);
        goBackFromFeedMoneyScreenButton.setBounds(270, 240, 100, 135);
        goBackFromFeedMoneyScreenButton.setVisible(false);
        goBackFromFeedMoneyScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchGuiMenu(feedMoneyButtons, userInputButtons);
                switchGuiMenu(feedMoneyPanel, purchaseMenuQuestions);
                purchaseMenuSubmit.setVisible(true);
                userInputTextField.setText("");
                goBackFromFeedMoneyScreenButton.setVisible(false);
            }
        });


        frame.add(mainMenuSubmit);
        frame.add(purchaseMenuSubmit);
       // frame.add(feedMoneySubmit);
        frame.add(selectProductSubmit);
        frame.add(goBackFromProductDisplayButton);
        frame.add(goBackFromPurchaseScreenButton);
        frame.add(goBackFromFeedMoneyScreenButton);


        mainMenuQuestions.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 30));
        mainMenuQuestions.setBackground(Color.lightGray);
        mainMenuQuestions.setBounds(380, 0, 610, 575);

        JLabel mainMenuTitleQuestion = new JLabel("What would you like to do?");
        mainMenuTitleQuestion.setFont(new

                Font("Arial", Font.BOLD, 44));
        mainMenuQuestions.add(mainMenuTitleQuestion);

        JLabel mainMenuQuestion1 = new JLabel(
                "(1) Display Items     ");
        mainMenuQuestion1.setFont(new Font("Arial", Font.PLAIN, 44));
        mainMenuQuestions.add(mainMenuQuestion1);
        JLabel mainMenuQuestion2 = new JLabel(
                "(2) Make a Purchase      ");
        mainMenuQuestion2.setFont(new Font("Arial", Font.PLAIN, 44));
        mainMenuQuestions.add(mainMenuQuestion2);
        JLabel mainMenuQuestion3 = new JLabel(
                "(3) Exit     ");
        mainMenuQuestion3.setFont(new Font("Arial", Font.PLAIN, 44));
        mainMenuQuestions.add(mainMenuQuestion3);

        frame.add(mainMenuQuestions);

        purchaseMenuQuestions.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 30));
        purchaseMenuQuestions.setBackground(Color.lightGray);
        purchaseMenuQuestions.setBounds(380, 0, 610, 575);
        purchaseMenuQuestions.setVisible(false);

        JLabel purchaseMenuTitleQuestion = new JLabel("What would you like to do?                ");
        purchaseMenuTitleQuestion.setFont(new Font("Arial", Font.BOLD, 36));
        purchaseMenuQuestions.add(purchaseMenuTitleQuestion);

        JLabel purchaseMenuQuestion1 = new JLabel("(1) Feed Money          ");
        purchaseMenuQuestion1.setFont(new

                Font("Arial", Font.PLAIN, 30));
        purchaseMenuQuestions.add(purchaseMenuQuestion1);
        JLabel purchaseMenuQuestion2 = new JLabel(
                "(2) Select Product            ");
        purchaseMenuQuestion2.setFont(new

                Font("Arial", Font.PLAIN, 30));
        purchaseMenuQuestions.add(purchaseMenuQuestion2);
        JLabel productMenuQuestion3 = new JLabel(
                "(3) Dispense Change            ");
        productMenuQuestion3.setFont(new

                Font("Arial", Font.PLAIN, 30));
        purchaseMenuQuestions.add(productMenuQuestion3);

        frame.add(purchaseMenuQuestions);


        feedMoneyPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 50));
        feedMoneyPanel.setBackground(Color.lightGray);
        feedMoneyPanel.setBounds(380, 0, 610, 575);

        JLabel feedMoneyTitleQuestion = new JLabel("Click the Money to add it to the vending machine!");
        feedMoneyTitleQuestion.setFont(new

                Font("Arial", Font.BOLD, 22));
        feedMoneyPanel.add(feedMoneyTitleQuestion);
        feedMoneyPanel.setVisible(false);
        frame.add(feedMoneyPanel);


        userInputButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        userInputButtons.setBounds(0, 0, 260, 475);

        userInputButtons.setBackground(Color.DARK_GRAY);


        /***** Creates Feed Money buttons and adds them to panel and list *****/

        for (String feed_Button : FEED_MONEY_BUTTONS) {
            JButton button = new JButton(feed_Button);
            button.setPreferredSize(new Dimension(156, 66));
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.addActionListener(feedMoneyButtonClick);
            button.setFocusable(false);
            button.setBackground(new Color(0, 102, 0));
            button.setForeground(Color.lightGray);
            feedMoneyButtons.add(button);
            feedMoneyButtonsList.add(button);

        }

        feedMoneyButtons.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 70));
        feedMoneyButtons.setVisible(false);
        feedMoneyButtons.setBackground(Color.DARK_GRAY);
        feedMoneyButtons.setBounds(0, 0, 260, 475);
        frame.add(feedMoneyButtons);


        /***** Creates User Input buttons and adds them to panel and list *****/


        for (
                String user_input_button : USER_INPUT_BUTTONS) {
            JButton button = new JButton(user_input_button);
            button.setPreferredSize(new Dimension(60, 60));
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.addActionListener(userInputButtonClick);
            button.setFocusable(false);
            userInputButtons.add(button);
            numberButtons.add(button);

        }
        clearInput.setText("Clear");
        clearInput.setPreferredSize(new Dimension(210, 60));
        userInputButtons.add(clearInput);
        clearInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInputTextField.setText("");
            }
        });

        frame.add(userInputButtons, BorderLayout.WEST);
        frame.add(userInputTextField);

        productDisplayPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        productDisplayPanel.setBackground(Color.lightGray);
        productDisplayPanel.setVisible(false);
        productDisplayPanel.setBounds(380, 0, 610, 575);
        frame.add(productDisplayPanel);

        balanceDisplayPanel.setBackground(Color.gray);
        balanceDisplayPanel.setBounds(0, 475, 380, 100);
        balanceDisplayPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        frame.add(balanceDisplayPanel);


    }

    /**
     * END OF INITIALIZE
     */


    public void show() {
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

    }

    /***** GUI SCREEN SWAPS *****/

    private void switchGuiMenu(JPanel oldPanel, JPanel newPanel) {

        oldPanel.setVisible(false);
        newPanel.setVisible(true);

    }

    private void switchGUISubmitButton(JButton oldButton, JButton newButton) {
        oldButton.setVisible(false);
        newButton.setVisible(true);
    }

    /***** RUN USER INPUT METHODS *****/
    public void runMainMenuChoice(String input) {
        if (Objects.equals(input, "1")) {
            generateProductDisplay(productMap, false);
            switchGuiMenu(mainMenuQuestions, productDisplayPanel);
            switchGUISubmitButton(mainMenuSubmit, goBackFromProductDisplayButton);
            //DISPLAY ITEMS

        } else if (Objects.equals(input, "2")) {
            switchGuiMenu(mainMenuQuestions, purchaseMenuQuestions);
            switchGUISubmitButton(mainMenuSubmit, purchaseMenuSubmit);           //Change GUI to Purchase Menu (DONE)
        } else if (Objects.equals(input, "3")) {
            frame.dispose();
        } else if (Objects.equals(input, "4")) {
            //run the sales report
            SalesReport.generateSalesReport(productMap);
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid input, please try again", null, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void runPurchaseMenuChoice(String input) {
        if (Objects.equals(input, "1")) {
            switchGuiMenu(purchaseMenuQuestions, feedMoneyPanel);
            purchaseMenuSubmit.setVisible(false);
            switchGuiMenu(userInputButtons, feedMoneyButtons);
            goBackFromFeedMoneyScreenButton.setVisible(true);

        } else if (Objects.equals(input, "2")) {
            generateProductDisplay(productMap, true);
            switchGuiMenu(purchaseMenuQuestions, productDisplayPanel);
            switchGUISubmitButton(purchaseMenuSubmit, selectProductSubmit);
            goBackFromPurchaseScreenButton.setVisible(true);

        } else if (Objects.equals(input, "3")) {
            //give change

            int[] values = vendingMachine.makeChange(vendingMachine.getBalance());
            JOptionPane.showMessageDialog(frame, "Your change is: " +
                    vendingMachine.dollarFormat.format(vendingMachine.getBalance()) +
                    " and will be dispensed as " + values[0] + " Quarters, " + values[1] + " Dimes, and " + values[2] + " Nickels");
            VendingMachineLog.logExit(String.valueOf(vendingMachine.dollarFormat.format(vendingMachine.getBalance())), "$0.00");
            vendingMachine.setBalance(0);
            updateBalanceDisplay();

            switchGuiMenu(purchaseMenuQuestions, mainMenuQuestions);
            switchGUISubmitButton(purchaseMenuSubmit, mainMenuSubmit);
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid input, please try again", null, JOptionPane.INFORMATION_MESSAGE);

        }
    }


    /***** GENERATES PRODUCT DISPLAY *****/
    private List<JButton> generateProductDisplay(Map<String, Product> map, Boolean isPurchaseMenu) {
        productDisplayPanel.removeAll();
        if(isPurchaseMenu) {
            JButton productDisplayInfo = new JButton("To purchase a product, just enter its code!");
            productDisplayInfo.setPreferredSize(new Dimension(600, 80));
            //productDisplayInfo.setEnabled(false);
            productDisplayInfo.setFont(new Font("Arial", Font.BOLD, 16));


            productDisplayPanel.add(productDisplayInfo);
        }

        for (Map.Entry<String, Product> product : map.entrySet()) {
            Product currentProduct = product.getValue();
            JButton button = new JButton(product.getKey() + ": " + currentProduct.getProductName());
            button.setPreferredSize(new Dimension(150, 80));
            button.setFont(new Font("Arial", Font.BOLD, 10));
            button.setFocusable(false);
            button.setToolTipText(vendingMachine.dollarFormat.format(currentProduct.getPrice()) + "     Quantity in Stock: " + currentProduct.getQuantity());
            productDisplayPanel.add(button);

            //button.setEnabled(false);
            productDisplayButtonsList.add(button);

        }
        JButton productDisplayHint = new JButton("Hint: Hover over items to see their price and quantity in stock");
        productDisplayHint.setPreferredSize(new Dimension(600, 80));
        productDisplayHint.setEnabled(false);
        productDisplayHint.setFont(new Font("Arial", Font.BOLD, 14));
        productDisplayPanel.add(productDisplayHint);
        return productDisplayButtonsList;
    }

    /***** Transactions *****/

    private void deposit(int userInput) {
        vendingMachine.setBalance(vendingMachine.getBalance() + userInput);
        updateBalanceDisplay();

    }

    private void makePurchase(String userInput) {
        if (productMap.containsKey(userInput)) {
            Product selectedProduct = productMap.get(userInput);
            String currentType = selectedProduct.getType();

            if (vendingMachine.getBalance() >= selectedProduct.getPrice()) {
                if (selectedProduct.getQuantity() > 0) {
                    selectedProduct.setQuantity(selectedProduct.getQuantity() - 1);
                    vendingMachine.setBalance((vendingMachine.getBalance()) - (selectedProduct.getPrice()));
                    updateBalanceDisplay();
                    VendingMachineLog.logPurchase(selectedProduct, vendingMachine.getBalance());
                    if (currentType.equals("Candy")) {
                        JOptionPane.showMessageDialog(frame, "You are so SWEET!\n" + "Your balance is: " + vendingMachine.dollarFormat.format(vendingMachine.getBalance()));
                    } else if (currentType.equals("Chip")) {
                        JOptionPane.showMessageDialog(frame, "Chip Chip, HOORAY!\n" + "Your balance is: " + vendingMachine.dollarFormat.format(vendingMachine.getBalance()));
                    } else if (currentType.equals("Drink")) {
                        JOptionPane.showMessageDialog(frame, "You are Soda-lightful!\n" + "Your balance is: " + vendingMachine.dollarFormat.format(vendingMachine.getBalance()));
                    } else if (currentType.equals("Gum")) {
                        JOptionPane.showMessageDialog(frame, "Thanks for chew-sing this vending machine!\n" + "Your balance is: " + vendingMachine.dollarFormat.format(vendingMachine.getBalance()));
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Sorry, this item is SOLD OUT");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Insufficient Balance, please insert more funds");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid Input please try again");
        }
    }


    private void updateBalanceDisplay() {
        currentBalanceDisplay.setText("Vending Machine Balance: " + vendingMachine.dollarFormat.format(vendingMachine.getBalance()));
    }


    /***** ACTION LISTENERS *****/
    ActionListener feedMoneyButtonClick = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == feedMoneyButtonsList.get(0)) {
                deposit(1);
                VendingMachineLog.logDeposit("$1.00", String.valueOf(vendingMachine.dollarFormat.format(vendingMachine.getBalance())));
                int result = JOptionPane.showConfirmDialog(frame, "You Just deposited $1.00.  Your balance is " + vendingMachine.dollarFormat.format(vendingMachine.getBalance()) + ". Would you like to deposit more?", null,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {

                } else if (result == JOptionPane.NO_OPTION) {
                    switchGuiMenu(feedMoneyPanel, purchaseMenuQuestions);
                    purchaseMenuSubmit.setVisible(true);
                    switchGuiMenu(feedMoneyButtons, userInputButtons);
                    goBackFromFeedMoneyScreenButton.setVisible(false);
                } else {
                    userInputTextField.setText("None");
                }

            } else if (e.getSource() == feedMoneyButtonsList.get(1)) {
                deposit(5);
                VendingMachineLog.logDeposit("$5.00", String.valueOf(vendingMachine.dollarFormat.format(vendingMachine.getBalance())));
                int result = JOptionPane.showConfirmDialog(frame, "You Just deposited $5.00.  Your balance is " + vendingMachine.dollarFormat.format(vendingMachine.getBalance()) + ". Would you like to deposit more?", null,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {

                } else if (result == JOptionPane.NO_OPTION) {
                    switchGuiMenu(feedMoneyPanel, purchaseMenuQuestions);
                    purchaseMenuSubmit.setVisible(true);
                    switchGuiMenu(feedMoneyButtons, userInputButtons);
                    goBackFromFeedMoneyScreenButton.setVisible(false);
                } else {
                    userInputTextField.setText("None");

                }
            } else if (e.getSource() == feedMoneyButtonsList.get(2)) {
                deposit(10);
                VendingMachineLog.logDeposit("$10.00", String.valueOf(vendingMachine.dollarFormat.format(vendingMachine.getBalance())));
                int result = JOptionPane.showConfirmDialog(frame, "You Just deposited $10.00.  Your balance is " + vendingMachine.dollarFormat.format(vendingMachine.getBalance()) + ". Would you like to deposit more?", null,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {

                } else if (result == JOptionPane.NO_OPTION) {
                    switchGuiMenu(feedMoneyPanel, purchaseMenuQuestions);
                    purchaseMenuSubmit.setVisible(true);
                    switchGuiMenu(feedMoneyButtons, userInputButtons);
                    goBackFromFeedMoneyScreenButton.setVisible(false);
                } else {
                    userInputTextField.setText("None");

                }
            }
        }
    };

    ActionListener userInputButtonClick = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < numberButtons.size(); i++) {

                if (e.getSource() == numberButtons.get(i)) {
                    userInputTextField.setText(userInputTextField.getText().concat(USER_INPUT_BUTTONS[i]));
                }
            }
        }
    };
}








