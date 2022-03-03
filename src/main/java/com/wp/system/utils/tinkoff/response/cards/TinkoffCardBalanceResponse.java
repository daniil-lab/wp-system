package com.wp.system.utils.tinkoff.response.cards;

public class TinkoffCardBalanceResponse {
    private TinkoffCardBalanceCurrencyResponse currency;

    private Double value;

    public TinkoffCardBalanceResponse() {}

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public TinkoffCardBalanceCurrencyResponse getCurrency() {
        return currency;
    }

    public void setCurrency(TinkoffCardBalanceCurrencyResponse currency) {
        this.currency = currency;
    }
}
