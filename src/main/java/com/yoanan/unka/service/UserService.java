package com.yoanan.unka.service;


import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.entity.enums.UserRole;
import com.yoanan.unka.model.service.CourseServiceModel;
import com.yoanan.unka.model.service.UserRegisterServiceModel;

import java.util.List;

public interface UserService {

    void seedUsers();

    void registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel);

    boolean usernameExists(String username);

    void addRole(String username, UserRole role);

    UserEntity findByUsername(String username);

}
