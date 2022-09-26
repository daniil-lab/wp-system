package com.wp.system.utils.bill;

public enum TransactionType {
    WITHDRAW("Расход"),
    DEPOSIT("Внесение");

    private String paymentType;

    TransactionType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }
}
