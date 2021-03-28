package com.yoanan.unka.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "profile_information")
public class ProfileInformationEntity extends BaseEntity {

    private UserEntity user;
    private String imgUrl;
    private String phoneNumber;
    private String profession;
    private Integer yearsExperience;
    private String presentation;

    public ProfileInformationEntity() {
    }

    @OneToOne
    public UserEntity getUser() {
        return user;
    }

    public ProfileInformationEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    @Column(name = "img")
    public String getImgUrl() {
        return imgUrl;
    }

    public ProfileInformationEntity setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ProfileInformationEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Column(name = "profession")
    public String getProfession() {
        return profession;
    }

    public ProfileInformationEntity setProfession(String profession) {
        this.profession = profession;
        return this;
    }

    @Column(name = "years_experience")
    public Integer getYearsExperience() {
        return yearsExperience;
    }

    public ProfileInformationEntity setYearsExperience(Integer yearsExperience) {
        this.yearsExperience = yearsExperience;
        return this;
    }


    @Column(name = "presentation", columnDefinition = "TEXT")
    public String getPresentation() {
        return presentation;
    }

    public ProfileInformationEntity setPresentation(String presentation) {
        this.presentation = presentation;
        return this;
    }
}
