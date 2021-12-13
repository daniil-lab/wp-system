package com.wp.system.entity.bill;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class BillBalance {
    @Id
    private UUID id = UUID.randomUUID();

    private int amount;

    private int cents;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private Bill bill;

    public BillBalance() {};

    public BillBalance(Bill bill) {
        this.bill = bill;
    };

    public UUID getId() {
        return id;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCents() {
        return cents;
    }

    public void setCents(int cents) {
        this.cents = cents;
    }

    public void withdraw(int amount, int cents) {
        this.amount -= amount;
        this.cents -= cents;

        if(this.cents < 0) {
            this.amount -= 1;
            this.cents += 100;
        }
    }

    public void deposit(int amount, int cents) {
        this.amount += amount;
        this.cents += cents;

        if(this.cents >= 100) {
            this.amount += 1;
            this.cents -= 100;
        }
    }
}
