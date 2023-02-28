package com.techelevator.view;

import com.techelevator.VendingMachineCLI;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class VendingMachineCLITests {
    Menu menu = new Menu(System.in,System.out);
    VendingMachineCLI vm = new VendingMachineCLI(menu);

    @Test
    public void shouldCreateVendingMachine(){

        Assert.assertEquals(vm.getMenu(), menu);

    }

    @Test
    public void shouldCreateProductMap (){
        Map<String, Product> map = vm.createProductMap();

        Assert.assertTrue(map.containsKey("B4"));
        Assert.assertEquals(map.get("A1").getProductName(), "Potato Crisps");

    }

    @Test
    public void shouldMakeChange(){
        vm.setBalance(1.50);
        int [] correctArr = {6,0,0};

        Assert.assertArrayEquals(vm.makeChange(vm.getBalance()), correctArr);

        vm.setBalance(0);
        int [] correctArr2 = {0,0,0};

        Assert.assertArrayEquals(vm.makeChange(vm.getBalance()), correctArr2);

        vm.setBalance(2.65);
        int [] correctArr3 = {10,1,1};

        Assert.assertArrayEquals(vm.makeChange(vm.getBalance()), correctArr3);

    }


}
