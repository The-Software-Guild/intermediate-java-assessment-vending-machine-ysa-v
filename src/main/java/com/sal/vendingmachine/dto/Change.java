
package com.sal.vendingmachine.dto;

import com.sal.vendingmachine.service.VendingMachineInsufficientFundsException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author salajrawi
 */
public class Change {
    private HashMap<Coins,Integer> coinChangeMap=new HashMap<>();

    public Change() {
    }
    
    public LinkedHashMap<Coins,Integer> getChange(BigDecimal funds) throws VendingMachineInsufficientFundsException{
        if (funds.compareTo(new BigDecimal("0")) != 1){
            throw new VendingMachineInsufficientFundsException("No change due.");
        } else {
            LinkedHashMap<Coins,Integer> changeDue = new LinkedHashMap<Coins, Integer>();

            for (Coins coin: Coins.toList()) {
                BigDecimal[] res = funds.divideAndRemainder(coin.getValue());
                changeDue.put(coin, res[0].intValue());
                funds = res[1];
            }

            return changeDue;
        }
    }
    
}
