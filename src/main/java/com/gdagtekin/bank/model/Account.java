package com.gdagtekin.bank.model;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * @author Gökhan DAĞTEKİN
 *
 * 2019 - Bialystok University of Technology
 */
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
})
@Entity
public class Account extends AbstractModel {

    public static enum Type {
        SAVING, CREDIT, INTEREST
    };

    @Column(precision = 13, scale = 2)
    private BigDecimal balance;
    private String notes;
    @Enumerated(EnumType.STRING)
    private Type type;
    private boolean status;

    @ManyToOne
    @NotNull
    private Client client;

    @OneToMany
    private List<Operation> operations = new LinkedList<>();

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void add(Operation o) {
        this.operations.add(o);
    }

    public void remove(Operation o) {
        this.operations.remove(o);
    }

    @Override
    public String toString() {
        return "Account{" + "balance=" + balance + ", notes=" + notes + ", type=" + type + ", status=" + status + ", client=" + client + '}';
    }

}
