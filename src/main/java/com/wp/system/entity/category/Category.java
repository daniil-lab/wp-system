package com.wp.system.entity.category;

import com.wp.system.entity.user.User;
import com.wp.system.other.CategoryColor;
import com.wp.system.other.CategoryIcon;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Category {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    private CategoryColor color;

    private String description;

    private int categoryLimit;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;

    private CategoryIcon categoryIcon;

    public Category() {};

    public Category(String name, CategoryColor categoryColor, User user, CategoryIcon icon) {
        this.name = name;
        this.color = categoryColor;
        this.user = user;
        this.categoryIcon = icon;
    }

    public Category(String name, CategoryColor categoryColor, String description, User user, CategoryIcon icon) {
        this.name = name;
        this.color = categoryColor;
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

    public CategoryColor getColor() {
        return color;
    }

    public void setColor(CategoryColor color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryIcon getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(CategoryIcon categoryIcon) {
        this.categoryIcon = categoryIcon;
    }
}
