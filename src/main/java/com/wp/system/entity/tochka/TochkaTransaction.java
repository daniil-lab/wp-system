package com.wp.system.entity.tochka;

import com.wp.system.entity.BankTransaction;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TochkaTransaction extends BankTransaction {
    private String sberId;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private TochkaCard card;

    private String status;

    private String description;

    public TochkaTransaction() {}

    public String getSberId() {
        return sberId;
    }

    public void setSberId(String sberId) {
        this.sberId = sberId;
    }

    public TochkaCard getCard() {
        return card;
    }

    public void setCard(TochkaCard card) {
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
