package com.wp.system.entity.sber;

import com.wp.system.entity.BankTransaction;
import com.wp.system.entity.tinkoff.TinkoffCard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SberTransaction extends BankTransaction {
    private String sberId;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private SberCard card;

    private String status;

    private String description;

    public SberTransaction() {}

    public String getSberId() {
        return sberId;
    }

    public void setSberId(String sberId) {
        this.sberId = sberId;
    }

    public SberCard getCard() {
        return card;
    }

    public void setCard(SberCard card) {
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
