package com.yoanan.unka.service.impl;

import com.yoanan.unka.model.entity.UserRoleEntity;
import com.yoanan.unka.model.entity.enums.UserRole;
import com.yoanan.unka.model.service.UserRoleServiceModel;
import com.yoanan.unka.repository.UserRoleRepository;
import com.yoanan.unka.service.UserRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
    public List<UserRoleServiceModel> findAllWithoutStudentAndRootAdmin() {
        UserRoleEntity teacherRoleEntity = userRoleRepository
                .findByRole(UserRole.TEACHER)
                .orElseThrow(() -> new IllegalStateException("Role not found! Please seed the roles!"));

        UserRoleEntity adminRoleEntity = userRoleRepository
                .findByRole(UserRole.ADMIN)
                .orElseThrow(() -> new IllegalStateException("Role not found! Please seed the roles!"));

        return List.of(teacherRoleEntity, adminRoleEntity)
                .stream()
                .map(role -> modelMapper.map(role, UserRoleServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void seedRoles() {

        if (userRoleRepository.count() == 0) {
            Arrays.stream(UserRole.values())
                    .forEach(roleName -> userRoleRepository.save(new UserRoleEntity().setRole(roleName)));
        }

    }

    @Override
    public UserRoleServiceModel findByRole(UserRole userRole) {
        UserRoleEntity userRoleEntity = userRoleRepository.findByRole(userRole)
                .orElseThrow(() -> new IllegalStateException("User role not found! Please seed the roles!"));

        return modelMapper.map(userRoleEntity, UserRoleServiceModel.class);
    }

    @Override
    public UserRoleServiceModel findById(Long newRoleId) {
        UserRoleEntity userRoleEntity = userRoleRepository.findById(newRoleId)
                .orElseThrow(() -> new IllegalStateException("Role with id " + newRoleId + " does not exist!!!"));

        return modelMapper.map(userRoleEntity, UserRoleServiceModel.class);
    }

    @Override
    public List<UserRoleServiceModel> findAllRoles() {

       return userRoleRepository
                .findAll()
                .stream()
                .map(role -> modelMapper.map(role, UserRoleServiceModel.class))
                .collect(Collectors.toList());
    }
}
