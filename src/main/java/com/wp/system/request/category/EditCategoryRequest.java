package com.wp.system.request.category;

import com.wp.system.utils.CategoryColor;
import com.wp.system.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

public class EditCategoryRequest {
    @Schema(required = false, description = "Название категории")
    @Length(min = 4, max = 64, message = ValidationErrorMessages.INVALID_CATEGORY_NAME)
    private String name;

    @Schema(required = false, description = "Описание категории")
    private String description;

    @Schema(required = false, description = "Иконка категории")
    private UUID icon;

    @Schema(required = false, description = "Цвет категории")
    private CategoryColor color;

    @Schema(required = false, description = "Лимит категории")
    @PositiveOrZero(message = ValidationErrorMessages.INVALID_CATEGORY_LIMIT)
    private int categoryLimit;

    public EditCategoryRequest() {}

    public EditCategoryRequest(String name, String description, CategoryColor color, UUID UUID) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.icon = UUID;
    }

    public UUID getIcon() {
        return icon;
    }

    public Integer getCategoryLimit() {
        return categoryLimit;
    }

    public void setCategoryLimit(int categoryLimit) {
        this.categoryLimit = categoryLimit;
    }

    public void setIcon(UUID icon) {
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
