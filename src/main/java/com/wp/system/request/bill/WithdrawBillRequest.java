package com.wp.system.request.bill;

import com.wp.system.other.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

public class WithdrawBillRequest {
    @Schema(required = true, description = "Целая часть баланса")
    @PositiveOrZero(message = ValidationErrorMessages.NEGATIVE_AMOUNT)
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private int amount;

    @Schema(required = true, description = "Вторая часть баланса (копейки)")
    @PositiveOrZero(message = ValidationErrorMessages.NEGATIVE_CENTS)
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private int cents;

    @Schema(required = false, description = "Комментарий к расходу")
    private String description;

    @Schema(required = false, description = "Категория, к которой относится расход")
    private UUID categoryId;

    @Schema(required = false, description = "Долгота места расхода")
    private Double lon;

    @Schema(required = false, description = "Широта места расхода")
    private Double lat;

    @Schema(required = false, description = "Место расхода. Если не передать, и будут в наличии lon и lat," +
            "проведет автоматическое обратное геокодирование, если данные долготы и широты будут отсутствовать," +
            "останется пустым")
    private String placeName;

    public WithdrawBillRequest() {}

    public WithdrawBillRequest(int amount, int cents, String description, UUID categoryId) {
        this.amount = amount;
        this.cents = cents;
        this.description = description;
        this.categoryId = categoryId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
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
