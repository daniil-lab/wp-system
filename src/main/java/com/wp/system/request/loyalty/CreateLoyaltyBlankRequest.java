package com.wp.system.request.loyalty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateLoyaltyBlankRequest {

    @NotNull
    private String name;

    private String description;

    private UUID imageId;

    public CreateLoyaltyBlankRequest() {}

    public CreateLoyaltyBlankRequest(String name, String description, UUID imageId) {
        this.name = name;
        this.description = description;
        this.imageId = imageId;
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

    public UUID getImageId() {
        return imageId;
    }

    public void setImageId(UUID imageId) {
        this.imageId = imageId;
    }
}
