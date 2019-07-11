package com.gdagtekin.bank.service;

import com.gdagtekin.bank.model.Account;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

/**
 * @author Gökhan DAĞTEKİN
 *
 * 2019 - Bialystok University of Technology
 */
@Singleton
public class InterestServiceImpl implements InterestService {

    @EJB
    private AccountService accountService;
    private double interestRate = 9;

    @Schedule(second = "*/10", minute = "*", hour = "*", persistent = false)
    @Override
    public void startService() {
        List<Account> accounts = accountService.findAll();
        for (Account account : accounts) {
            if (account.getType() == Account.Type.INTEREST) {
                Double balance = account.getBalance().doubleValue();
                balance = balance + (balance * (interestRate / 10000));
                account.setBalance(BigDecimal.valueOf(balance));
                accountService.update(account);
            }
        }

    }

}
