package com.wp.system.dto;

import com.wp.system.dto.category.CategoryDTO;
import com.wp.system.entity.BankTransactionType;
import com.wp.system.utils.WalletType;

import java.time.Instant;
import java.util.UUID;

public class AbstractTransactionDTO {
    private UUID id;

    private String type;

    private String description;

    private String transactionType;

    private Instant date;

    private CategoryDTO category;

    private Double sum;

    private WalletType currency;

    private String billName;

    private String billId;

    public AbstractTransactionDTO() {}

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
