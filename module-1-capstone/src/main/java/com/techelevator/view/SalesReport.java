package com.techelevator.view;

import com.techelevator.VendingMachineCLI;

import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

public class SalesReport {

    double totalSales = 0;

    static PrintWriter writer;

    static String isoDate = LocalDate.parse(LocalDate.now().toString()).format(DateTimeFormatter.ISO_DATE);

    public static void generateSalesReport(Map<String, Product> productMap) {
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance();

        //keep track of to money made
        double total = 0;

        //gets today's date for salesReport file name
        Date date = new Date();
        String strDateFormat = "HHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);


//+ sdf.format(date)
        //open new SalesReport file
        File salesReport = new File("src/main/resources/SalesReport-" + isoDate + "-" + sdf.format(date) + ".txt");


        //print required data to new SalesReport
        try {
            //open writer
            if (writer == null) {
                writer = new PrintWriter(new BufferedWriter(new FileWriter(salesReport, true)));

            }
            //move through productMap, printing required data
            for (Map.Entry<String, Product> product : productMap.entrySet()) {

                writer.printf("%-20s %s %2d\n", product.getValue().getProductName(), "|", (5 - (product.getValue().getQuantity())));
                total += (((5 - (product.getValue().getQuantity())))) * (product.getValue().getPrice());

            }

            writer.println("\nTotal Sales: " + dollarFormat.format(total));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writer.flush();  //need to flush data before close, so that data in stream gets push to file
        writer.close();


    }
}
