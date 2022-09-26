package com.wp.system.entity.transaction;

import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.user.User;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Transaction {
    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private Bill bill;

    private int sum;

    private int cents;

    private String createAt;

    private String editAt;

    public Transaction() {};

    public Transaction(Category category, User user, Bill bill, int sum, int cents) {
        this.category = category;
        this.user = user;
        this.bill = bill;
        this.sum = sum;
        this.cents = cents;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public UUID getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getCents() {
        return cents;
    }

    public void setCents(int cents) {
        this.cents = cents;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getEditAt() {
        return editAt;
    }

    public void setEditAt(String editAt) {
        this.editAt = editAt;
    }
}
