package com.yoanan.unka.service;


import com.yoanan.unka.model.service.UserRegisterServiceModel;

public interface UserService {
    void seedUsers();

    void registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel);


    boolean usernameExists(String username);
}
