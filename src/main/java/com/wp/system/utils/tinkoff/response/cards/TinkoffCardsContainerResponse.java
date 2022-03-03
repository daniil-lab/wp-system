package com.wp.system.utils.tinkoff.response.cards;

import java.util.List;

public class TinkoffCardsContainerResponse {
    private List<TinkoffCardsResponse> cardNumbers;

    public TinkoffCardsContainerResponse() {}

    public List<TinkoffCardsResponse> getCardNumbers() {
        return cardNumbers;
    }

    public void setCardNumbers(List<TinkoffCardsResponse> cardNumbers) {
        this.cardNumbers = cardNumbers;
    }
}
