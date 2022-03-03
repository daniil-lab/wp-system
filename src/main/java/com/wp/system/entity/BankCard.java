package com.wp.system.entity;

import com.wp.system.entity.image.SystemImage;

import javax.persistence.*;
import java.util.UUID;

@MappedSuperclass
public class BankCard {
    @Id
    private UUID id = UUID.randomUUID();

    @Embedded
    private BankCardBalance balance = new BankCardBalance();

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="icon_id")
    private SystemImage picture;

    public BankCard() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BankCardBalance getBalance() {
        return balance;
    }

    public void setBalance(BankCardBalance balance) {
        this.balance = balance;
    }

    public SystemImage getPicture() {
        return picture;
    }

    public void setPicture(SystemImage picture) {
        this.picture = picture;
    }
}
