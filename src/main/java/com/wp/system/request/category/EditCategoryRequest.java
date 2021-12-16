package com.wp.system.request.category;

import com.wp.system.other.CategoryColor;
import com.wp.system.other.CategoryIcon;
import com.wp.system.other.ValidationErrorMessages;

import javax.validation.constraints.PositiveOrZero;

public class EditCategoryRequest {
    private String name;

    private String description;

    private CategoryColor color;

    private CategoryIcon icon;

    @PositiveOrZero(message = ValidationErrorMessages.INVALID_CATEGORY_LIMIT)
    private int categoryLimit;

    public EditCategoryRequest() {}

    public EditCategoryRequest(String name, String description, CategoryColor color, CategoryIcon categoryIcon) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.icon = categoryIcon;
    }

    public CategoryIcon getIcon() {
        return icon;
    }

    public int getCategoryLimit() {
        return categoryLimit;
    }

    public void setCategoryLimit(int categoryLimit) {
        this.categoryLimit = categoryLimit;
    }

    public void setIcon(CategoryIcon icon) {
        this.icon = icon;
    }

    public CategoryColor getColor() {
        return color;
    }

    public void setColor(CategoryColor color) {
        this.color = color;
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
}
