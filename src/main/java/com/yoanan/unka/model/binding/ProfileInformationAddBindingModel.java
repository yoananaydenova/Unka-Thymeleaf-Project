package com.yoanan.unka.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

public class ProfileInformationAddBindingModel {


    private String profession;
    private Integer yearsExperience;
    private String presentation;

    public ProfileInformationAddBindingModel() {
    }

    @Length(max = 30, message = "Професията е с максимална дължина 30 символа!")
    public String getProfession() {
        return profession;
    }

    public ProfileInformationAddBindingModel setProfession(String profession) {
        this.profession = profession;
        return this;
    }

    @Min(value = 0, message = "Въведете положитено число!")
    public Integer getYearsExperience() {
        return yearsExperience;
    }

    public ProfileInformationAddBindingModel setYearsExperience(Integer yearsExperience) {
        this.yearsExperience = yearsExperience;
        return this;
    }

    @Length(max = 500, message = "Представянето е с максимална дължина 500 символа!")
    public String getPresentation() {
        return presentation;
    }

    public ProfileInformationAddBindingModel setPresentation(String presentation) {
        this.presentation = presentation;
        return this;
    }
}
