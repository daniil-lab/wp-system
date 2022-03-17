package com.wp.system.dto.sber;

import com.wp.system.entity.BankBalance;
import com.wp.system.utils.WalletType;

import java.util.UUID;

public class SberCardBalanceDTO {
    private int amount;

    private int cents;

    public SberCardBalanceDTO() {}

    public SberCardBalanceDTO(BankBalance b) {
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
