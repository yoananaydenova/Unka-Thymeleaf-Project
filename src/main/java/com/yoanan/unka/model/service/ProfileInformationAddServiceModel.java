package com.yoanan.unka.model.service;

public class ProfileInformationAddServiceModel extends BaseServiceModel {

    private Long userId;
    private String profession;
    private Integer yearsExperience;
    private String presentation;

    public ProfileInformationAddServiceModel() {
    }

    public Long getUserId() {
        return userId;
    }

    public ProfileInformationAddServiceModel setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getProfession() {
        return profession;
    }

    public ProfileInformationAddServiceModel setProfession(String profession) {
        this.profession = profession;
        return this;
    }

    public Integer getYearsExperience() {
        return yearsExperience;
    }

    public ProfileInformationAddServiceModel setYearsExperience(Integer yearsExperience) {
        this.yearsExperience = yearsExperience;
        return this;
    }

    public String getPresentation() {
        return presentation;
    }

    public ProfileInformationAddServiceModel setPresentation(String presentation) {
        this.presentation = presentation;
        return this;
    }
}
