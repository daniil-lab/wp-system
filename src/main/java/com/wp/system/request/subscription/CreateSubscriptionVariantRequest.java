package com.wp.system.request.subscription;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateSubscriptionVariantRequest {
    @NotNull
    private String name;

    private String description;

    @NotNull
    private double price;

    private double newPrice;

    @NotNull
    private int expiration;

    @NotNull
    private UUID roleId;

    public CreateSubscriptionVariantRequest() {}

    public CreateSubscriptionVariantRequest(String name, String description, double price, double newPrice, int expiration) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.newPrice = newPrice;
        this.expiration = expiration;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }
}
