package com.wp.system.request.tinkoff;

import java.util.UUID;

public class UpdateTinkoffTransactionRequest {
    private UUID categoryId;

    public UpdateTinkoffTransactionRequest() {}

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
}
