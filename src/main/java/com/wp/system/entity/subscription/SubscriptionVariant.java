package com.wp.system.entity.subscription;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class SubscriptionVariant {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    private String description;

    private long expiration;

    private double price;

    private double newPrice;

    public SubscriptionVariant() {}

    public SubscriptionVariant(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public UUID getId() {
        return id;
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

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
