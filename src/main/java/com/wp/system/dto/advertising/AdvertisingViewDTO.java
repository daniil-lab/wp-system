package com.wp.system.dto.advertising;

import com.wp.system.entity.advertising.AdvertisingView;

import java.time.Instant;
import java.util.UUID;

public class AdvertisingViewDTO {
    private UUID id;

    private Instant createAt;

    public AdvertisingViewDTO() {}

    public AdvertisingViewDTO(AdvertisingView view) {
        if(view == null)
            return;

        this.id = view.getId();
        this.createAt = view.getCreateAt();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }
}
