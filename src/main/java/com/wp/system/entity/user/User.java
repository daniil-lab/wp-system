package com.wp.system.entity.user;

import com.wp.system.entity.activity.Activity;
import com.wp.system.entity.auth.PhoneAuthData;
import com.wp.system.entity.bill.Bill;
import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.logging.SystemAdminLog;
import com.wp.system.entity.loyalty.LoyaltyCard;
import com.wp.system.entity.sber.SberIntegration;
import com.wp.system.entity.subscription.Subscription;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.utils.WalletType;
import com.wp.system.utils.user.UserType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "_user")
public class User {
    @Id
    private UUID id = UUID.randomUUID();

    private String username;

    private String password;

    @Embedded
    private UserEmail email;

    private UserType userType;

    private boolean notificationsEnable = true;

    // TODO: REMOVE
    private int plannedIncome;

    private int plannedEarn = 0;

    private int plannedSpend = 0;

    private boolean touchId;

    private boolean faceId;

    @OneToMany(mappedBy="user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<LoyaltyCard> cards;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "role_id")
    private UserRole role;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "admin")
    private Set<SystemAdminLog> logs;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Activity> activities;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private Set<PhoneAuthData> phoneAuthRequests;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Category> categories;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant createAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Bill> bills = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    @Fetch(FetchMode.SUBSELECT)
    private Set<BillTransaction> transactions = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    @Fetch(FetchMode.SUBSELECT)
    private Set<TinkoffIntegration> tinkoffIntegrations = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    @Fetch(FetchMode.SUBSELECT)
    private Set<SberIntegration> sberIntegrations = new HashSet<>();

    @Column(columnDefinition = "TEXT")
    private String registerCred;

    @ElementCollection
    private List<String> deviceTokens = new ArrayList<>();

    private WalletType wallet;

    private String pinCode;

    private Boolean googleLink = false;

    public User() {};

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.createAt = Instant.now();
    }

    public int getPlannedEarn() {
        return plannedEarn;
    }

    public void setPlannedEarn(int plannedEarn) {
        this.plannedEarn = plannedEarn;
    }

    public int getPlannedSpend() {
        return plannedSpend;
    }

    public void setPlannedSpend(int plannedSpend) {
        this.plannedSpend = plannedSpend;
    }

    public Boolean getGoogleLink() {
        return googleLink;
    }

    public void setGoogleLink(Boolean googleLink) {
        this.googleLink = googleLink;
    }

    public String getRegisterCred() {
        return registerCred;
    }

    public void setRegisterCred(String registerCred) {
        this.registerCred = registerCred;
    }

    public Set<Bill> getBills() {
        return bills;
    }

    public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }

    public Set<BillTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<BillTransaction> transactions) {
        this.transactions = transactions;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription userSubscription) {
        this.subscription = userSubscription;
    }

    public boolean isTouchId() {
        return touchId;
    }

    public void setTouchId(boolean touchId) {
        this.touchId = touchId;
    }

    public boolean isFaceId() {
        return faceId;
    }

    public void setFaceId(boolean faceId) {
        this.faceId = faceId;
    }

    public int getPlannedIncome() {
        return plannedIncome;
    }

    public void setPlannedIncome(int plannedIncome) {
        this.plannedIncome = plannedIncome;
    }

    public boolean isNotificationsEnable() {
        return notificationsEnable;
    }

    public void setNotificationsEnable(boolean notificationsEnable) {
        this.notificationsEnable = notificationsEnable;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<String> getDeviceTokens() {
        return deviceTokens;
    }

    public void setDeviceTokens(List<String> deviceTokens) {
        this.deviceTokens = deviceTokens;
    }

    public void addDeviceToken(String token) {
        this.deviceTokens.add(token);
    }

    public void removeDeviceToken(String token) {
        this.deviceTokens.remove(token);
    }

    public void removeDeviceToken(int idx) {
        this.deviceTokens.remove(idx);
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public WalletType getWallet() {
        return wallet;
    }

    public UserEmail getEmail() {
        return email;
    }

    public void setEmail(UserEmail email) {
        this.email = email;
    }

    public void setWallet(WalletType wallet) {
        this.wallet = wallet;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
