package com.wp.system.entity.bill;

import com.wp.system.entity.category.Category;
import com.wp.system.entity.user.User;
import com.wp.system.utils.WalletType;
import com.wp.system.utils.bill.TransactionType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
public class BillTransaction {
    @Id
    private UUID id = UUID.randomUUID();

    private TransactionType action;

    private BigDecimal sum;

    private String description;

    private WalletType currency;

    private String geocodedPlace;

    private Double longitude;

    private Double latitude;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="bill_id")
    private Bill bill;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant createAt = Instant.now();

    public BillTransaction() {};

    public BillTransaction(TransactionType action, BigDecimal amount, String description, Bill bill, Category category, WalletType currency, Instant time, User user) {
        this.action = action;
        this.sum = amount;
        this.description = description;
        this.currency = currency;
        this.bill = bill;
        this.category = category;
        this.createAt = time == null ? Instant.now() : time;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public String getGeocodedPlace() {
        return geocodedPlace;
    }

    public void setGeocodedPlace(String geocodedPlace) {
        this.geocodedPlace = geocodedPlace;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WalletType getCurrency() {
        return currency;
    }

    public void setCurrency(WalletType currency) {
        this.currency = currency;
    }
}
