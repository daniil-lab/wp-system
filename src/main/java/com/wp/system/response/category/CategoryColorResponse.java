package com.wp.system.response.category;

import com.wp.system.utils.CategoryColor;

public class CategoryColorResponse {

    private String systemName;

    private String hex;

    private String name;

    public CategoryColorResponse() {}

    public CategoryColorResponse(CategoryColor categoryColor) {
        this.systemName = categoryColor.name();
        this.hex = categoryColor.getColorHex();
        this.name = categoryColor.getColorName();
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
