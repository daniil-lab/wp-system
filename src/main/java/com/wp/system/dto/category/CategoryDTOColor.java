package com.wp.system.dto.category;

import com.wp.system.other.CategoryColor;

public class CategoryDTOColor {
    private String name;

    private String hex;

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
