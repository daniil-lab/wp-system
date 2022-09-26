package com.wp.system.request.bill;

import com.wp.system.utils.WalletType;
import com.wp.system.utils.bill.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public class UpdateBillTransactionRequest {
    private TransactionType action;

    private BigDecimal amount;

    private String description;

    private WalletType currency;

    private String geocodedPlace;

    private Double longitude;

    private Double latitude;

    private UUID billId;

    private UUID categoryId;

    public UpdateBillTransactionRequest() {}

    public TransactionType getAction() {
        return action;
    }

    public void setAction(TransactionType action) {
        this.action = action;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public UUID getBillId() {
        return billId;
    }

    public void setBillId(UUID billId) {
        this.billId = billId;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
}
