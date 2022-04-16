package com.wp.system.dto.subscription;

import com.wp.system.entity.subscription.SubscriptionVariant;
import com.wp.system.entity.user.UserRole;

import java.util.UUID;

public class SubscriptionVariantRoleDTO {
    private UUID id;

    private String name;

    public SubscriptionVariantRoleDTO() {}

    public SubscriptionVariantRoleDTO(UserRole role) {
        if(role == null)
            return;

        this.id = role.getId();
        this.name = role.getName();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
