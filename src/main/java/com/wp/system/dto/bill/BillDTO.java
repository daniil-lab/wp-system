package com.wp.system.dto.bill;

import com.wp.system.dto.user.UserDTO;
import com.wp.system.entity.bill.Bill;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

public class BillDTO {

    @Schema(description = "ID счета")
    private UUID id;

    @Schema(description = "Название счета")
    private String name;

    @Schema(description = "Пользователь, к которому прикреплен счет")
    private UserDTO user;

    @Schema(description = "Баланс счета")
    private BigDecimal balance;

    private Boolean hidden;

    public BillDTO() {}

    public BillDTO(Bill bill) {
        this.id = bill.getId();
        this.name = bill.getName();
        this.user = new UserDTO(bill.getUser());
        this.balance = bill.getBalance();
        this.hidden = bill.getHidden();
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
