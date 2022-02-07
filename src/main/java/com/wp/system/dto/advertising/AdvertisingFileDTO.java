package com.wp.system.dto.advertising;

import com.wp.system.entity.advertising.AdvertisingFile;

import java.util.UUID;

public class AdvertisingFileDTO {
    private UUID id;

    private String path;

    public AdvertisingFileDTO() {}

    public AdvertisingFileDTO(AdvertisingFile af) {
        if(af == null)
            return;

        this.id = af.getId();
        this.path = af.getPath();
    }

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
