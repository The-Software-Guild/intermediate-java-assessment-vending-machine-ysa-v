package com.sal.vendingmachine.service;

import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Item;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineServiceImplTest {

    VendingMachineService testService = new VendingMachineServiceImpl();

    VendingMachineServiceImplTest() throws VendingMachineException {
    }

    @Test
    void listAllItems() {

    }

    @Test
    @Description("changeInventoryCount will not change to a negative number")
    void changeInventoryCountTest() throws VendingMachineException {
        Item testItem = new Item("Test Candy", new BigDecimal("1"), 0);

        testService.changeInventoryCount(testItem, -1);

        assertEquals(testItem.getNumInventoryItems(), 0);
    }

    @Test
    void sellItem() {

    }
}