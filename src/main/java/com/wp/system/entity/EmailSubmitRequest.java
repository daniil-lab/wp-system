package com.wp.system.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Entity
public class EmailSubmitRequest {
    @Id
    private UUID id = UUID.randomUUID();

    private UUID userId;

    private String url;

    public EmailSubmitRequest() {}

    public EmailSubmitRequest(UUID userId) {
        this.userId = userId;
        this.url = new String(Base64.getEncoder().encode((id.toString() + userId.toString()).getBytes(StandardCharsets.UTF_8)));
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
