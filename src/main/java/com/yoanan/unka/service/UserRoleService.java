package com.yoanan.unka.service;

import com.yoanan.unka.model.service.UserRoleServiceModel;

import java.util.List;

public interface UserRoleService {

    List<UserRoleServiceModel> findAllWithoutStudentAndRootAdmin();
}
