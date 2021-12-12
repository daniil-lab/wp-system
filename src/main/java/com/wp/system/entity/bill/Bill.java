package com.wp.system.entity.bill;

import com.wp.system.entity.user.User;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Bill {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    private double balance = 0;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;

    public Bill() {};

    public Bill(String name, User user) {
        this.name = name;
        this.user = user;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public float getCheck() {
//        return check;
//    }
//
//    public void setCheck(float check) {
//        this.check = check;
//    }
}
