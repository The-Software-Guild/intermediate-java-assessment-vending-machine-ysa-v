
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dto.Item;
import java.util.List;
import java.util.Map;

/**
 *
 * @author salajrawi
 */
public interface FileDao {
    public Item unmarshallItem(String line);
    public String marshallItem(Item item);
    public void writeFile(List<Item> list) throws VendingMachineException;
    public Map<String,Item> readFile(String file) throws VendingMachineException;
}
