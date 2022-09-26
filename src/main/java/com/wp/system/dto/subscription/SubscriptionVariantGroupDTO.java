package com.wp.system.dto.subscription;

import com.wp.system.entity.subscription.SubscriptionVariant;
import com.wp.system.entity.subscription.SubscriptionVariantGroup;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SubscriptionVariantGroupDTO {
    private UUID id;

    private String name;

    private List<SubscriptionVariantDTO> variants;

    public SubscriptionVariantGroupDTO() {}

    public SubscriptionVariantGroupDTO(SubscriptionVariantGroup group) {
        if(group == null)
            return;

        this.id = group.getId();
        this.name = group.getName();
        this.variants = group.getVariants().stream().map(SubscriptionVariantDTO::new).collect(Collectors.toList());
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

    public List<SubscriptionVariantDTO> getVariants() {
        return variants;
    }

    public void setVariants(List<SubscriptionVariantDTO> variants) {
        this.variants = variants;
    }
}
