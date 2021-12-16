package com.wp.system.request.category;

import com.wp.system.other.CategoryColor;
import com.wp.system.other.CategoryIcon;
import com.wp.system.other.ValidationErrorMessages;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateCategoryRequest {
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String name;

    private String description;

    private CategoryIcon icon;

    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private CategoryColor color;

    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private UUID userId;

    public CreateCategoryRequest() {}

    public CreateCategoryRequest(String name, String description, CategoryIcon categoryIcon, CategoryColor categoryColor, UUID userId) {
        this.name = name;
        this.color = categoryColor;
        this.description = description;
        this.icon = categoryIcon;
        this.userId = userId;
    }

    public CategoryIcon getIcon() {
        return icon;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
