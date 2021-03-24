package com.yoanan.unka.model.binding;

import com.yoanan.unka.validators.FieldMatch;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@FieldMatch(
        first = "password",
        second = "repeatPassword"
)
public class UserRegisterBindingModel {

    private String username;
    private String fullName;
    private String email;
    private String password;
    private String repeatPassword;

    public UserRegisterBindingModel() {
    }

    @NotEmpty
    @Length(min = 6, max = 20, message = "Username length must be minimum 6 and maximum 20 characters!")
    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @NotEmpty
    @Length(min = 6, max=30, message = "Full name length must be minimum 6 and maximum 30 characters!")
    public String getFullName() {
        return fullName;
    }

    public UserRegisterBindingModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @NotEmpty
    @Email(message = "Enter valid email address!")
    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @NotEmpty
    @Length(min = 6, max = 20, message = "Password length must be minimum 6 and maximum 20 characters!")
    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @NotEmpty
    public String getRepeatPassword() {
        return repeatPassword;
    }

    public UserRegisterBindingModel setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
        return this;
    }
}
