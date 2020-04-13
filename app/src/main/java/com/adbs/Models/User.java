package com.adbs.Models;

public class User {
    private String email,name,phone,password;
    private int points;
    private final int INITIAL_POINTS = 0;

    public int getINITIAL_POINTS() {
        return INITIAL_POINTS;
    }

    public User() {
    }

    public User(String email, String name, String phone, String password, int points) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
