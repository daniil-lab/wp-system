package com.wp.system.request.category;

import com.wp.system.other.ValidationErrorMessages;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class EditCategoryRequest {
    private String name;

    private String description;

    private String hexColor;

    public EditCategoryRequest() {}

    public EditCategoryRequest(String name, String description, String hexColor) {
        this.name = name;
        this.description = description;
        this.hexColor = hexColor;
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
