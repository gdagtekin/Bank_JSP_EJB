package com.gdagtekin.bank.dao;

import com.gdagtekin.bank.model.Account;
import com.gdagtekin.bank.model.Client;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Gökhan DAĞTEKİN
 *
 * 2019 - Bialystok University of Technology
 */
@Stateless
public class AccountDaoImpl implements AccountDao {

    @PersistenceContext(name = "com_mycompany_task5_PU")
    private EntityManager entityManager;

    @Override
    public void create(Account t) {
        entityManager.persist(t);
        // reuqired to update cache when object from many side is saved in bidirectional relationship
        entityManager.getEntityManagerFactory().getCache().evict(Client.class, t.getClient().getId());
    }

    @Override
    public void delete(Long id) {
        Account o = entityManager.find(Account.class, id);
        entityManager.remove(o);
        // reuqired to update cache when object from many side is saved in bidirectional relationship
        entityManager.getEntityManagerFactory().getCache().evict(Client.class, o.getClient().getId());
    }

    @Override
    public Account update(Account t) {
        t = entityManager.merge(t);
        // reuqired to update cache when object from many side is saved in bidirectional relationship
        entityManager.getEntityManagerFactory().getCache().evict(Client.class, t.getClient().getId());
        return t;
    }

    @Override
    public Account findById(Long id) {
        return entityManager.find(Account.class, id);
    }

    @Override
    public List<Account> findAll() {
        return entityManager.createNamedQuery("Account.findAll", Account.class).getResultList();
    }

}
