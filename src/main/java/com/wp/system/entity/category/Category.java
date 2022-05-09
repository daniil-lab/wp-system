package com.wp.system.entity.category;

import com.wp.system.entity.bill.BillLog;
import com.wp.system.entity.image.SystemImage;
import com.wp.system.entity.user.User;
import com.wp.system.utils.CategoryColor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
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

    private int categoryLimit;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant resetDataDate;

    private Boolean forEarn;

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

    private Double categorySpend = 0.0;

    private Double categoryEarn = 0.0;

    private Double percentsFromLimit = 0.0;

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

    public Double getCategorySpend() {
        return categorySpend;
    }

    public void setCategorySpend(Double categorySpend) {
        this.categorySpend = categorySpend;
    }

    public Double getCategoryEarn() {
        return categoryEarn;
    }

    public void setCategoryEarn(Double categoryEarn) {
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

    public Double getPercentsFromLimit() {
        return percentsFromLimit;
    }

    public void setPercentsFromLimit(Double percentsFromLimit) {
        this.percentsFromLimit = percentsFromLimit;
    }

    public SystemImage getIcon() {
        return icon;
    }

    public void setIcon(SystemImage icon) {
        this.icon = icon;
    }

    public int getCategoryLimit() {
        return categoryLimit;
    }

    public void setCategoryLimit(int categoryLimit) {
        this.categoryLimit = categoryLimit;
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
