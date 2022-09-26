package com.wp.system.entity.bill;

import com.wp.system.entity.category.Category;
import com.wp.system.utils.bill.TransactionType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class BillLog {
    @Id
    private UUID id = UUID.randomUUID();

    private TransactionType action;

    private BigDecimal sum;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="bill_id")
    private Bill bill;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="category_id")
    private Category category;

    public BillLog() {};

    public BillLog(TransactionType action, BigDecimal amount, Bill bill, Category category) {
        this.action = action;
        this.sum = amount;
        this.bill = bill;
        this.category = category;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public UUID getId() {
        return id;
    }

    public TransactionType getAction() {
        return action;
    }

    public void setAction(TransactionType action) {
        this.action = action;
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
