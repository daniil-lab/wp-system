package com.wp.system.dto.sber;

import com.wp.system.dto.category.CategoryDTO;
import com.wp.system.entity.sber.SberTransaction;
import com.wp.system.entity.tinkoff.TinkoffTransaction;
import com.wp.system.utils.WalletType;
import com.wp.system.utils.bill.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class SberTransactionDTO {
    private UUID id;

    private String status;

    private BigDecimal amount;

    private String description;

    private WalletType currency;

    private TransactionType transactionType;

    private Instant date;

    private CategoryDTO category;

    public SberTransactionDTO() {}

    public SberTransactionDTO(SberTransaction t) {
        if(t == null)
            return;

        this.id = t.getId();
        this.status = t.getStatus();
        this.amount = t.getAmount();
        this.description = t.getDescription();
        this.currency = t.getCurrency();
        this.transactionType = t.getTransactionType();
        this.date = t.getDate();
        this.category = t.getCategory() == null ? null : new CategoryDTO(t.getCategory());
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WalletType getCurrency() {
        return currency;
    }

    public void setCurrency(WalletType currency) {
        this.currency = currency;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
}
