package com.wp.system.dto.tochka;

import com.wp.system.entity.BankBalance;

public class TochkaCardBalanceDTO {
    private int amount;

    private int cents;

    public TochkaCardBalanceDTO() {}

    public TochkaCardBalanceDTO(BankBalance b) {
        if(b == null)
            return;

        this.amount = b.getAmount();
        this.cents = b.getCents();
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
}
