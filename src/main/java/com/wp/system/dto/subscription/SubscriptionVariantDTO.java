package com.wp.system.dto.subscription;

import com.wp.system.entity.subscription.SubscriptionVariant;

import java.util.UUID;

public class SubscriptionVariantDTO {
    private UUID id;

    private String name;

    private String description;

    private long expiration;

    private double price;

    private double newPrice;

    private SubscriptionVariantRoleDTO role;

    public SubscriptionVariantDTO() {}

    public SubscriptionVariantDTO(SubscriptionVariant variant) {
        if(variant == null)
            return;

        this.id = variant.getId();
        this.name = variant.getName();
        this.description = variant.getDescription();
        this.expiration = variant.getExpiration();
        this.price = variant.getPrice();
        this.newPrice = variant.getNewPrice();
        this.role = variant.getRole() == null ? null : new SubscriptionVariantRoleDTO(variant.getRole());
    }

    public SubscriptionVariantRoleDTO getRole() {
        return role;
    }

    public void setRole(SubscriptionVariantRoleDTO role) {
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }
}
