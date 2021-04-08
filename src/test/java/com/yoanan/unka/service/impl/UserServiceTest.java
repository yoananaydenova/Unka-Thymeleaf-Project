package com.yoanan.unka.service.impl;

import com.yoanan.unka.config.AuthenticationFacade;
import com.yoanan.unka.config.IAuthenticationFacade;
import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.entity.UserRoleEntity;
import com.yoanan.unka.model.entity.enums.UserRole;
import com.yoanan.unka.model.service.UserRegisterServiceModel;
import com.yoanan.unka.repository.UserRepository;
import com.yoanan.unka.repository.UserRoleRepository;
import com.yoanan.unka.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private Long STUDENT_ROLE_ID, TEACHER_ROLE_ID, ADMIN_ROLE_ID, ROOT_ADMIN_ROLE_ID;
    private UserService userService;

    @Mock
    UserRepository userRepository;




    UnkaUserDetailsService unkaUserDetailsService;


    IAuthenticationFacade authenticationFacade;
    ModelMapper modelMapper;
    PasswordEncoder passwordEncoder;


    @BeforeEach
    public void SetUp() {
        modelMapper = new ModelMapper();
        passwordEncoder = new BCryptPasswordEncoder();
        authenticationFacade = new AuthenticationFacade();
        unkaUserDetailsService = new UnkaUserDetailsService(userRepository);
//
//        userService = new UserServiceImpl(
//                userRepository,
//                userRoleService,
//                passwordEncoder,
//                modelMapper,
//                unkaUserDetailsService,
//                authenticationFacade
//        );

//        UserRoleEntity roleStudent = new UserRoleEntity();
//        roleStudent.setRole(UserRole.STUDENT);
//        roleStudent = userRoleRepository.save(roleStudent);
//
//        when(userRoleRepository.findByRole(UserRole.STUDENT))
//                .thenReturn(Optional.of(roleStudent));

    }

    @Test
    public void testRegisterUserWithCorrectInfo() {
        UserRegisterServiceModel userRegisterServiceModel = new UserRegisterServiceModel();
        userRegisterServiceModel.setFullName("Pesho Peshev");
        userRegisterServiceModel.setUsername("pesho123");
        userRegisterServiceModel.setPassword("123456");

        UserEntity newUser = modelMapper.map(userRegisterServiceModel, UserEntity.class);

        newUser.setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()));

//        UserRoleEntity userRoleEntity = userRoleRepository.findByRole(UserRole.STUDENT);

//        newUser.addRole(userRoleEntity);

        when(userRepository.saveAndFlush(newUser))
                .thenReturn(newUser);


        userService.registerAndLoginUser(userRegisterServiceModel);
        Assertions.assertEquals(userRepository.count(), 1);

    }

}
