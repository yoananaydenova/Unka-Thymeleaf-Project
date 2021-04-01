package com.yoanan.unka.model.service;

import org.springframework.web.multipart.MultipartFile;

public class ProfileInformationChangeServiceModel extends BaseServiceModel {

    private Long userId;

    private MultipartFile img;
    private String phoneNumber;
    private String email;
    private String profession;
    private Integer yearsExperience;
    private String presentation;

    public ProfileInformationChangeServiceModel() {
    }

    public Long getUserId() {
        return userId;
    }

    public ProfileInformationChangeServiceModel setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public MultipartFile getImg() {
        return img;
    }

    public ProfileInformationChangeServiceModel setImg(MultipartFile img) {
        this.img = img;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ProfileInformationChangeServiceModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ProfileInformationChangeServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getProfession() {
        return profession;
    }

    public ProfileInformationChangeServiceModel setProfession(String profession) {
        this.profession = profession;
        return this;
    }

    public Integer getYearsExperience() {
        return yearsExperience;
    }

    public ProfileInformationChangeServiceModel setYearsExperience(Integer yearsExperience) {
        this.yearsExperience = yearsExperience;
        return this;
    }

    public String getPresentation() {
        return presentation;
    }

    public ProfileInformationChangeServiceModel setPresentation(String presentation) {
        this.presentation = presentation;
        return this;
    }
}
