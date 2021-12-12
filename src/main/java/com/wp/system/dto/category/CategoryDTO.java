package com.wp.system.dto.category;

import com.wp.system.dto.user.UserDTO;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.user.User;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

public class CategoryDTO {
    private UUID id;

    private String name;

    private String hexColor;

    private String description;

    private int categoryLimit;

    private UserDTO user;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.hexColor = category.getHexColor();
        this.description = category.getDescription();
        this.user = new UserDTO(category.getUser());
        this.categoryLimit = category.getCategoryLimit();
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public int getCategoryLimit() {
        return categoryLimit;
    }

    public void setCategoryLimit(int categoryLimit) {
        this.categoryLimit = categoryLimit;
    }
}
