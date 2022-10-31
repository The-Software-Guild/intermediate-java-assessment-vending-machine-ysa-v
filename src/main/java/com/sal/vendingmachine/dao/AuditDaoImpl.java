
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dao.AuditDao;
import com.sal.vendingmachine.dao.VendingMachineException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author salajrawi
 */
public class AuditDaoImpl implements AuditDao{

    public static final String AUDIT_FILE = "audit.txt";
    
    @Override
    public void writeAuditEntry(String entry) throws VendingMachineException{
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new VendingMachineException("Could not persist audit information.", e);
        }

        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " :: " + entry);
        out.flush();
    }
    
}
