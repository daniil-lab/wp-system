package com.wp.system.utils.tinkoff.response.cards;

public class TinkoffCardsResponse {
    private TinkoffCardBalanceResponse availableBalance;

    private String id;

    private String status;

    private String value;

    private String name;

    private TinkoffCardExpirationResponse expiration;

    public TinkoffCardsResponse() {}

    public TinkoffCardBalanceResponse getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(TinkoffCardBalanceResponse availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TinkoffCardExpirationResponse getExpiration() {
        return expiration;
    }

    public void setExpiration(TinkoffCardExpirationResponse expiration) {
        this.expiration = expiration;
    }
}
