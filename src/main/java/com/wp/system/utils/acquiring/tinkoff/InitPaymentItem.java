package com.wp.system.utils.acquiring.tinkoff;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InitPaymentItem {
    @JsonProperty("Name")
    private String Name;

    @JsonProperty("Quantity")
    private Integer Quantity;

    @JsonProperty("Amount")
    private Integer Amount;

    @JsonProperty("Price")
    private Integer Price;

    @JsonProperty("Tax")
    private String Tax = "none";

    public InitPaymentItem() {}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        Price = price;
    }

    public String getTax() {
        return Tax;
    }

    public void setTax(String tax) {
        Tax = tax;
    }
}
