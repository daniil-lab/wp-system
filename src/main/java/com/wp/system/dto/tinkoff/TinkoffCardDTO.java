package com.wp.system.dto.tinkoff;

import com.wp.system.entity.BankList;
import com.wp.system.entity.tinkoff.TinkoffCard;
import com.wp.system.utils.WalletType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class TinkoffCardDTO {
    private UUID id;

    private String cardNumber;

    private String name;

    private BigDecimal balance;

    private String cardId;

    private String status;

    private Instant expiration;

    private Instant createdInBank;

    private WalletType currency;

    private BankList bankName;

    public TinkoffCardDTO() {}

    public TinkoffCardDTO(TinkoffCard c) {
        if(c == null)
            return;

        this.id = c.getId();
        this.cardId = c.getCardId();
        this.cardNumber = c.getCardNumber();
        this.name = c.getName();
        this.status = c.getStatus();
        this.expiration = c.getExpirationMillis() == null ? null : Instant.ofEpochMilli(c.getExpirationMillis());
        this.createdInBank = c.getCreatedMillis() == null ? null : Instant.ofEpochMilli(c.getCreatedMillis());
        this.currency = c.getCurrency();
        this.bankName = c.getBankName();
        this.balance = c.getBalance();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BankList getBankName() {
        return bankName;
    }

    public void setBankName(BankList bankName) {
        this.bankName = bankName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }

    public Instant getCreatedInBank() {
        return createdInBank;
    }

    public void setCreatedInBank(Instant createdInBank) {
        this.createdInBank = createdInBank;
    }

    public WalletType getCurrency() {
        return currency;
    }

    public void setCurrency(WalletType currency) {
        this.currency = currency;
    }
}
