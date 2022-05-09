package com.wp.system.request.category;

import com.wp.system.utils.CategoryColor;
import com.wp.system.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateBaseCategoryRequest {
    @Schema(required = true, description = "Название категории")
    @Length(min = 1, max = 64, message = ValidationErrorMessages.INVALID_CATEGORY_NAME)
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String name;

    @Schema(required = false, description = "Описание категории")
    private String description;

    @Schema(required = false, description = "Иконка категории")
//    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private UUID icon;

    @Schema(required = true, description = "Цвет категории")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private CategoryColor color;

    @Schema(required = true, description = "Указатель, что категория может содержать пополнения")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private Boolean forEarn;

    @Schema(required = true, description = "Указатель, что категория может содержать расходы")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private Boolean forSpend;

    public CreateBaseCategoryRequest() {}

    public CreateBaseCategoryRequest(String name, String description, UUID UUID, CategoryColor categoryColor) {
        this.name = name;
        this.color = categoryColor;
        this.description = description;
        this.icon = UUID;
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

}
