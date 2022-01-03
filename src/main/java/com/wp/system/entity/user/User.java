package com.wp.system.entity.user;

import com.wp.system.entity.auth.PhoneAuthData;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.loyalty.LoyaltyCard;
import com.wp.system.other.WalletType;
import com.wp.system.other.user.UserType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "_user")
public class User {
    @Id
    private UUID id = UUID.randomUUID();

    private String username;

    private String password;

    private String email;

    private UserType userType;

    private boolean notificationsEnable = true;

    private int plannedIncome;

    private boolean touchId;

    private boolean faceId;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LoyaltyCard> cards;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "role_id")
    private UserRole role;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private List<PhoneAuthData> phoneAuthRequests;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    @Fetch(FetchMode.SUBSELECT)
    private List<Category> categories;

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
//    @Fetch(FetchMode.SUBSELECT)
//    private List<Bill> bills;

    @ElementCollection
    private List<String> deviceTokens = new ArrayList<>();

    private WalletType wallet;

    private String pinCode;

    public User() {};

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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
