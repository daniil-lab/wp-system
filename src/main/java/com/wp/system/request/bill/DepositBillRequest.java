package com.wp.system.request.bill;

import com.wp.system.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.Instant;
import java.util.UUID;

public class DepositBillRequest {
    @Schema(required = true, description = "Целая часть баланса")
    @PositiveOrZero(message = ValidationErrorMessages.NEGATIVE_AMOUNT)
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private int amount;

    @Schema(required = true, description = "Вторая часть баланса (копейки)")
    @PositiveOrZero(message = ValidationErrorMessages.NEGATIVE_CENTS)
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private int cents;

    @Schema(required = false, description = "Комментарий к пополнению")
    private String description;

    @Schema(required = false, description = "Категория")
    private UUID categoryId;

    private Instant time;

    public DepositBillRequest() {}

    public DepositBillRequest(int amount, int cents, String description) {
        this.amount = amount;
        this.cents = cents;
        this.description = description;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
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
