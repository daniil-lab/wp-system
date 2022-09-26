package com.wp.system.entity.tochka;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wp.system.entity.BankCard;
import com.wp.system.entity.BankList;
import com.wp.system.utils.WalletType;

import javax.persistence.*;
import java.util.Set;

@Entity
public class TochkaCard extends BankCard {
    private String cardNumber;

    private WalletType currency;

    private String cardAccount;

    private String type;

    private String bik;

    @JsonIgnore
    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TochkaTransaction> transactions;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="integration_id")
    private TochkaIntegration integration;

    public Set<TochkaTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TochkaTransaction> transactions) {
        this.transactions = transactions;
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

    public TochkaCard() {
        setBankName(BankList.SBER);
    }

    public TochkaIntegration getIntegration() {
        return integration;
    }

    public void setIntegration(TochkaIntegration integration) {
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

    public String getBik() {
        return bik;
    }

    public void setBik(String bik) {
        this.bik = bik;
    }
}
