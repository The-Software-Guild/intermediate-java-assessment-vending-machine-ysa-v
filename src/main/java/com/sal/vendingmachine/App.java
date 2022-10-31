
package com.sal.vendingmachine;

import com.sal.vendingmachine.controller.VendingMachineController;
import com.sal.vendingmachine.dao.*;
import com.sal.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sal.vendingmachine.service.VendingMachineItemInventoryException;
import com.sal.vendingmachine.service.VendingMachineService;
import com.sal.vendingmachine.service.VendingMachineServiceImpl;
import com.sal.vendingmachine.ui.UserIO;
import com.sal.vendingmachine.ui.UserIOImpl;
import com.sal.vendingmachine.ui.VendingMachineView;

/**
 *
 * @author salajrawi
 */
public class App {
    public static void main(String[] args) throws VendingMachineException{
        UserIO myIo = new UserIOImpl();
        VendingMachineView myView = new VendingMachineView(myIo);
        VendingMachineDao myDao = new VendingMachineDaoImpl();
        AuditDao myAuditDao = new AuditDaoImpl();
        VendingMachineService myService = new VendingMachineServiceImpl(myDao, myAuditDao);
        VendingMachineController myController = new VendingMachineController(myView, myService);
        myController.run();
    }
}
