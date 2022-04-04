package com.wp.system.request.subscription;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class UpdateSubscriptionVariantRequest {
    private String name;

    private String description;

    private Double price;

    private Double newPrice;

    private Integer expiration;

    private UUID roleId;

    public UpdateSubscriptionVariantRequest() {}

    public UpdateSubscriptionVariantRequest(String name, String description, double price, double newPrice, int expiration) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Double newPrice) {
        this.newPrice = newPrice;
    }

    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }
}
