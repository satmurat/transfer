package com.ironbank.transfer.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column
    @Type(type = "pg-uuid")
    private UUID id;
    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    public Account(UUID id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }

    public Account() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
