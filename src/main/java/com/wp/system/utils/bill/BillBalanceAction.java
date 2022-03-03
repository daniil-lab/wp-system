package com.wp.system.utils.bill;

public enum BillBalanceAction {
    WITHDRAW("Расход"),
    DEPOSIT("Внесение");

    private String paymentType;

    BillBalanceAction(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }
}
