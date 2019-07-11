package com.gdagtekin.bank.service;

import com.gdagtekin.bank.dao.OperationDao;
import com.gdagtekin.bank.model.Operation;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @author Gökhan DAĞTEKİN
 *
 * 2019 - Bialystok University of Technology
 */
@Stateless
public class OperationServiceImpl implements OperationService {

    @EJB
    private OperationDao operationDao;

    @Override
    public void create(Operation t) {
        operationDao.create(t);
    }

    @Override
    public Operation update(Operation t) {
        return operationDao.update(t);
    }

    @Override
    public Operation findById(Long id) {
        return operationDao.findById(id);
    }

    @Override
    public List<Operation> findAll() {
        return operationDao.findAll();
    }

}
