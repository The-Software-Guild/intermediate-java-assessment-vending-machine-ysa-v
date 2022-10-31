
package com.sal.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author salajrawi
 */
public enum Coins {
    QUARTER(new BigDecimal(0.25)),
    DIME(new BigDecimal(0.1)),
    NICKLE(new BigDecimal(0.05)),
    PENNY(new BigDecimal(0.01));


    private final BigDecimal value;

    private Coins(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue()
    {
        return value.setScale(2, RoundingMode.DOWN);
    }

    public static List<Coins> toList() {
        List<Coins> list = new ArrayList<Coins>();
        list.add(QUARTER);
        list.add(DIME);
        list.add(NICKLE);
        list.add(PENNY);

        return list;
    }
}
