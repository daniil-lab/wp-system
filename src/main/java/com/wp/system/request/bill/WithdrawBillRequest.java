package com.wp.system.request.bill;

import com.wp.system.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class WithdrawBillRequest {
    @Schema(required = true, description = "Сумма")
    @PositiveOrZero(message = ValidationErrorMessages.NEGATIVE_AMOUNT)
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private BigDecimal amount;

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

    private Instant time;

    public WithdrawBillRequest() {}

    public WithdrawBillRequest(BigDecimal amount, String description, UUID categoryId) {
        this.amount = amount;
        this.description = description;
        this.categoryId = categoryId;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
