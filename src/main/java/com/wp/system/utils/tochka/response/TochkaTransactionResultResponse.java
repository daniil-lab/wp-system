package com.wp.system.utils.tochka.response;

import java.util.List;

public class TochkaTransactionResultResponse {
    private String balance_closing;

    private String balance_opening;

    private List<TochkaTransactionResponse> payments;

    public TochkaTransactionResultResponse() {}

    @Override
    public String toString() {
        return "TochkaTransactionResultResponse{" +
                "balance_closing='" + balance_closing + '\'' +
                ", balance_opening='" + balance_opening + '\'' +
                ", payments=" + payments +
                '}';
    }

    public String getBalance_closing() {
        return balance_closing;
    }

    public void setBalance_closing(String balance_closing) {
        this.balance_closing = balance_closing;
    }

    public String getBalance_opening() {
        return balance_opening;
    }

    public void setBalance_opening(String balance_opening) {
        this.balance_opening = balance_opening;
    }

    public List<TochkaTransactionResponse> getPayments() {
        return payments;
    }

    public void setPayments(List<TochkaTransactionResponse> payments) {
        this.payments = payments;
    }
}
