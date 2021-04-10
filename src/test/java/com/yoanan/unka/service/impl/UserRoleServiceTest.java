package com.yoanan.unka.service.impl;

import com.yoanan.unka.model.entity.UserRoleEntity;
import com.yoanan.unka.model.entity.enums.UserRole;
import com.yoanan.unka.model.service.UserRoleServiceModel;
import com.yoanan.unka.repository.UserRoleRepository;
import com.yoanan.unka.service.UserRoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {

    UserRoleService userRoleServiceToTest;

    @Mock
    UserRoleRepository userRoleRepository;

    ModelMapper modelMapper;

    @BeforeEach
    public void SetUp() {
        modelMapper = new ModelMapper();
        userRoleServiceToTest = new UserRoleServiceImpl(userRoleRepository, modelMapper);

    }


    @Test
    public void findAllWithoutStudentAndRootAdmin_whenHaveRoles_shoudReturnCorrectRoles() {
        UserRoleEntity userRoleTeacher = new UserRoleEntity(UserRole.TEACHER);
        userRoleTeacher.setId(2L);

        UserRoleEntity userRoleAdmin = new UserRoleEntity(UserRole.ADMIN);
        userRoleAdmin.setId(3L);


        when(userRoleRepository.findByRole(UserRole.TEACHER))
                .thenReturn(Optional.of(userRoleTeacher));

        when(userRoleRepository.findByRole(UserRole.ADMIN))
                .thenReturn(Optional.of(userRoleAdmin));

        List<UserRoleServiceModel> allWithoutStudentAndRootAdmin =
                userRoleServiceToTest.findAllWithoutStudentAndRootAdmin();

        Assertions.assertEquals(2, allWithoutStudentAndRootAdmin.size());

        List<UserRole> userRoleResult
                = allWithoutStudentAndRootAdmin
                .stream()
                .map(UserRoleServiceModel::getRole)
                .collect(Collectors.toList());

        Assertions.assertEquals(List.of(UserRole.TEACHER, UserRole.ADMIN), userRoleResult);
    }

    @Test
    public void findAllWithoutStudentAndRootAdmin_whenNOTFoundTeacher_shouldThrow() {

        UserRoleEntity userRoleTeacher = new UserRoleEntity(UserRole.TEACHER);
        userRoleTeacher.setId(2L);

        when(userRoleRepository.findByRole(UserRole.TEACHER))
                .thenReturn(Optional.of(userRoleTeacher));

        Assertions.assertThrows(
                IllegalStateException.class, () -> {
                    userRoleServiceToTest.findAllWithoutStudentAndRootAdmin();
                }
        );
    }

    @Test
    public void findAllWithoutStudentAndRootAdmin_whenNOTFoundAdmin_shouldThrow() {
        Assertions.assertThrows(
                IllegalStateException.class, () -> {
                    userRoleServiceToTest.findAllWithoutStudentAndRootAdmin();
                }
        );
    }


    @Test
    public void findByRole_whenCanNOTFindRoleInDB_shouldThrow(){

        Assertions.assertThrows(
                IllegalStateException.class, () -> {
                    userRoleServiceToTest.findByRole(UserRole.TEACHER);
                }
        );
    }

    @Test
    public void findByRole_whenFindRoleInDB_shouldReturnCorrect(){
        UserRoleEntity userRoleTeacher = new UserRoleEntity(UserRole.TEACHER);
        userRoleTeacher.setId(2L);

        when(userRoleRepository.findByRole(UserRole.TEACHER))
                .thenReturn(Optional.of(userRoleTeacher));

        UserRoleServiceModel userRoleServiceModelToTest = userRoleServiceToTest.findByRole(UserRole.TEACHER);

        Assertions.assertEquals(userRoleTeacher.getId(),userRoleServiceModelToTest.getId());
        Assertions.assertEquals(userRoleTeacher.getRole(),userRoleServiceModelToTest.getRole());

    }


    @Test
    public void findById_whenCanNOTFindRoleInDB_shouldThrow(){
        Assertions.assertThrows(
                IllegalStateException.class, () -> {
                    userRoleServiceToTest.findById(10L);
                }
        );
    }
    @Test
    public void findById_whenFindRoleInDB_shouldReturnCorrect(){

        UserRoleEntity userRoleTeacher = new UserRoleEntity(UserRole.TEACHER);
        userRoleTeacher.setId(2L);

        when(userRoleRepository.findById(userRoleTeacher.getId()))
                .thenReturn(Optional.of(userRoleTeacher));

        UserRoleServiceModel userRoleServiceModelToTest = userRoleServiceToTest.findById(userRoleTeacher.getId());

        Assertions.assertEquals(userRoleTeacher.getId(),userRoleServiceModelToTest.getId());
        Assertions.assertEquals(userRoleTeacher.getRole(),userRoleServiceModelToTest.getRole());
    }

    @Test
    public void findAllRoles_whenFindAllRoleInDB_shouldReturnCorrect(){

        UserRoleEntity userRoleStudent = new UserRoleEntity(UserRole.STUDENT);
        userRoleStudent.setId(1L);

        UserRoleEntity userRoleTeacher = new UserRoleEntity(UserRole.TEACHER);
        userRoleTeacher.setId(2L);

        UserRoleEntity userRoleAdmin = new UserRoleEntity(UserRole.ADMIN);
        userRoleAdmin.setId(3L);

        UserRoleEntity userRoleRootAdmin = new UserRoleEntity(UserRole.ROOT_ADMIN);
        userRoleRootAdmin.setId(4L);

        List<UserRoleEntity> userRoleList = List.of(userRoleStudent, userRoleTeacher, userRoleAdmin, userRoleRootAdmin);

        List<UserRole> expectUserRoles = userRoleList.stream()
                .map(UserRoleEntity::getRole)
                .collect(Collectors.toList());

        when(userRoleRepository.findAll())
                .thenReturn(userRoleList);

        List<UserRoleServiceModel> allRolesToTest = userRoleServiceToTest.findAllRoles();

        List<UserRole> userRolesTest = allRolesToTest.stream()
                .map(UserRoleServiceModel::getRole)
                .collect(Collectors.toList());

        Assertions.assertEquals(userRoleList.size(),allRolesToTest.size());
        Assertions.assertEquals(expectUserRoles,userRolesTest);
    }


    @Test
    public void findAllRoles_whenCanNotFindRoleInDB_shouldReturnEmptyList(){

        when(userRoleRepository.findAll())
                .thenReturn(new ArrayList<>());

        List<UserRoleServiceModel> allRolesToTest = userRoleServiceToTest.findAllRoles();

        Assertions.assertEquals(0,allRolesToTest.size());

    }
}
