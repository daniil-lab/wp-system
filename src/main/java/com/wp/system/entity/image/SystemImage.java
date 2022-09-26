package com.wp.system.entity.image;

import com.wp.system.entity.category.Category;
import com.wp.system.entity.loyalty.LoyaltyBlank;
import com.wp.system.utils.SystemImageTag;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class SystemImage {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    private String path;

    private String contentType;

    private SystemImageTag tag;

    @OneToMany(mappedBy="icon", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Category> categories;

    @OneToMany(mappedBy="image", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LoyaltyBlank> blanks;

    public SystemImage() {};

    public SystemImage(String name, String path, String contentType) {
        this.name = name;
        this.path = path;
        this.contentType = contentType;
    }

    public SystemImageTag getTag() {
        return tag;
    }

    public void setTag(SystemImageTag tag) {
        this.tag = tag;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
