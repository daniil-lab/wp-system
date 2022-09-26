package com.wp.system.utils.tinkoff.response.cards;

import java.util.List;

public class TinkoffCardsWrapperResponse {
    private List<TinkoffCardsContainerResponse> payload;

    public TinkoffCardsWrapperResponse() {}

    public List<TinkoffCardsContainerResponse> getPayload() {
        return payload;
    }

    public void setPayload(List<TinkoffCardsContainerResponse> payload) {
        this.payload = payload;
    }
}
