package com.gdagtekin.bank.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Gökhan DAĞTEKİN
 *
 * 2019 - Bialystok University of Technology
 */
@NamedQueries({
    @NamedQuery(name = "Operation.findAll", query = "SELECT a FROM Operation a")
    ,
    @NamedQuery(name = "Operation.findAccountOperations", query = "SELECT a FROM Operation a where a.source=:id or a.destination=:id")
})
@Entity
public class Operation extends AbstractModel {

    public static enum Type {
        WITHDRAW, DEPOSIT, TRANSFER, CHARGE, INTEREST
    };

    @Column(precision = 13, scale = 2)
    private BigDecimal amount;
    @Temporal(TemporalType.DATE)
    private Date date;
    @Enumerated(EnumType.STRING)
    private Type type;
    @ManyToOne
    private Account source;
    @ManyToOne
    private Account destination;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Account getSource() {
        return source;
    }

    public void setSource(Account source) {
        this.source = source;
    }

    public Account getDestination() {
        return destination;
    }

    public void setDestination(Account destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Operation{" + "amount=" + amount + ", date=" + date + ", type=" + type + ", source=" + source + ", destination=" + destination + '}';
    }

}
