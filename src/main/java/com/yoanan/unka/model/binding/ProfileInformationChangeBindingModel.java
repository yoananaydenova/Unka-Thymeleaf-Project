package com.yoanan.unka.model.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

public class ProfileInformationChangeBindingModel {

    // Profile information
    private Long userId;
    // For upload of new image
    private MultipartFile img;
    private String phoneNumber;
    private String email;
    private String profession;
    private Integer yearsExperience;
    private String presentation;

    // User information
    private Long newRole;
    private String fullName;

    // Not editable information
    private String username;
    private String imgUrl;
    private List<Long> rolesId = new ArrayList<>();
    private int teachCourses;
    private int enrolledCourses;



    public ProfileInformationChangeBindingModel() {
    }

    @NotNull
    @Min(value = 1, message = "User id must be present!")
    public Long getUserId() {
        return userId;
    }

    public ProfileInformationChangeBindingModel setUserId(Long userId) {
        this.userId = userId;
        return this;
    }


    public MultipartFile getImg() {
        return img;
    }

    public ProfileInformationChangeBindingModel setImg(MultipartFile img) {
        this.img = img;
        return this;
    }

    @Length(max = 10, message = "Телефонният номер е с максимална дължина 10 цифри!")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ProfileInformationChangeBindingModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Email(message = "Въведете валиден имейл адрес!")
    @Pattern(regexp = "^$|([a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$)", message = "Въведете валиден имейл адрес!")
    public String getEmail() {
        return email;
    }

    public ProfileInformationChangeBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @Length(max = 30, message = "Професията е с максимална дължина 30 символа!")
    public String getProfession() {
        return profession;
    }

    public ProfileInformationChangeBindingModel setProfession(String profession) {
        this.profession = profession;
        return this;
    }

    @Min(value = 0, message = "Въведете положитено число!")
    public Integer getYearsExperience() {
        return yearsExperience;
    }

    public ProfileInformationChangeBindingModel setYearsExperience(Integer yearsExperience) {
        this.yearsExperience = yearsExperience;
        return this;
    }

    @Length(max = 500, message = "Представянето е с максимална дължина 500 символа!")
    public String getPresentation() {
        return presentation;
    }

    public ProfileInformationChangeBindingModel setPresentation(String presentation) {
        this.presentation = presentation;
        return this;
    }

    @Min(0)
    public Long getNewRole() {
        return newRole;
    }

    public ProfileInformationChangeBindingModel setNewRole(Long newRole) {
        this.newRole = newRole;
        return this;
    }


    @Length(min = 6, max = 30, message = "Броят на символите за име и фамилия трябва да е минимум 6 и максимум 30 символа!")
    public String getFullName() {
        return fullName;
    }

    public ProfileInformationChangeBindingModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public ProfileInformationChangeBindingModel setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public List<Long> getRolesId() {
        return rolesId;
    }

    public ProfileInformationChangeBindingModel setRolesId(List<Long> rolesId) {
        this.rolesId = rolesId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ProfileInformationChangeBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getTeachCourses() {
        return teachCourses;
    }

    public ProfileInformationChangeBindingModel setTeachCourses(int teachCourses) {
        this.teachCourses = teachCourses;
        return this;
    }

    public int getEnrolledCourses() {
        return enrolledCourses;
    }

    public ProfileInformationChangeBindingModel setEnrolledCourses(int enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
        return this;
    }
}
