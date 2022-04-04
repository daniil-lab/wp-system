package com.wp.system.request.sber;

import java.util.UUID;

public class UpdateSberTransactionRequest {
    private UUID categoryId;

    public UpdateSberTransactionRequest() {}

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
}
