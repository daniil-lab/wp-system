package com.wp.system.entity.user;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Embeddable
public class UserEmail {
    private String address;

    private boolean activated;

    public UserEmail() {}

    public UserEmail(String address, boolean activated) {
        this.address = address;
        this.activated = activated;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
