package com.wp.system.dto.category;

import com.wp.system.dto.image.SystemImageDTO;
import com.wp.system.dto.user.UserDTO;
import com.wp.system.entity.category.BaseCategory;
import com.wp.system.entity.category.Category;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public class BaseCategoryDTO {
    @Schema(description = "ID категории")
    private UUID id;

    @Schema(description = "Название категории")
    private String name;

    @Schema(description = "Цвет категории")
    private CategoryDTOColor color;

    @Schema(description = "Иконка категории")
    private SystemImageDTO icon;

    @Schema(description = "Описание категории")
    private String description;

    public BaseCategoryDTO(BaseCategory category) {
        if(category == null)
            return;

        this.id = category.getId();
        this.name = category.getName();
        this.color = new CategoryDTOColor(category.getColor());
        this.icon = category.getIcon() != null ? new SystemImageDTO(category.getIcon()) : null;
        this.description = category.getDescription();
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

    public CategoryDTOColor getColor() {
        return color;
    }

    public void setColor(CategoryDTOColor color) {
        this.color = color;
    }

    public SystemImageDTO getIcon() {
        return icon;
    }

    public void setIcon(SystemImageDTO icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
