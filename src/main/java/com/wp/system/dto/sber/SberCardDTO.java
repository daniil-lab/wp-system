package com.wp.system.dto.sber;

import com.wp.system.entity.BankList;
import com.wp.system.entity.sber.SberCard;
import com.wp.system.utils.WalletType;

import java.math.BigDecimal;
import java.util.UUID;

public class SberCardDTO {
    private UUID id;

    private String cardNumber;

    private String description;

    private String name;

    private String cardId;

    private String status;

    private String expireDate;

    private String cardAccount;

    private BigDecimal balance;

    private String cardName;

    private BankList bankName;

    public SberCardDTO() {}

    public SberCardDTO(SberCard c) {
        if(c == null)
            return;

        this.id = c.getId();
        this.balance = c.getBalance();
        this.cardId = c.getCardId();
        this.cardName = c.getName();
        this.cardAccount = c.getCardAccount();
        this.cardNumber = c.getCardNumber();
        this.expireDate = c.getExpireDate();
        this.status = c.getStatus();
        this.name = c.getName();
        this.description = c.getDescription();
        this.bankName = c.getBankName();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getCardAccount() {
        return cardAccount;
    }

    public void setCardAccount(String cardAccount) {
        this.cardAccount = cardAccount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
