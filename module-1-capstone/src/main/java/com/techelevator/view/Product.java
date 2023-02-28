package com.techelevator.view;


public class Product {
    private String slotLocation;
    private String productName;
    private double price;
    private String type;
    private int quantity = 5;

    //private Map<String,Double> products;


    public Product(String slotLocation, String productName, double price, String type) {

        this.slotLocation = slotLocation;
        this.productName = productName;
        this.price = price;
        this.type = type;

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    //prints out the type when needed for the user experience
    public String getType() {


        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getSlotLocation() {
        return slotLocation;
    }

}