package com.wp.system.dto.tochka;

import com.wp.system.dto.sber.SberCardBalanceDTO;
import com.wp.system.entity.BankList;
import com.wp.system.entity.sber.SberCard;
import com.wp.system.entity.tochka.TochkaCard;

import java.util.UUID;

public class TochkaCardDTO {
    private UUID id;

    private String cardNumber;

    private TochkaCardBalanceDTO balance;

    private BankList bankName;

    public TochkaCardDTO() {}

    public TochkaCardDTO(TochkaCard c) {
        if(c == null)
            return;

        this.id = c.getId();
        this.balance = new TochkaCardBalanceDTO(c.getBalance());
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TochkaCardBalanceDTO getBalance() {
        return balance;
    }

    public void setBalance(TochkaCardBalanceDTO balance) {
        this.balance = balance;
    }

    public BankList getBankName() {
        return bankName;
    }

    public void setBankName(BankList bankName) {
        this.bankName = bankName;
    }
}
