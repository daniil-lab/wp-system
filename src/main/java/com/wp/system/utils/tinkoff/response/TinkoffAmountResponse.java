package com.wp.system.utils.tinkoff.response;

import com.wp.system.utils.tinkoff.response.cards.TinkoffCardBalanceCurrencyResponse;

public class TinkoffAmountResponse {
    private TinkoffCardBalanceCurrencyResponse currency;

    private Double value;

    public TinkoffAmountResponse() {}

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
