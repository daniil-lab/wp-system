package com.wp.system.request.user;

public class FindUsersRequest {
    private String phone;

    public FindUsersRequest() {}

    public FindUsersRequest(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
