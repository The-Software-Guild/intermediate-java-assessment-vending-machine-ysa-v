
package com.sal.vendingmachine.controller;

import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Coins;
import com.sal.vendingmachine.dto.Item;
import com.sal.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sal.vendingmachine.service.VendingMachineItemInventoryException;
import com.sal.vendingmachine.service.VendingMachineService;
import com.sal.vendingmachine.service.VendingMachineServiceImpl;
import com.sal.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Salajrawi
 */
public class VendingMachineController {
    
    private VendingMachineView view;
    private VendingMachineService service;

//    public VendingMachineController() throws VendingMachineException {
//
//    }
    
    public VendingMachineController(VendingMachineView view, VendingMachineService service) {
        this.view = view;
        this.service = service;
    }

    
    public void run() throws VendingMachineException {
        BigDecimal balance = new BigDecimal(0.0);
        boolean start = true;

        try{
            while (start) {
            view.printMenu();
            view.displayBalance(balance);
            String operation = view.getMenuSelection();
            switch(operation) {
                case "1":  // add funds
                    balance=addFunds(balance);
                    break;
                case "2":  // buy items
                    try{
                        balance=buyItems(balance);
                    }catch(VendingMachineItemInventoryException | VendingMachineInsufficientFundsException e){
                        view.displayBalance(balance);
                        view.displayErrorMessage(e.getMessage());
                    }
                    break;
                case "3": // quit
                    try{
                        quit(balance);
                    }catch(VendingMachineInsufficientFundsException e){
                        view.displayBalance(balance);
                        view.displayErrorMessage(e.getMessage());
                    }
                    start = false;
                    break;
                default:
                    view.displayUnknownCommand();
            }
        }
        }catch(VendingMachineException e){
            view.displayErrorMessage(e.getMessage());
        }
        
        view.displayQuitMessage();
    }
    
    public BigDecimal addFunds(BigDecimal balance){
        BigDecimal newFunds = view.displayAndGetFunds();
        return balance.add(newFunds);
    }
    
    public BigDecimal buyItems(BigDecimal balance) throws VendingMachineException, VendingMachineItemInventoryException, VendingMachineInsufficientFundsException{
        view.printAllItems(service.listAllItems());
        int itemIndex = view.getItemSelection() - 1;
        Item item = service.getItem(service.listAllItems().get(itemIndex).getName());
        BigDecimal newBalance = service.sellItem(balance, item);
        view.purchaseSucceeded();
        return newBalance;
    }
    
    public void quit(BigDecimal balance) throws VendingMachineInsufficientFundsException{
        Change change = new Change();
        view.printChanges(change.getChange(balance));
        balance = balance.subtract(balance);
        view.displayBalance(balance);
    }
}
