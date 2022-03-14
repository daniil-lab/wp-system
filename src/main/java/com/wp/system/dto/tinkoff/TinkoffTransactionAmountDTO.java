package com.wp.system.dto.tinkoff;

import com.wp.system.entity.BankBalance;

public class TinkoffTransactionAmountDTO {
    private int amount;

    private int cents;

    public TinkoffTransactionAmountDTO() {}

    public TinkoffTransactionAmountDTO(BankBalance b) {
        if(b == null)
            return;

        this.amount = b.getAmount();
        this.cents = b.getCents();
    }
}
