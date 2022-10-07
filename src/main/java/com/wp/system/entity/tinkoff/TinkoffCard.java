package com.wp.system.entity.tinkoff;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wp.system.entity.BankCard;
import com.wp.system.entity.BankList;
import com.wp.system.entity.bill.Bill;
import com.wp.system.utils.WalletType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
public class TinkoffCard extends BankCard {
    private String cardNumber;

    private String name;

    private String cardId;

    private String status;

    private Long expirationMillis;

    private WalletType currency;

    private Long createdMillis;

    private Boolean hidden = false;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="integration_id")
    private TinkoffIntegration integration;

    @JsonIgnore
    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TinkoffTransaction> transactions;

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public TinkoffCard() {
        setBankName(BankList.TINKOFF);
    }

    public Long getCreatedMillis() {
        return createdMillis;
    }

    public void setCreatedMillis(Long createdMillis) {
        this.createdMillis = createdMillis;
    }

    public Set<TinkoffTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TinkoffTransaction> transactions) {
        this.transactions = transactions;
    }

    public TinkoffIntegration getIntegration() {
        return integration;
    }

    public void setIntegration(TinkoffIntegration integration) {
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

    public Long getExpirationMillis() {
        return expirationMillis;
    }

    public void setExpirationMillis(Long expirationMillis) {
        this.expirationMillis = expirationMillis;
    }
}
