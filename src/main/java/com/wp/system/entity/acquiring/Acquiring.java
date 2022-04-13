package com.wp.system.entity.acquiring;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Acquiring {
    @Id
    private UUID id = UUID.randomUUID();

    private Long bankPaymentId;

    private String terminalKey;

    private UUID variantId;

    private UUID userId;

    private Long amount;

    private String orderId;

    public Acquiring() {}

    public UUID getVariantId() {
        return variantId;
    }

    public void setVariantId(UUID variantId) {
        this.variantId = variantId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getBankPaymentId() {
        return bankPaymentId;
    }

    public void setBankPaymentId(Long bankPaymentId) {
        this.bankPaymentId = bankPaymentId;
    }

    public String getTerminalKey() {
        return terminalKey;
    }

    public void setTerminalKey(String terminalKey) {
        this.terminalKey = terminalKey;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
