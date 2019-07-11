package com.gdagtekin.bank.service;

import com.gdagtekin.bank.dao.AccountDao;
import com.gdagtekin.bank.model.Account;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @author Gökhan DAĞTEKİN
 *
 * 2019 - Bialystok University of Technology
 */
@Stateless
public class AccountServiceImpl implements AccountService {

    @EJB
    private AccountDao accountDao;

    @Override
    public void create(Account t) {
        accountDao.create(t);
    }

    @Override
    public void delete(Long id) {
        accountDao.delete(id);
    }

    @Override
    public Account update(Account t) {
        return accountDao.update(t);
    }

    @Override
    public Account findById(Long id) {
        return accountDao.findById(id);
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

}
