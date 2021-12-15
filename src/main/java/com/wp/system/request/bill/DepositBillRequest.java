package com.wp.system.request.bill;

import com.wp.system.other.ValidationErrorMessages;
import org.checkerframework.checker.nullness.compatqual.NonNullType;
import reactor.util.annotation.NonNull;

import javax.annotation.Nonnegative;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class DepositBillRequest {
    @Positive(message = ValidationErrorMessages.NEGATIVE_AMOUNT)
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private int amount;

    @PositiveOrZero(message = ValidationErrorMessages.NEGATIVE_CENTS)
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private int cents;

    private String description;

    public DepositBillRequest() {}

    public DepositBillRequest(int amount, int cents, String description) {
        this.amount = amount;
        this.cents = cents;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
