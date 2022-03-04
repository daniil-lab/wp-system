package com.wp.system.utils.tinkoff.response.operations;

import com.wp.system.utils.tinkoff.response.TinkoffAmountResponse;

public class TinkoffOperationResponse {
    private String id;

    private String status;

    private String description;

    private TinkoffAmountResponse amount;

    private TinkoffOperationSubgroupResponse subgroup;

    private TinkoffOperationTimeResponse operationTime;

    private String card;

    public TinkoffOperationResponse() {}

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public TinkoffOperationTimeResponse getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(TinkoffOperationTimeResponse operationTime) {
        this.operationTime = operationTime;
    }

    public TinkoffOperationSubgroupResponse getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(TinkoffOperationSubgroupResponse subgroup) {
        this.subgroup = subgroup;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TinkoffAmountResponse getAmount() {
        return amount;
    }

    public void setAmount(TinkoffAmountResponse amount) {
        this.amount = amount;
    }
}
