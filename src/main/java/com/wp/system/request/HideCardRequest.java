package com.wp.system.request;

import java.util.UUID;

public class HideCardRequest {
    private UUID id;

    private Boolean hidden;

    public HideCardRequest() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}
