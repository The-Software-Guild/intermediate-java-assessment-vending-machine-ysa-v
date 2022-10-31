
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dao.FileDao;
import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Item;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salajrawi
 */
public class FileDaoImpl implements FileDao{

    private static final String ITEM_FILE = "items.txt";
    private static final String DELIMITER = ",";

    private Map<String,Item> itemMap = new HashMap<>();

    @Override
    public Item unmarshallItem(String line) {
        String[] itemTokens = line.split(DELIMITER);
        String itemName = itemTokens[0];
        BigDecimal itemCost = new BigDecimal(itemTokens[1]);
        int itemInventory = Integer.parseInt(itemTokens[2]);
        return new Item(itemName, itemCost, itemInventory);
    }

    @Override
    public String marshallItem(Item item) {
        return item.getName() + DELIMITER + item.getCost() + DELIMITER + item.getNumInventoryItems();
    }

    @Override
    public void writeFile(List<Item> list) throws VendingMachineException{
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ITEM_FILE));
        }
        catch(IOException e)
        {
            throw new VendingMachineException("Could not save item data", e);
        }

        String itemAsText;
        for (Item currentItem : list) {
            itemAsText = marshallItem(currentItem);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }

    @Override
    public Map<String,Item> readFile(String file) throws VendingMachineException{
        Scanner scanner;

        try {
            scanner = new Scanner((new BufferedReader(new FileReader(file))));
        }
        catch(FileNotFoundException e)
        {
            throw new VendingMachineException("File not found", e);
        }

        String currentLine;
        Item currentItem;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
            itemMap.put(currentItem.getName(), currentItem);
        }
        return itemMap;
    }
    
}
