package com.yoanan.unka.model.service;

public class ProfileInformationServiceModel extends BaseServiceModel {

    private UserServiceModel user;
    private String imgUrl;
    private String phoneNumber;
    private String email;
    private String profession;
    private Integer yearsExperience;
    private String presentation;

    public ProfileInformationServiceModel() {
    }

    public UserServiceModel getUser() {
        return user;
    }

    public ProfileInformationServiceModel setUser(UserServiceModel user) {
        this.user = user;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public ProfileInformationServiceModel setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ProfileInformationServiceModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ProfileInformationServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getProfession() {
        return profession;
    }

    public ProfileInformationServiceModel setProfession(String profession) {
        this.profession = profession;
        return this;
    }

    public Integer getYearsExperience() {
        return yearsExperience;
    }

    public ProfileInformationServiceModel setYearsExperience(Integer yearsExperience) {
        this.yearsExperience = yearsExperience;
        return this;
    }

    public String getPresentation() {
        return presentation;
    }

    public ProfileInformationServiceModel setPresentation(String presentation) {
        this.presentation = presentation;
        return this;
    }
}
