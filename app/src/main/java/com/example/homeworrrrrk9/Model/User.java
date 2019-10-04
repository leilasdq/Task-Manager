package com.example.homeworrrrrk9.Model;

import java.util.UUID;

public class User {
    private UUID id;
    private String username;
    private String password;

    public User() {
        id = UUID.randomUUID();
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
