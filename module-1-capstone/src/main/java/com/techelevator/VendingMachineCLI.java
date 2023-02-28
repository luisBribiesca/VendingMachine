package com.techelevator;
import com.techelevator.view.*;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachineCLI {


    /*****   START SCREEN OPTIONS  *****/
    private static final String START_GUI = "Start GUI";
    private static final String START_COMMAND_LINE = "Start Command Line";

    private static final String[] START_MENU_OPTIONS = {START_GUI, START_COMMAND_LINE};

    /*****   MAIN MENU OPTIONS  *****/

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";

    private static final String EXIT = "Exit";

    private static final String GENERATE_SALES_REPORT = "";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, EXIT, GENERATE_SALES_REPORT};

    /*****   PURCHASE MENU OPTIONS  *****/

    private static final String CUSTOMER_OPTION_FEED_MONEY = "Feed Money";
    private static final String CUSTOMER_OPTION_SELECT_PRODUCT = "Select Product";
    private static final String CUSTOMER_OPTION_END_TRANSACTION = "Finish Transaction";
    private static final String[] CUSTOMER_OPTIONS = new String[]{CUSTOMER_OPTION_FEED_MONEY, CUSTOMER_OPTION_SELECT_PRODUCT, CUSTOMER_OPTION_END_TRANSACTION};

    /****************************/
    NumberFormat dollarFormat = NumberFormat.getCurrencyInstance();

    public boolean usingGUI = false;

    private double balance = 0.0;

    private Menu menu;

    public Map<String, Product> productMap = new TreeMap<>();

    /*********************   GETTERS AND SETTERS  ****************************/

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Menu getMenu() {
        return menu;
    }

    /*********************   CONSTRUCTORS  ****************************/
    public VendingMachineCLI(Menu menu) {
        this.menu = menu;
    }


    /*********************   RUN  ****************************/


    public void run() {

        createProductMap();


        while (true) {
            String startChoice = (String) menu.getChoiceFromOptions(START_MENU_OPTIONS);
            if (startChoice.equals(START_GUI)) {
                usingGUI = true;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        VendingMachineGUI vendMachineGUI = new VendingMachineGUI(productMap);
                        vendMachineGUI.show();
                    }

                });
            } else if (startChoice.equals(START_COMMAND_LINE)) {
                VendingMachineLog.logStart();
                while (true) {
                    String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

                    if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {

                        displayInventory();

                    } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {

                        runCustomerOptions();

                    } else if (choice.equals(EXIT)) {

                        return;

                    } else if (choice.equals(GENERATE_SALES_REPORT)) {

                        SalesReport.generateSalesReport(productMap);
                    }
                }
            }
        }
    }

    /*************** MAIN ***************/

    public static void main(String[] args) {

        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();

    }


    /*************** METHODS ***************/



    /**   createProductMap creates a map of products from the vendingmachinecsv file **/
    public Map<String, Product> createProductMap() {
        try (Scanner inventoryScanner = new Scanner(new File("vendingmachine.csv"))) {
            while (inventoryScanner.hasNextLine()) {
                String currentLine = inventoryScanner.nextLine();
                String[] splitString = currentLine.split("\\|");
                productMap.put(splitString[0], new Product(splitString[0], splitString[1], Double.parseDouble(splitString[2]), splitString[3]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return productMap;
    }


    /**   displayInventory prints a formatted list of all products with their slotLocation, name, price, and amount in stock to the console log **/
    private void displayInventory() {

        for (Map.Entry<String, Product> product : productMap.entrySet()) {
            if (product.getValue().getQuantity() > 0) {
                System.out.printf("%s %s %-18s %s %d %s",product.getKey(),":",product.getValue().getProductName(),dollarFormat.format(product.getValue().getPrice()),product.getValue().getQuantity(),"in stock");
            } else {
                System.out.printf("%s %s %-18s %s %s",product.getKey(),":",product.getValue().getProductName(),dollarFormat.format(product.getValue().getPrice()),"- SOLD OUT");


            }
            if (product.getKey().charAt(1) == '4') {
                System.out.println();
            } else {
                System.out.print(" |   ");
            }
        }

    }

    /**  runCustomerOptions takes customer input from console log and runs it through CUSTOMER_OPTIONS then runs chosen option **/
    private void runCustomerOptions() {
        while (true) {

            System.out.println("\nCurrent Money Provided: " + dollarFormat.format(balance));
            String customerChoice = (String) menu.getChoiceFromOptions(CUSTOMER_OPTIONS);
            if (customerChoice.equals(CUSTOMER_OPTION_FEED_MONEY)) {
                doTheDepositAndLog();

            } else if (customerChoice.equals(CUSTOMER_OPTION_SELECT_PRODUCT)) {

                displayInventory();

                System.out.println("Type the code for your item to select it for purchase:");

                String purchaseLocation = menu.getLocation(productMap);

                doPurchaseAndLog(purchaseLocation);

            } else if (customerChoice.equals(CUSTOMER_OPTION_END_TRANSACTION)) {
                giveChangeAndLog();

                break;
            }

        }
    }

    /**   doTheDepositAndLog asks user for deposit amount, deposits that amount into vending machine balance then logs deposit  **/

    private void doTheDepositAndLog() {
        System.out.println("Deposit money. Dollar bills only.");
        double depositAmount = menu.getDepositAmount();
        balance += depositAmount;

        VendingMachineLog.logDeposit(dollarFormat.format(depositAmount), dollarFormat.format(balance));
    }


    /**   doPurchaseAndLog runs the purchase if in stock and balance is sufficient, then decreases vending machine balance by price of product then logs purchase  **/
    private void doPurchaseAndLog(String purchaseLocation) {
        if (productMap.get(purchaseLocation).getQuantity() > 0) {

            if (balance >= productMap.get(purchaseLocation).getPrice()) {
                if (productMap.containsKey(purchaseLocation)) {
                    balance -= productMap.get(purchaseLocation).getPrice();
                    System.out.println("You purchased " + productMap.get(purchaseLocation).getProductName() + " for " + dollarFormat.format(productMap.get(purchaseLocation).getPrice()) + ". Your remaining balance is: " + dollarFormat.format(balance));
                    String type = productMap.get(purchaseLocation).getType();

                    printSaleMessage(type);

                    int currentQuantity = productMap.get(purchaseLocation).getQuantity();

                    productMap.get(purchaseLocation).setQuantity(currentQuantity - 1);

                    VendingMachineLog.logPurchase(productMap.get(purchaseLocation), balance);

                }
            } else System.out.println("Insufficient balance, please insert more funds");
        } else System.out.println("Item is sold out");
    }


    /**   giveChangeAndLog runs the makeChange method on current balance, then sets balance to zero and logs change given  **/
    private void giveChangeAndLog() {
        double oldBalance = balance;

        makeChange(balance);

        balance = 0;
        VendingMachineLog.logExit(dollarFormat.format(oldBalance), dollarFormat.format(balance));
    }


    /**   printSaleMessage checks product type and prints appropriate message  **/
    private void printSaleMessage(String type) {
        if (!usingGUI)
            if (type.equals("Candy")) {
                System.out.println("Candy swirl! You are my world!");
            } else if (type.equals("Chip")) {
                System.out.println("Chip Chip, HOORAY!");
            } else if (type.equals("Drink")) {
                System.out.println("You are Soda-lightful!");
            } else if (type.equals("Gum")) {
                System.out.println("Thanks for chew-sing this vending machine!");
            }
    }


    /**   makeChange runs the math on balance to get amount of quarters, dimes, and nickels and returns their quantities as an int[]   **/
    public int[] makeChange(double balance) {

        int quarters = (int) ((balance * 100) / 25);
        int remainder = (int) ((balance * 100) % 25);

        int dimes = remainder / 10;
        remainder = remainder % 10;

        int nickels = remainder / 5;

        if (!usingGUI) {

            System.out.println("From " + dollarFormat.format(balance) + " you get:");
            System.out.println(quarters + " quarters");
            System.out.println(dimes + " dimes");
            System.out.println(nickels + " nickels");
        }

        return new int[]{quarters, dimes, nickels};
    }

}