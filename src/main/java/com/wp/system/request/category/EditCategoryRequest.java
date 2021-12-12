package com.wp.system.request.category;

import com.wp.system.other.ValidationErrorMessages;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

public class EditCategoryRequest {
    private String name;

    private String description;

    @Pattern(regexp = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$", message = ValidationErrorMessages.INVALID_HEX_CODE)
    private String hexColor;

    private String categoryIcon;

    @PositiveOrZero(message = ValidationErrorMessages.INVALID_CATEGORY_LIMIT)
    private int categoryLimit;

    public EditCategoryRequest() {}

    public EditCategoryRequest(String name, String description, String hexColor, String categoryIcon) {
        this.name = name;
        this.description = description;
        this.hexColor = hexColor;
        this.categoryIcon = categoryIcon;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public int getCategoryLimit() {
        return categoryLimit;
    }

    public void setCategoryLimit(int categoryLimit) {
        this.categoryLimit = categoryLimit;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
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
