package com.yoanan.unka.service;

import com.yoanan.unka.model.entity.enums.UserRole;
import com.yoanan.unka.model.service.UserRoleServiceModel;

import java.util.List;
import java.util.Optional;

public interface UserRoleService {

    List<UserRoleServiceModel> findAllWithoutStudentAndRootAdmin();

    void seedRoles();

    UserRoleServiceModel findByRole(UserRole userRole);

    UserRoleServiceModel findById(Long newRoleId);

    List<UserRoleServiceModel> findAllRoles();
}
