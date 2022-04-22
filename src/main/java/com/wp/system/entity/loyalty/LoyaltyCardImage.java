package com.wp.system.entity.loyalty;

import com.wp.system.entity.category.Category;
import com.wp.system.utils.SystemImageTag;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class LoyaltyCardImage {
    @Id
    private UUID id = UUID.randomUUID();

    private String path;

    private String contentType;

    public LoyaltyCardImage() {};

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public UUID getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
