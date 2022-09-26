package com.wp.system.entity.category;

import com.wp.system.entity.bill.BillLog;
import com.wp.system.entity.image.SystemImage;
import com.wp.system.entity.user.User;
import com.wp.system.utils.CategoryColor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
public class Category {
    @Id
    private java.util.UUID id = java.util.UUID.randomUUID();

    private String name;

    private CategoryColor color;

    private String description;

    private BigDecimal categoryLimit = BigDecimal.ZERO;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant resetDataDate;

    private Boolean forEarn;

    private BigDecimal spendStatistic = BigDecimal.ZERO;

    private BigDecimal earnStatistic = BigDecimal.ZERO;

    private Boolean favorite = false;

    private Boolean forSpend;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="icon_id")
    private SystemImage icon;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "category")
    @Fetch(FetchMode.SUBSELECT)
    private Set<BillLog> billLogs;

    private BigDecimal categorySpend = BigDecimal.ZERO;

    private BigDecimal categoryEarn = BigDecimal.ZERO;

    private BigDecimal percentsFromLimit = BigDecimal.ZERO;

    public Category() {};

    public Category(String name, CategoryColor categoryColor, User user, SystemImage icon) {
        this.name = name;
        this.color = categoryColor;
        this.user = user;
        this.icon = icon;
    }

    public Category(String name, CategoryColor categoryColor, String description, User user, SystemImage icon) {
        this.name = name;
        this.color = categoryColor;
        this.description = description;
        this.user = user;
        this.icon = icon;
    }

    public BigDecimal getEarnStatistic() {
        return earnStatistic;
    }

    public void setEarnStatistic(BigDecimal earnStatistic) {
        this.earnStatistic = earnStatistic;
    }

    public BigDecimal getSpendStatistic() {
        return spendStatistic;
    }

    public void setSpendStatistic(BigDecimal spendStatistic) {
        this.spendStatistic = spendStatistic;
    }

    public Instant getResetDataDate() {
        return resetDataDate;
    }

    public void setResetDataDate(Instant resetDataDate) {
        this.resetDataDate = resetDataDate;
    }

    public Set<BillLog> getBillLogs() {
        return billLogs;
    }

    public void setBillLogs(Set<BillLog> billLogs) {
        this.billLogs = billLogs;
    }

    public BigDecimal getCategorySpend() {
        return categorySpend;
    }

    public void setCategorySpend(BigDecimal categorySpend) {
        this.categorySpend = categorySpend;
    }

    public BigDecimal getCategoryEarn() {
        return categoryEarn;
    }

    public void setCategoryEarn(BigDecimal categoryEarn) {
        this.categoryEarn = categoryEarn;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getForSpend() {
        return forSpend;
    }

    public void setForSpend(Boolean forSpend) {
        this.forSpend = forSpend;
    }

    public Boolean getForEarn() {
        return forEarn;
    }

    public void setForEarn(Boolean forEarn) {
        this.forEarn = forEarn;
    }

    public BigDecimal getPercentsFromLimit() {
        return percentsFromLimit;
    }

    public void setPercentsFromLimit(BigDecimal percentsFromLimit) {
        this.percentsFromLimit = percentsFromLimit;
    }

    public SystemImage getIcon() {
        return icon;
    }

    public void setIcon(SystemImage icon) {
        this.icon = icon;
    }

    public BigDecimal getCategoryLimit() {
        return categoryLimit;
    }

    public void setCategoryLimit(BigDecimal categoryLimit) {
        this.categoryLimit = categoryLimit;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public java.util.UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryColor getColor() {
        return color;
    }

    public void setColor(CategoryColor color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
