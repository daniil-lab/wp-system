package com.wp.system.request.advertising;

import java.util.UUID;

public class RemoveFileFromAdvertisingRequest {
    private UUID id;

    public RemoveFileFromAdvertisingRequest() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
