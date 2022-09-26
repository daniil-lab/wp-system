package com.wp.system.entity.bill;

import com.wp.system.entity.user.User;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Bill {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    private BigDecimal balance = new BigDecimal("0");

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name="user_id")
    private User user;

    private Boolean hidden = false;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "bill")
    @Fetch(FetchMode.SUBSELECT)
    private List<BillTransaction> transactions = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "bill")
    @Fetch(FetchMode.SUBSELECT)
    private List<BillLog> logs = new ArrayList<>();

    public Bill() {};

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Bill(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<BillLog> getLogs() {
        return logs;
    }

    public void setLogs(List<BillLog> logs) {
        this.logs = logs;
    }

    public List<BillTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<BillTransaction> transactions) {
        this.transactions = transactions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
