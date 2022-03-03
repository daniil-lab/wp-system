package com.wp.system.dto.image;

import com.wp.system.entity.image.SystemImage;
import com.wp.system.utils.SystemImageTag;

import java.util.UUID;

public class SystemImageDTO {
    private UUID id;

    private String name;

    private String path;

    private SystemImageTag tag;

    public SystemImageDTO() {}

    public SystemImageDTO(SystemImage image) {
        this.id = image.getId();
        this.name = image.getName();
        this.path = image.getPath();
        this.tag = image.getTag();
    }

    public SystemImageTag getTag() {
        return tag;
    }

    public void setTag(SystemImageTag tag) {
        this.tag = tag;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
