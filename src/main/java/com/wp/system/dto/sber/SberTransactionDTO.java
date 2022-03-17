package com.wp.system.dto.sber;

import com.wp.system.entity.BankTransactionType;
import com.wp.system.entity.sber.SberTransaction;
import com.wp.system.entity.tinkoff.TinkoffTransaction;
import com.wp.system.utils.WalletType;

import java.time.Instant;
import java.util.UUID;

public class SberTransactionDTO {
    private UUID id;

    private String status;

    private SberTransactionAmountDTO amount;

    private String description;

    private WalletType currency;

    private BankTransactionType transactionType;

    private Instant date;

    public SberTransactionDTO() {}

    public SberTransactionDTO(SberTransaction t) {
        if(t == null)
            return;

        this.id = t.getId();
        this.status = t.getStatus();
        this.amount = t.getAmount() == null ? null : new SberTransactionAmountDTO(t.getAmount());
        this.description = t.getDescription();
        this.currency = t.getCurrency();
        this.transactionType = t.getTransactionType();
        this.date = t.getDate();
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

    public SberTransactionAmountDTO getAmount() {
        return amount;
    }

    public void setAmount(SberTransactionAmountDTO amount) {
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

    public BankTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(BankTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
}
