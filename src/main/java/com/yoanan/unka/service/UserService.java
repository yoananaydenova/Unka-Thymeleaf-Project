package com.yoanan.unka.service;


import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.entity.enums.UserRole;
import com.yoanan.unka.model.service.UserRegisterServiceModel;
import com.yoanan.unka.model.service.UserServiceModel;

import java.util.List;

public interface UserService {

    void seedUsers();

    UserServiceModel registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel);

    boolean usernameExists(String username);

    void addRole(String username, UserRole role);

    UserEntity findByUsername(String username);

    List<UserServiceModel> findAll();

    boolean currentUserIsTeacher();

    boolean currentUserIsAdmin();

    UserServiceModel findUserById(Long userId);

    boolean findUserByIdIsLogedUser(Long id);

    UserServiceModel saveChangeFullName(UserServiceModel userServiceModel);

    UserServiceModel changeRoleOfUser(Long userId, Long newRoleId);

    String findFullName();
}
