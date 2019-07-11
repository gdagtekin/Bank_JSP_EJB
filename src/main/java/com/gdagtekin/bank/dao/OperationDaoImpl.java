package com.gdagtekin.bank.dao;

import com.gdagtekin.bank.model.Operation;
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
public class OperationDaoImpl implements OperationDao {

    @PersistenceContext(name = "com_mycompany_task5_PU")
    private EntityManager entityManager;

    @Override
    public void create(Operation t) {
        entityManager.persist(t);
        // reuqired to update cache when object from many side is saved in bidirectional relationship
    }

    @Override
    public Operation update(Operation t) {
        t = entityManager.merge(t);
        // reuqired to update cache when object from many side is saved in bidirectional relationship
        return t;
    }

    @Override
    public Operation findById(Long id) {
        return entityManager.find(Operation.class, id);
    }

    @Override
    public List<Operation> findAll() {
        return entityManager.createNamedQuery("Operation.findAll", Operation.class).getResultList();

    }
}
