package com.wp.system.entity.sber;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wp.system.entity.BankCard;
import com.wp.system.entity.BankList;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.tinkoff.TinkoffTransaction;
import com.wp.system.utils.WalletType;

import javax.persistence.*;
import java.util.Set;

@Entity
public class SberCard extends BankCard {
    private String cardNumber;

    private String description;

    private String name;

    private String cardId;

    private String status;

    private String expireDate;

    private WalletType currency;

    private String cardAccount;

    private String type;

    private Boolean hidden = false;

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SberTransaction> transactions;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="integration_id")
    private SberIntegration integration;

    public Set<SberTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<SberTransaction> transactions) {
        this.transactions = transactions;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SberCard() {
        setBankName(BankList.SBER);
    }

    public SberIntegration getIntegration() {
        return integration;
    }

    public void setIntegration(SberIntegration integration) {
        this.integration = integration;
    }

    public WalletType getCurrency() {
        return currency;
    }

    public void setCurrency(WalletType currency) {
        this.currency = currency;
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
}
