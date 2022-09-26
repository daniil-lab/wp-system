package com.wp.system.entity.logging;

import com.wp.system.entity.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class SystemAdminLog {
    @Id
    private UUID id = UUID.randomUUID();

    private String action;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="admin_id")
    private User admin;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant date;

    public SystemAdminLog() {}

    public SystemAdminLog(String action, String description, User admin) {
        this.action = action;
        this.description = description;
        this.admin = admin;
        this.date = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return admin;
    }

    public void setUser(User user) {
        this.admin = user;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
}
