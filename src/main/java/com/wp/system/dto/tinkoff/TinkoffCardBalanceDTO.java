package com.wp.system.dto.tinkoff;

import com.wp.system.entity.BankBalance;

public class TinkoffCardBalanceDTO {
    private int amount;

    private int cents;

    public TinkoffCardBalanceDTO() {}

    public TinkoffCardBalanceDTO(BankBalance b) {
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
