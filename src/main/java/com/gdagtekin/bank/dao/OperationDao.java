package com.gdagtekin.bank.dao;

import com.gdagtekin.bank.model.Operation;
import java.util.List;

/**
 * @author Gökhan DAĞTEKİN
 *
 * 2019 - Bialystok University of Technology
 */
public interface OperationDao {

    void create(Operation o);

    Operation update(Operation o);

    Operation findById(Long id);

    List<Operation> findAll();
}
