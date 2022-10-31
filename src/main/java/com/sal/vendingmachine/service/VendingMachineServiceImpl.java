
package com.sal.vendingmachine.service;

import com.sal.vendingmachine.dao.AuditDao;
import com.sal.vendingmachine.dao.AuditDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dao.VendingMachineDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author salajrawi
 */
public class VendingMachineServiceImpl implements VendingMachineService{

    private VendingMachineDao dao;
    private AuditDao adao;

    public VendingMachineServiceImpl() throws VendingMachineException {
        throw new VendingMachineException("Item access and audit not implemented.");
    }
    
    public VendingMachineServiceImpl(VendingMachineDao dao, AuditDao adao) {
        this.dao = dao;
        this.adao = adao;
    }
    
    @Override
    public Item getItem(String name) throws VendingMachineException, VendingMachineItemInventoryException{
        return dao.getItem(name);
    }

    @Override
    public List<Item> listAllItems() throws VendingMachineException{
        return dao.listAllItems()
                .stream()
                .filter(item->item.getNumInventoryItems()>0)
                .collect(Collectors.toList());
    }

    @Override
    public Item addItem(Item item) throws VendingMachineException{
        return dao.addItem(item);
    }

    @Override
    public Item removeItem(Item item) throws VendingMachineException{
        return dao.removeItem(item);
    }

    @Override
    public Item changeInventoryCount(Item item, int newCount) throws VendingMachineException{
        if (newCount < 0) {
            newCount = 0;
        }
        dao.changeInventoryCount(item, newCount);
        return item;
    }

    @Override
    public BigDecimal sellItem(BigDecimal totalFunds, Item item) throws VendingMachineException, VendingMachineItemInventoryException, VendingMachineInsufficientFundsException{
        if (item.getNumInventoryItems() < 1) {
            throw new VendingMachineItemInventoryException("Sorry, that item is out of stock.");
        } else if (item.getCost().compareTo(totalFunds) != -1) {
            throw new VendingMachineInsufficientFundsException("Insufficient funds");
        } else {
            changeInventoryCount(item, item.getNumInventoryItems() - 1);
            adao.writeAuditEntry("Item " + item.getName() + " has been sold. Number in inventory: " + item.getNumInventoryItems());
            return totalFunds.subtract(item.getCost());
        }
    }
}
