
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dto.Item;
import java.util.List;

/**
 *
 * @author Salajrawi
 */
public interface VendingMachineDao {
    Item getItem(String name) throws VendingMachineException;
    List<Item> listAllItems() throws VendingMachineException;
    Item addItem(Item item) throws VendingMachineException;
    Item removeItem(Item item) throws VendingMachineException;
    Item changeInventoryCount(Item item, int newCount) throws VendingMachineException;
}
