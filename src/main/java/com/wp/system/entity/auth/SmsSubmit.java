package com.wp.system.entity.auth;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

@Entity
public class SmsSubmit {
    @Id
    private UUID id = UUID.randomUUID();

    public int code;

    public String phone;

    private String createAt;

    private boolean submited = false;

    public SmsSubmit() {}

    public SmsSubmit(int code, String phone) {
        this.code = code;
        this.phone = phone;
        this.createAt = Instant.now().toString();
    }

    public boolean isSubmited() {
        return submited;
    }

    public void setSubmited(boolean submited) {
        this.submited = submited;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
