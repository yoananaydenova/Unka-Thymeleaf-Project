package com.yoanan.unka.model.view;

public class ProfileInformationViewModel extends BaseViewModel{

    private UserViewModel user;
    private String imgUrl;
    private String phoneNumber;
    private String profession;
    private Integer yearsExperience;
    private String presentation;

    public ProfileInformationViewModel() {
    }

    public UserViewModel getUser() {
        return user;
    }

    public ProfileInformationViewModel setUser(UserViewModel user) {
        this.user = user;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public ProfileInformationViewModel setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ProfileInformationViewModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getProfession() {
        return profession;
    }

    public ProfileInformationViewModel setProfession(String profession) {
        this.profession = profession;
        return this;
    }

    public Integer getYearsExperience() {
        return yearsExperience;
    }

    public ProfileInformationViewModel setYearsExperience(Integer yearsExperience) {
        this.yearsExperience = yearsExperience;
        return this;
    }

    public String getPresentation() {
        return presentation;
    }

    public ProfileInformationViewModel setPresentation(String presentation) {
        this.presentation = presentation;
        return this;
    }
}
