package com.wp.system.dto.tochka;

import com.wp.system.entity.BankList;
import com.wp.system.entity.tochka.TochkaCard;

import java.math.BigDecimal;
import java.util.UUID;

public class TochkaCardDTO {
    private UUID id;

    private String cardNumber;

    private BigDecimal balance;

    private BankList bankName;

    public TochkaCardDTO() {}

    public TochkaCardDTO(TochkaCard c) {
        if(c == null)
            return;

        this.id = c.getId();
        this.balance = c.getBalance();
        this.cardNumber = c.getCardNumber();
        this.bankName = BankList.TOCHKA;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BankList getBankName() {
        return bankName;
    }

    public void setBankName(BankList bankName) {
        this.bankName = bankName;
    }
}
