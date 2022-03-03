package com.wp.system.request.bill;

import com.wp.system.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateBillRequest {
    @Schema(required = true, description = "ID пользователя")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private UUID userId;

    @Schema(required = true, description = "Название счета")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    @Length(min = 4, max = 64, message = ValidationErrorMessages.INVALID_BILL_NAME)
    private String name;

    @Schema(required = true, description = "Изначальный баланс")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private int balance;

    @Schema(required = true, description = "Изначальный баланс (копейки)")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    @Min(value = 0, message = ValidationErrorMessages.NEGATIVE_CENTS)
    @Max(value = 99, message = ValidationErrorMessages.MAXIMUM_CENTS)
    private int cents;

    public CreateBillRequest() {};

    public CreateBillRequest(UUID userId, String name, int balance, int cents) {
        this.userId = userId;
        this.name = name;
        this.balance = balance;
        this.cents = cents;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getCents() {
        return cents;
    }

    public void setCents(int cents) {
        this.cents = cents;
    }
}
