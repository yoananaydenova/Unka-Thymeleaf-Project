package com.yoanan.unka.model.service;


public class UserRegisterServiceModel {

    private String username;
    private String fullName;
    private String password;


    public UserRegisterServiceModel() {
    }


    public String getUsername() {
        return username;
    }

    public UserRegisterServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserRegisterServiceModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

}
