package com.wp.system.entity;

import com.wp.system.entity.bill.Bill;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.UUID;

@Embeddable
public class BankBalance {
    private int amount;

    private int cents;

    public BankBalance() {};

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
