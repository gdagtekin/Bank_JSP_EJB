package com.gdagtekin.bank.dao;

import com.gdagtekin.bank.model.Client;
import java.util.List;

/**
 * @author Gökhan DAĞTEKİN
 *
 * 2019 - Bialystok University of Technology
 */
public interface ClientDao {

    void create(Client t);

    void delete(Long id);

    Client update(Client t);

    Client findById(Long id);

    List<Client> findAll();
}
