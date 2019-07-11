package com.gdagtekin.bank.dao;

import com.gdagtekin.bank.model.Client;
import java.util.List;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Gökhan DAĞTEKİN
 *
 * 2019 - Bialystok University of Technology
 */
@DataSourceDefinition(
        name = "java:global/task5datasource",
        className = "org.apache.derby.jdbc.ClientDataSource",
        minPoolSize = 1,
        initialPoolSize = 1,
        portNumber = 1527,
        serverName = "localhost",
        user = "app",
        password = "app",
        databaseName = "task5db",
        properties = {"connectionAttributes=;create=true"}
)
@Stateless
public class ClientDaoImpl implements ClientDao {

    @PersistenceContext(name = "com_mycompany_task5_PU")
    private EntityManager entityManager;

    @Override
    public void create(Client t) {
        entityManager.persist(t);
    }

    @Override
    public void delete(Long id) {
        Client o = entityManager.getReference(Client.class, id);
        entityManager.remove(o);
    }

    @Override
    public Client update(Client t) {
        return entityManager.merge(t);
    }

    @Override
    public Client findById(Long id) {
        return entityManager.find(Client.class, id);
    }

    @Override
    public List<Client> findAll() {
        return entityManager.createNamedQuery("Client.findAll", Client.class).getResultList();
    }

}
