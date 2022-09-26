package com.wp.system.dto.loyalty;

import com.wp.system.dto.image.SystemImageDTO;
import com.wp.system.entity.loyalty.LoyaltyBlank;

import java.util.UUID;

public class LoyaltyBlankDTO {
    private UUID id;

    private String name;

    private String description;

    private SystemImageDTO image;

    public LoyaltyBlankDTO() {}

    public LoyaltyBlankDTO(LoyaltyBlank blank) {
        this.id = blank.getId();
        this.name = blank.getName();
        this.description = blank.getDescription();
        this.image = blank.getImage() == null ? null : new SystemImageDTO(blank.getImage());
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SystemImageDTO getImage() {
        return image;
    }

    public void setImage(SystemImageDTO image) {
        this.image = image;
    }
}
