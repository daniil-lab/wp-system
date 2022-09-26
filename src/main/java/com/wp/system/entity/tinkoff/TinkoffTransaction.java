package com.wp.system.entity.tinkoff;

import com.wp.system.entity.BankTransaction;

import javax.persistence.*;

@Entity
public class TinkoffTransaction extends BankTransaction {
    @Column(name = "_group")
    private String group;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private TinkoffCard card;

    private String status;

    private String description;

    private String tinkoffId;

    public TinkoffTransaction() {}

    public String getTinkoffId() {
        return tinkoffId;
    }

    public void setTinkoffId(String tinkoffId) {
        this.tinkoffId = tinkoffId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public TinkoffCard getCard() {
        return card;
    }

    public void setCard(TinkoffCard card) {
        this.card = card;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
