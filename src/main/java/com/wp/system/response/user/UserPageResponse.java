package com.wp.system.response.user;

import com.wp.system.dto.user.UserDTO;

import java.util.List;

public class UserPageResponse {
    private List<UserDTO> users;

    private int total;

    public UserPageResponse() {}

    public UserPageResponse(List<UserDTO> users, int total) {
        this.users = users;
        this.total = total;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
