package com.wp.system.request.tochka;

import java.util.UUID;

public class UpdateTochkaTransactionRequest {
    private UUID categoryId;

    public UpdateTochkaTransactionRequest() {}

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
}
