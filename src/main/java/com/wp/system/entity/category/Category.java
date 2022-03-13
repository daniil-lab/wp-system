package com.wp.system.entity.category;

import com.wp.system.entity.bill.BillLog;
import com.wp.system.entity.image.SystemImage;
import com.wp.system.entity.user.User;
import com.wp.system.utils.CategoryColor;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Category {
    @Id
    private java.util.UUID id = java.util.UUID.randomUUID();

    private String name;

    private CategoryColor color;

    private String description;

    private int categoryLimit;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="icon_id")
    private SystemImage icon;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "category")
    private Set<BillLog> billLogs;

    public Category() {};

    public Category(String name, CategoryColor categoryColor, User user, SystemImage icon) {
        this.name = name;
        this.color = categoryColor;
        this.user = user;
        this.icon = icon;
    }

    public Category(String name, CategoryColor categoryColor, String description, User user, SystemImage icon) {
        this.name = name;
        this.color = categoryColor;
        this.description = description;
        this.user = user;
        this.icon = icon;
    }

    public SystemImage getIcon() {
        return icon;
    }

    public void setIcon(SystemImage icon) {
        this.icon = icon;
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

    public java.util.UUID getId() {
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
}
