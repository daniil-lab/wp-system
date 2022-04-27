package com.wp.system.dto.loyalty;

import com.wp.system.entity.loyalty.LoyaltyCardImage;

import java.util.UUID;

public class LoyaltyCardCustomImageDTO {
    private UUID id;

    private String path;

    public LoyaltyCardCustomImageDTO(LoyaltyCardImage lci) {
        this.id = lci.getId();
        this.path = lci.getPath();
    }

    public LoyaltyCardCustomImageDTO() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
