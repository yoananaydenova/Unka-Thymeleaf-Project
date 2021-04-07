package com.yoanan.unka.service.impl;

import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.entity.UserRoleEntity;
import com.yoanan.unka.model.entity.enums.UserRole;
import com.yoanan.unka.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class UnkaUserDetailsServiceTest {


    private UnkaUserDetailsService serviceToTest;

    @Mock
    UserRepository mockUserRepository;

    @BeforeEach
    public void setUp() {

        serviceToTest = new UnkaUserDetailsService(mockUserRepository);
    }

    @Test
    void testUserNotFound() {

        Assertions.assertThrows(
                UsernameNotFoundException.class, () -> {
                    serviceToTest.loadUserByUsername("user_does_not_exits");
                }
        );
    }

    @Test
    void testExistingUser() {
        // prepare data
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("pesho123");
        userEntity.setPassword("123456");
        userEntity.setFullName("Petar Petrov");

        UserRoleEntity roleStudent = new UserRoleEntity();
        roleStudent.setRole(UserRole.STUDENT);

        UserRoleEntity roleTeacher = new UserRoleEntity();
        roleTeacher.setRole(UserRole.TEACHER);

        UserRoleEntity roleAdmin = new UserRoleEntity();
        roleAdmin.setRole(UserRole.ADMIN);

        UserRoleEntity roleRootAdmin = new UserRoleEntity();
        roleRootAdmin.setRole(UserRole.ROOT_ADMIN);

        userEntity.setRoles(List.of(roleStudent, roleTeacher, roleAdmin, roleRootAdmin));

        // configure mocks
        Mockito.when(mockUserRepository.findByUsername("pesho123"))
                .thenReturn(Optional.of(userEntity));

        // test
       UserDetails userDetails = serviceToTest.loadUserByUsername("pesho123");

       Assertions.assertEquals(userEntity.getUsername(), userDetails.getUsername());
       Assertions.assertEquals(4, userDetails.getAuthorities().size());

      List<String> authorities = userDetails
               .getAuthorities()
               .stream()
               .map(GrantedAuthority::getAuthority)
               .collect(Collectors.toList());

      Assertions.assertTrue(authorities.contains("ROLE_STUDENT"));
      Assertions.assertTrue(authorities.contains("ROLE_TEACHER"));
      Assertions.assertTrue(authorities.contains("ROLE_ADMIN"));
      Assertions.assertTrue(authorities.contains("ROLE_ROOT_ADMIN"));
    }
}
