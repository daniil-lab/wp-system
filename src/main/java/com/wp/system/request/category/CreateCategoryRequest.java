package com.wp.system.request.category;

import com.wp.system.other.ValidationErrorMessages;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateCategoryRequest {
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String name;

    private String description;

    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String hexColor;

    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private UUID userId;

    public CreateCategoryRequest() {}

    public CreateCategoryRequest(String name, String description, String hexColor, UUID userId) {
        this.name = name;
        this.description = description;
        this.hexColor = hexColor;
        this.userId = userId;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
