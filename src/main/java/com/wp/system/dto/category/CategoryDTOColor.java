package com.wp.system.dto.category;

import com.wp.system.utils.CategoryColor;
import io.swagger.v3.oas.annotations.media.Schema;

public class CategoryDTOColor {
    @Schema(description = "Название цвета")
    private String name;

    @Schema(description = "HEX цвета")
    private String hex;

    @Schema(description = "Системное название цвета")
    private String systemName;

    public CategoryDTOColor() {};

    public CategoryDTOColor(CategoryColor categoryColor) {
        this.name = categoryColor.getColorName();
        this.hex = categoryColor.getColorHex();
        this.systemName = categoryColor.name();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
