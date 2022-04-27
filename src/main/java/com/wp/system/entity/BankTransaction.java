package com.wp.system.entity;

import com.wp.system.entity.category.Category;
import com.wp.system.utils.WalletType;

import javax.persistence.*;
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

    @Column(columnDefinition = "TIMESTAMP")
    private Instant date;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    private Category category;

    public BankTransaction() {}

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

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
