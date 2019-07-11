package com.gdagtekin.bank.service;

import com.gdagtekin.bank.model.Account;
import java.util.List;

/**
 * @author Gökhan DAĞTEKİN
 *
 * 2019 - Bialystok University of Technology
 */
public interface AccountService {

    void create(Account t);

    void delete(Long id);

    Account update(Account t);

    Account findById(Long id);

    List<Account> findAll();
}
