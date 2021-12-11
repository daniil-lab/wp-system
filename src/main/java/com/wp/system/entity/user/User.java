package com.wp.system.entity.user;

import com.wp.system.other.WalletType;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "_user")
public class User {
    @Id
    private UUID id = UUID.randomUUID();

    private String username;

    private String password;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "role_id")
    private UserRole role;

    private WalletType wallet;

    public User() {};

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public WalletType getWallet() {
        return wallet;
    }

    public void setWallet(WalletType wallet) {
        this.wallet = wallet;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
