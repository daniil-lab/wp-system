package com.wp.system.utils.tinkoff.response.cards;

public class TinkoffCardBalanceCurrencyResponse {
    private Integer code;

    private String name;

    public TinkoffCardBalanceCurrencyResponse() {}

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
