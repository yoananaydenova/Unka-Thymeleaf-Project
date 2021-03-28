package com.yoanan.unka.model.binding;

import com.yoanan.unka.model.entity.UserEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProfileInformationAddBindingModel {


    private String profession;
    private Integer yearsExperience;
    private String presentation;

    public ProfileInformationAddBindingModel() {
    }

    public String getProfession() {
        return profession;
    }

    public ProfileInformationAddBindingModel setProfession(String profession) {
        this.profession = profession;
        return this;
    }

    public Integer getYearsExperience() {
        return yearsExperience;
    }

    public ProfileInformationAddBindingModel setYearsExperience(Integer yearsExperience) {
        this.yearsExperience = yearsExperience;
        return this;
    }

    public String getPresentation() {
        return presentation;
    }

    public ProfileInformationAddBindingModel setPresentation(String presentation) {
        this.presentation = presentation;
        return this;
    }
}
