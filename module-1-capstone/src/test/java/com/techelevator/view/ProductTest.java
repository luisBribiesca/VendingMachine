package com.techelevator.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ProductTest {
    Map<String, Product> productMap = new TreeMap<>();

    @Before
    public void makeProducts() {
        try (
                Scanner inventoryScanner = new Scanner(new File("vendingmachine.csv"))) {
            while (inventoryScanner.hasNextLine()) {
                String currentLine = inventoryScanner.nextLine();
                String[] splitString = currentLine.split("\\|");
                productMap.put(splitString[0], new Product(splitString[0], splitString[1], Double.parseDouble(splitString[2]), splitString[3]));
            }
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getTypeChipTest(){
        Assert.assertEquals(productMap.get("A1").getType(), "Chip");
    }
    @Test
    public void getTypeCandyTest(){
        Assert.assertEquals(productMap.get("B1").getType(), "Candy");
    }
    @Test
    public void getTypeDrinkTest(){
        Assert.assertEquals(productMap.get("C1").getType(), "Drink");
    }
    @Test
    public void getTypeGumTest(){
        Assert.assertEquals(productMap.get("D1").getType(), "Gum");
    }
    @Test
    public void getTypeLowerCaseTest(){
        Assert.assertNull(productMap.get("d1"));
    }
    @Test
    public void shouldBuildProduct(){
        Product apple = new Product("E1", "apple", .50, "fruit");
        Assert.assertEquals(apple.getType(), "fruit");
        Assert.assertEquals(apple.getPrice(), .50, 0);
        Assert.assertEquals(apple.getSlotLocation(), "E1");



    }

}
