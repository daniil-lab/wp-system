package com.wp.system.entity.category;

import com.wp.system.entity.user.User;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Category {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    private String hexColor;

    private String description;

    private int categoryLimit;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;

    private String categoryIcon;

    public Category() {};

    public Category(String name, String hexColor, User user, String icon) {
        this.name = name;
        this.hexColor = hexColor;
        this.user = user;
        this.categoryIcon = icon;
    }

    public Category(String name, String hexColor, String description, User user, String icon) {
        this.name = name;
        this.hexColor = hexColor;
        this.description = description;
        this.user = user;
        this.categoryIcon = icon;
    }

    public int getCategoryLimit() {
        return categoryLimit;
    }

    public void setCategoryLimit(int categoryLimit) {
        this.categoryLimit = categoryLimit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }
}
