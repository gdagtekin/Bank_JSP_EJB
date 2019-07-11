package com.gdagtekin.bank.service;

import com.gdagtekin.bank.dao.ClientDao;
import com.gdagtekin.bank.model.Client;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @author Gökhan DAĞTEKİN
 *
 * 2019 - Bialystok University of Technology
 */
@Stateless
public class ClientServiceImpl implements ClientService {

    @EJB
    private ClientDao clientDao;

    @Override
    public void create(Client t) {
        clientDao.create(t);
    }

    @Override
    public void delete(Long id) {
        clientDao.delete(id);
    }

    @Override
    public Client update(Client t) {
        return clientDao.update(t);
    }

    @Override
    public Client findById(Long id) {
        return clientDao.findById(id);
    }

    @Override
    public List<Client> findAll() {
        return clientDao.findAll();
    }

}
