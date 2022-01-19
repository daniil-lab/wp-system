package com.wp.system.entity.category;

import com.wp.system.entity.image.SystemImage;
import com.wp.system.other.CategoryColor;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class BaseCategory {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    private CategoryColor color;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="icon_id")
    private SystemImage icon;

    public BaseCategory() {}

    public BaseCategory(String name, CategoryColor color, String description) {
        this.name = name;
        this.color = color;
        this.description = description;
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

    public CategoryColor getColor() {
        return color;
    }

    public void setColor(CategoryColor color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SystemImage getIcon() {
        return icon;
    }

    public void setIcon(SystemImage icon) {
        this.icon = icon;
    }
}
