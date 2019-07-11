package com.gdagtekin.bank.model;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;

/**
 * @author Gökhan DAĞTEKİN
 *
 * 2019 - Bialystok University of Technology
 */
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c")
})
@Entity
public class Client extends AbstractModel {

    private String firstName;
    private String lastName;
    @Email
    private String email;
    /*
      Marks accounts field as representation of many-to-one relationship.
      Parameter mappedBy denotes the name of the field in Account class that will be mapped to foreign key (is responsible
      for the relation in the database).
      Parameter cascade cases says that all operations (PERSIST, MERGE, REMOVE, ...) will be propagated from client object to
      related accounts objects - for example: if we invoke persist on client object (which cases insert for Client), persist
      operation will propagate to all account objects in the accounts list, so that the client and its account object will
      be saved together just by invoking persist operation on client. Similarly, if we remove client object by calling
      remove operation on on client object, the remove operation will be propagated to accounts object, so as a result
      a client and its account object will be removed together.
      The orphanRemoval parameter causes that if we remove specific account from the accounts list in client (by calling client.remove(account)),
      and then we update account in the database (by calling clientDao.update(client)), the account that is absent in the list
      will be also removed from the database.
     */
    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Account> accounts = new LinkedList<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Adds account a to the list of accounts of this client
     *
     * @param a
     */
    public void add(Account a) {
        this.accounts.add(a);
        a.setClient(this);
    }

    /**
     * Removes account a from the list of accounts of this client
     *
     * @param a
     */
    public void remove(Account a) {
        this.accounts.remove(a);
        a.setClient(null);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + getId() + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + '}';
    }

}
