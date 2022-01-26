package com.wp.system.entity.email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@Entity
public class EmailSubmitRequest {
    @Id
    private UUID id = UUID.randomUUID();

    private UUID userId;

    private int code;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant createAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant expiration;

    public EmailSubmitRequest() {}

    public EmailSubmitRequest(UUID userId) {
        Random rand = new Random();

        StringBuilder codeString = new StringBuilder();
        for (int j = 0; j < 4; j++) {
            codeString.append(rand.nextInt(8) + 1);
        }

        this.userId = userId;
        this.code = Integer.parseInt(codeString.toString());
        this.createAt = Instant.now();
        this.expiration = Instant.now().plus(1, ChronoUnit.DAYS);
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
