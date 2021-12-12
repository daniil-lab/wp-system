package com.wp.system.entity.auth;

import com.wp.system.entity.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class PhoneAuthData {
    @Id
    private UUID id = UUID.randomUUID();

    private String phone;

    private int code;

    private String createAt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;

    public PhoneAuthData() {}

    public PhoneAuthData(String phone, int code, User user) {
        this.phone = phone;
        this.code = code;
        this.user = user;
        this.createAt = Instant.now().toString();
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
