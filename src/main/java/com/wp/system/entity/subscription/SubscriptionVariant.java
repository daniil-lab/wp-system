package com.wp.system.entity.subscription;

import com.wp.system.entity.user.UserRole;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class SubscriptionVariant {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    private String description;

    private long expiration;

    private Double price;

    private Double newPrice;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "role_id")
    private UserRole role;

    public SubscriptionVariant() {}

    public SubscriptionVariant(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Double newPrice) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
