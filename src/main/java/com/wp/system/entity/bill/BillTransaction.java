package com.wp.system.entity.bill;

import com.wp.system.entity.category.Category;
import com.wp.system.other.bill.BillBalanceAction;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class BillTransaction {
    @Id
    private UUID id = UUID.randomUUID();

    private BillBalanceAction action;

    private String sum;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="bill_id")
    private Bill bill;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="category_id")
    private Category category;

    public BillTransaction() {};

    public BillTransaction(BillBalanceAction action, int amount, int cents, Bill bill, Category category, String description) {
        this.action = action;
        this.sum = String.format("%d.%d", amount, cents);
        this.bill = bill;
        this.category = category;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public BillBalanceAction getAction() {
        return action;
    }

    public void setAction(BillBalanceAction action) {
        this.action = action;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
