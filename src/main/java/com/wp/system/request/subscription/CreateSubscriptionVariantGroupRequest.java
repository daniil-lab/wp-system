package com.wp.system.request.subscription;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public class CreateSubscriptionVariantGroupRequest {
    @NotNull
    private String name;

    @NotNull
    private List<UUID> variantIds;

    public CreateSubscriptionVariantGroupRequest() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UUID> getVariantIds() {
        return variantIds;
    }

    public void setVariantIds(List<UUID> variantIds) {
        this.variantIds = variantIds;
    }
}
