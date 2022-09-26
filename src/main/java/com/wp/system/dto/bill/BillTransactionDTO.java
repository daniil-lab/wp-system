package com.wp.system.dto.bill;

import com.wp.system.dto.category.CategoryDTO;
import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.utils.WalletType;
import com.wp.system.utils.bill.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

public class BillTransactionDTO {
    @Schema(description = "ID транзакции")
    private UUID id;

    @Schema(description = "Действие транзакции")
    private TransactionType action;

    @Schema(description = "Сумма транзакции")
    private BigDecimal sum;

    @Schema(description = "Комментарий транзакции")
    private String description;

    @Schema(description = "Валюта транзакции")
    private WalletType currency;

    @Schema(description = "Место транзакции")
    private String geocodedPlace;

    @Schema(description = "Широта места транзакции")
    private Double longitude;

    @Schema(description = "Долгота места транзакции")
    private Double latitude;

    @Schema(description = "Категория, к которой относится транзакции")
    private CategoryDTO category;

    @Schema(description = "Дата транзакции")
    private String createAt;

    private BillDTO bill;

    public BillTransactionDTO() {}

    public BillTransactionDTO(BillTransaction transaction) {
        this.id = transaction.getId();
        this.action = transaction.getAction();
        this.sum = transaction.getSum();
        this.description = transaction.getDescription();
        this.currency = transaction.getCurrency();
        this.longitude = transaction.getLongitude();
        this.latitude = transaction.getLatitude();
        this.category = transaction.getCategory() == null ? null : new CategoryDTO(transaction.getCategory());
        this.createAt = transaction.getCreateAt() == null ? null : transaction.getCreateAt().toString();
        this.geocodedPlace = transaction.getGeocodedPlace();
        this.bill = transaction.getBill() == null ? null : new BillDTO(transaction.getBill());
    }

    public BillDTO getBill() {
        return bill;
    }

    public void setBill(BillDTO bill) {
        this.bill = bill;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TransactionType getAction() {
        return action;
    }

    public void setAction(TransactionType action) {
        this.action = action;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WalletType getCurrency() {
        return currency;
    }

    public void setCurrency(WalletType currency) {
        this.currency = currency;
    }

    public String getGeocodedPlace() {
        return geocodedPlace;
    }

    public void setGeocodedPlace(String geocodedPlace) {
        this.geocodedPlace = geocodedPlace;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }
}
