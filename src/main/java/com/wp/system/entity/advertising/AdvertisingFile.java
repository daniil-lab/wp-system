package com.wp.system.entity.advertising;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class AdvertisingFile {
    @Id
    private UUID id = UUID.randomUUID();

    private String path;

    public AdvertisingFile() {}

    public AdvertisingFile(String path) {
        this.path = path;
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
