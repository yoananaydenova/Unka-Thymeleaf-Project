package com.yoanan.unka.service.impl;

import com.yoanan.unka.model.entity.enums.UserRole;
import com.yoanan.unka.model.service.UserRoleServiceModel;
import com.yoanan.unka.repository.UserRoleRepository;
import com.yoanan.unka.service.UserRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<UserRoleServiceModel> findAllWithoutStudent() {
        return userRoleRepository
                .findAllByRoleNotLike(UserRole.STUDENT)
                .stream()
                .map(role-> modelMapper.map(role, UserRoleServiceModel.class ))
                .collect(Collectors.toList());
    }
}
