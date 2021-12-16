package com.wp.system.dto.bill;

import com.wp.system.entity.bill.BillBalance;
import io.swagger.v3.oas.annotations.media.Schema;

public class BillBalanceDTO {

    @Schema(description = "Целочисленая часть баланса")
    private int amount;

    @Schema(description = "Десятичная часть баланса (копейки)")
    private int cents;

    public BillBalanceDTO() {}

    public BillBalanceDTO(BillBalance balance) {
        this.amount = balance.getAmount();
        this.cents = balance.getCents();
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
