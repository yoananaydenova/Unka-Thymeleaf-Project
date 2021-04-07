package com.yoanan.unka.service.impl;

import com.yoanan.unka.config.AuthenticationFacade;
import com.yoanan.unka.config.IAuthenticationFacade;
import com.yoanan.unka.repository.UserRepository;
import com.yoanan.unka.repository.UserRoleRepository;
import com.yoanan.unka.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserRoleRepository userRoleRepository;


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

        userService = new UserServiceImpl(
                userRepository,
                userRoleRepository,
                passwordEncoder,
                modelMapper,
                unkaUserDetailsService,
                authenticationFacade
        );


    }

}
