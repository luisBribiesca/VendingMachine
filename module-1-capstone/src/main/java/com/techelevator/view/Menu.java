package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    private PrintWriter out;
    private Scanner in;

    public Menu(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output);
        this.in = new Scanner(input);
    }

    //loops through an array, printing each element, and asking user which one they'd like to select by typing 1,2,3...
    public Object getChoiceFromOptions(Object[] options) {
        Object choice = null;
        while (choice == null) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    //part of above function, gets user input (1,2,3...)
    private Object getChoiceFromUserInput(Object[] options) {
        Object choice = null;
        String userInput = in.nextLine();
        try {
            int selectedOption = Integer.parseInt(userInput);
            if (selectedOption > 0 && selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch (NumberFormatException e) {
            // eat the exception, an error message will be displayed below since choice will be null
        }
        if (choice == null) {
            out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
        }
        return choice;
    }

    public void displayMenuOptions(Object[] options) {

        //loops through an array and prints first 3 elements to console.  4th element onward become hidden options

            out.println();

            for (int i = 0; i < options.length; i++) {
                if (i < 3) {
                    int optionNum = i + 1;
                    out.println(optionNum + ") " + options[i]);
                }
            }
            out.print(System.lineSeparator() + "Please choose an option >>> ");
            out.flush();
        }

        //asks user for integer to deposit and returns it
        public double getDepositAmount () {
            while (true) {
                double x = Double.parseDouble(in.nextLine());
                double y = Math.floor(x);
                if (x <= 0) {
                    System.out.println("Please enter a positive number.\n");
                } else if (x != y) {
                    System.out.println("Please enter whole dollar value\n");
                } else {
                    return x;
                }
            }
        }

        //asks user for location i.e. A9, and returns it
        public String getLocation (Map < String, Product > map){
            while (true) {
                String input = in.nextLine().toUpperCase();
                if (map.containsKey(input)) {
                    return input;
                } else {
                    System.out.println("Invalid input, please try another code");
                }
            }
        }

    }
