package com.baseWeb.baseWeb;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegisteredUsers {
    private List<UserData> users;

    public RegisteredUsers(List<UserData> users) {
        this.users = users;
    }

    public List<UserData> getUsers() {
        return users;
    }

    public void setUsers(List<UserData> users) {
        this.users = users;
    }
}
