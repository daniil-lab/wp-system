package com.wp.system.entity.loyalty;

import com.wp.system.entity.user.User;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class LoyaltyCard {
    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="blank_id")
    private LoyaltyBlank blank;

    private String data;

    public LoyaltyCard() {}

    public LoyaltyCard(User user, LoyaltyBlank blank, String data) {
        this.user = user;
        this.blank = blank;
        this.data = data;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LoyaltyBlank getBlank() {
        return blank;
    }

    public void setBlank(LoyaltyBlank blank) {
        this.blank = blank;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
