package com.wp.system.request.category;

import com.wp.system.other.CategoryColor;
import com.wp.system.other.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateCategoryRequest {
    @Schema(required = true, description = "Название категории")
    @Length(min = 4, max = 64, message = ValidationErrorMessages.INVALID_CATEGORY_NAME)
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String name;

    @Schema(required = false, description = "Описание категории")
    private String description;

    @Schema(required = true, description = "Иконка категории")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private UUID icon;

    @Schema(required = true, description = "Цвет категории")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private CategoryColor color;

    @Schema(required = true, description = "Пользователь, к которому будет относится категория")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private UUID userId;

    public CreateCategoryRequest() {}

    public CreateCategoryRequest(String name, String description, UUID UUID, CategoryColor categoryColor, java.util.UUID userId) {
        this.name = name;
        this.color = categoryColor;
        this.description = description;
        this.icon = UUID;
        this.userId = userId;
    }

    public UUID getIcon() {
        return icon;
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

    public java.util.UUID getUserId() {
        return userId;
    }

    public void setUserId(java.util.UUID userId) {
        this.userId = userId;
    }
}
