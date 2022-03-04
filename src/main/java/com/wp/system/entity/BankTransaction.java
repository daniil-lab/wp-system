package com.wp.system.entity;

import com.wp.system.utils.WalletType;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
public class BankTransaction {
    @Id
    private UUID id = UUID.randomUUID();

    @Embedded
    private BankBalance amount = new BankBalance();

    private WalletType currency;

    private BankTransactionType transactionType;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant date;

    public BankTransaction() {}

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BankBalance getAmount() {
        return amount;
    }

    public void setAmount(BankBalance amount) {
        this.amount = amount;
    }

    public WalletType getCurrency() {
        return currency;
    }

    public void setCurrency(WalletType currency) {
        this.currency = currency;
    }

    public BankTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(BankTransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
