package com.wp.system.entity.subscription;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class SubscriptionVariantGroup {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    private Set<SubscriptionVariant> variants = new HashSet<>();

    public SubscriptionVariantGroup() {}

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

    public Set<SubscriptionVariant> getVariants() {
        return variants;
    }

    public void setVariants(Set<SubscriptionVariant> variants) {
        this.variants = variants;
    }
}
