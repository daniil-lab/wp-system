package com.wp.system.entity;

import com.wp.system.entity.image.SystemImage;

import javax.persistence.*;
import java.util.UUID;

@MappedSuperclass
public class BankCard {
    @Id
    private UUID id = UUID.randomUUID();

    @Embedded
    private BankBalance balance = new BankBalance();

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="icon_id")
    private SystemImage picture;

    private BankList bankName;

    public BankCard() {}

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

    public BankBalance getBalance() {
        return balance;
    }

    public void setBalance(BankBalance balance) {
        this.balance = balance;
    }

    public SystemImage getPicture() {
        return picture;
    }

    public void setPicture(SystemImage picture) {
        this.picture = picture;
    }
}
