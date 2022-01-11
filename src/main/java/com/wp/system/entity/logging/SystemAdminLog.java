package com.wp.system.entity.logging;

import com.wp.system.entity.admin.Admin;
import com.wp.system.entity.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class SystemAdminLog {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="admin_id")
    private Admin admin;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant date;

    public SystemAdminLog() {}

    public SystemAdminLog(String name, String description, Admin admin, Instant date) {
        this.name = name;
        this.description = description;
        this.admin = admin;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Admin getUser() {
        return admin;
    }

    public void setUser(Admin user) {
        this.admin = user;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
}
