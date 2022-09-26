package com.wp.system.request.category;

import com.wp.system.entity.category.BaseCategory;
import com.wp.system.utils.CategoryColor;
import com.wp.system.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.UUID;

public class CreateCategoryRequest {
    @Schema(required = true, description = "Название категории")
    @Length(min = 1, max = 64, message = ValidationErrorMessages.INVALID_CATEGORY_NAME)
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String name;

    @Schema(required = false, description = "Описание категории")
    private String description;

    @Schema(required = false, description = "Иконка категории")
    private UUID icon;

    @Schema(required = true, description = "Цвет категории")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private CategoryColor color;

    @Schema(required = true, description = "Пользователь, к которому будет относится категория")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private UUID userId;

    @Schema(required = false, description = "Лимит категории")
    @PositiveOrZero(message = ValidationErrorMessages.INVALID_CATEGORY_LIMIT)
    private BigDecimal categoryLimit;

    @Schema(required = true, description = "Указатель, что категория может содержать пополнения")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private Boolean forEarn;

    @Schema(required = true, description = "Указатель, что категория может содержать расходы")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private Boolean forSpend;

    public CreateCategoryRequest() {}

    public CreateCategoryRequest(String name, String description, UUID UUID, CategoryColor categoryColor, java.util.UUID userId) {
        this.name = name;
        this.color = categoryColor;
        this.description = description;
        this.icon = UUID;
        this.userId = userId;
    }

    public CreateCategoryRequest(BaseCategory category, UUID userId) {
        this.name = category.getName();
        this.color = category.getColor();
        this.forSpend = category.getForSpend();
        this.forEarn = category.getForEarn();
        this.description = category.getDescription();
        this.icon = category.getIcon() != null ? category.getIcon().getId() : null;
        this.userId = userId;
    }

    public Boolean getForEarn() {
        return forEarn;
    }

    public void setForEarn(Boolean forEarn) {
        this.forEarn = forEarn;
    }

    public Boolean getForSpend() {
        return forSpend;
    }

    public void setForSpend(Boolean forSpend) {
        this.forSpend = forSpend;
    }

    public BigDecimal getCategoryLimit() {
        return categoryLimit;
    }

    public void setCategoryLimit(BigDecimal categoryLimit) {
        this.categoryLimit = categoryLimit;
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
