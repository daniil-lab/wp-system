package com.wp.system.dto.bill;

import com.wp.system.dto.user.UserDTO;
import com.wp.system.entity.bill.Bill;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public class BillDTO {

    @Schema(description = "ID счета")
    private UUID id;

    @Schema(description = "Название счета")
    private String name;

    @Schema(description = "Пользователь, к которому прикреплен счет")
    private UserDTO user;

    @Schema(description = "Баланс счета")
    private BillBalanceDTO balance;

    public BillDTO() {}

    public BillDTO(Bill bill) {
        this.id = bill.getId();
        this.name = bill.getName();
        this.user = new UserDTO(bill.getUser());
        this.balance = new BillBalanceDTO(bill.getBalance());
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public BillBalanceDTO getBalance() {
        return balance;
    }

    public void setBalance(BillBalanceDTO balance) {
        this.balance = balance;
    }
}
