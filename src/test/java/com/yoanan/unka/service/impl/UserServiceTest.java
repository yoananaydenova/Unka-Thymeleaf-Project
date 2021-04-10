package com.yoanan.unka.service.impl;

import com.yoanan.unka.config.AuthenticationFacade;
import com.yoanan.unka.config.IAuthenticationFacade;
import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.entity.UserRoleEntity;
import com.yoanan.unka.model.entity.enums.UserRole;
import com.yoanan.unka.model.service.UserRoleServiceModel;
import com.yoanan.unka.model.service.UserServiceModel;
import com.yoanan.unka.repository.UserRepository;
import com.yoanan.unka.service.UserRoleService;
import com.yoanan.unka.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final String TEACHER_USERNAME = "pesho123";
    private final Long TEACHER_ID = 1L;
    private final String TEACHER_PASSWORD = "123456";
    private final String TEACHER_FULLNAME = "Pesho Peshev";

    private UserService userServiceToTest;

    @Mock
    UserRepository userRepository;

    @Mock
    UnkaUserDetailsService unkaUserDetailsService;

    @Mock
    UserRoleService userRoleService;


    @Mock
    IAuthenticationFacade authenticationFacade;

    ModelMapper modelMapper;
    PasswordEncoder passwordEncoder;


    UserEntity userStudent;
    UserEntity userTeacher;
    UserRoleEntity userRoleStudent;
    UserRoleEntity userRoleTeacher;
    UserRoleEntity userRoleAdmin;

    @BeforeEach
    public void SetUp() {

        modelMapper = new ModelMapper();
        passwordEncoder = new BCryptPasswordEncoder();
        authenticationFacade = new AuthenticationFacade();

        userServiceToTest = new UserServiceImpl(
                userRepository,
                userRoleService,
                passwordEncoder,
                modelMapper,
                unkaUserDetailsService,
                authenticationFacade
        );
        userRoleStudent = new UserRoleEntity(UserRole.STUDENT);
        userRoleStudent.setId(1L);

        userRoleTeacher = new UserRoleEntity(UserRole.TEACHER);
        userRoleTeacher.setId(2L);

        userRoleAdmin = new UserRoleEntity(UserRole.ADMIN);
        userRoleAdmin.setId(3L);


        userTeacher = new UserEntity();
        userTeacher.setId(TEACHER_ID);
        userTeacher.setUsername(TEACHER_USERNAME);
        userTeacher.setPassword(TEACHER_PASSWORD);
        userTeacher.setFullName(TEACHER_FULLNAME);
        userTeacher.setRoles(List.of(userRoleStudent, userRoleTeacher));

        userStudent = new UserEntity();
        userStudent.setId(2L);
        userStudent.setUsername("Student");
        userStudent.setPassword("abcdef");
        userStudent.setFullName("Pesho Peshev");
        userStudent.setRoles(List.of(userRoleStudent));


    }


//    @Test
//    public void testRegisterUserWithCorrectInfo() {
//
//        UserRegisterServiceModel userRegisterServiceModel = new UserRegisterServiceModel();
//        userRegisterServiceModel.setFullName(FULL_NAME);
//        userRegisterServiceModel.setUsername(USERNAME);
//        userRegisterServiceModel.setPassword(PASSWORD);
//
//        UserRoleServiceModel userRoleServiceModelStudent = new UserRoleServiceModel(UserRole.STUDENT);
//        userRoleServiceModelStudent.setId(1L);
//        UserRoleServiceModel userRoleServiceModelTeacher = new UserRoleServiceModel(UserRole.TEACHER);
//        userRoleServiceModelTeacher.setId(2L);
//        UserRoleServiceModel userRoleServiceModelAdmin = new UserRoleServiceModel(UserRole.ADMIN);
//        userRoleServiceModelAdmin.setId(3L);
//        UserRoleServiceModel userRoleServiceModelRootAdmin = new UserRoleServiceModel(UserRole.ROOT_ADMIN);
//        userRoleServiceModelRootAdmin.setId(4L);
//
//        List<UserRoleServiceModel> roles = List.of(userRoleServiceModelStudent,
//                userRoleServiceModelTeacher,
//                userRoleServiceModelAdmin,
//                userRoleServiceModelRootAdmin);
//
//        Mockito.lenient().when(userRoleService.findAllRoles())
//                .thenReturn(List.of(userRoleServiceModelStudent,
//                        userRoleServiceModelTeacher,
//                        userRoleServiceModelAdmin,
//                        userRoleServiceModelRootAdmin));
//
//        Mockito.lenient().when(userRoleService.findByRole(UserRole.STUDENT))
//                .thenReturn(userRoleServiceModelStudent);
//        UserEntity user = modelMapper.map(userRegisterServiceModel, UserEntity.class);
//
//
//        List<UserRoleEntity> collect = roles.stream().map(r -> modelMapper.map(r, UserRoleEntity.class)).collect(Collectors.toList());
//        user.setRoles(collect);
//        when(userRepository.save(user)).thenReturn(user);
//
//        UserServiceModel userServiceModel = userServiceToTest.registerAndLoginUser(userRegisterServiceModel);
//        Assertions.assertEquals(userServiceModel.getUsername(), userRegisterServiceModel.getUsername());
//    }

    @Test
    public void usernameExists_whenUserIsInDb_shouldReturnTrue() {
        UserEntity user = new UserEntity();
        user.setUsername(TEACHER_USERNAME);

        when(userRepository.findByUsername(TEACHER_USERNAME))
                .thenReturn(Optional.of(user));
        Assertions.assertTrue(userServiceToTest.usernameExists(TEACHER_USERNAME));
    }

    @Test
    public void findByUsername_whenUserIsInDb_shouldReturnUserEntity() {

        when(userRepository.findByUsername(TEACHER_USERNAME))
                .thenReturn(Optional.of(userTeacher));

        UserEntity userEntityToTest = userServiceToTest.findByUsername(TEACHER_USERNAME);

        Assertions.assertEquals(TEACHER_USERNAME, userEntityToTest.getUsername());
        Assertions.assertEquals(TEACHER_FULLNAME, userEntityToTest.getFullName());
        Assertions.assertEquals(TEACHER_PASSWORD, userEntityToTest.getPassword());
        Assertions.assertEquals(userTeacher.getId(), userEntityToTest.getId());
        Assertions.assertEquals(userTeacher.getRoles(), userEntityToTest.getRoles());
    }

    @Test
    public void findAll_whenExistTwoUsersIsInDb_shouldReturnListOfTwoUserServiceModel() {

        when(userRepository.findAll())
                .thenReturn(List.of(userStudent, userTeacher));


        List<UserServiceModel> allUsers = userServiceToTest.findAll();

        Assertions.assertEquals(2, allUsers.size());

        UserServiceModel userStudentResult = allUsers.get(0);
        UserServiceModel userTeacherResult = allUsers.get(1);

        // test user 1
        Assertions.assertEquals(userStudent.getUsername(), userStudentResult.getUsername());
        Assertions.assertEquals(userStudent.getFullName(), userStudentResult.getFullName());
        Assertions.assertEquals(userStudent.getRoles().size(), userStudentResult.getRoles().size());

        // test user 2
        Assertions.assertEquals(userTeacher.getUsername(), userTeacherResult.getUsername());
        Assertions.assertEquals(userTeacher.getFullName(), userTeacherResult.getFullName());
        Assertions.assertEquals(userTeacher.getRoles().size(), userTeacherResult.getRoles().size());

    }

    @Test
    public void findAll_whenNotExistUsersIsInDb_shouldReturnEmptyList() {

        when(userRepository.findAll())
                .thenReturn(new ArrayList<>());

        List<UserServiceModel> allUsers = userServiceToTest.findAll();

        Assertions.assertEquals(0, allUsers.size());
    }


//    @Test
//    public void findUserByIdIsLogedUser_whenUserIsCurrentLoggedUser_shouldReturnTrue(){
//
//        // TODO how to mock authenticationFacade
//        when(authenticationFacade.getAuthentication())
//                .thenReturn();
//
//        when(userRepository.findById(TEACHER_ID))
//                .thenReturn(Optional.of(userTeacher));
//
//        when(userRepository.findByUsername(TEACHER_USERNAME))
//                .thenReturn(Optional.of(userTeacher));
//
//        Assertions.assertTrue(userServiceToTest.findUserByIdIsLogedUser(TEACHER_ID));
//    }

    @Test
    public void saveChangeFullName_whenCorrectChange_shouldChangeFullName() {
        //change name

        UserServiceModel userServiceModel = modelMapper.map(userTeacher, UserServiceModel.class);
        userServiceModel.setFullName("Ivan Ivanov");

        when(userRepository.findById(TEACHER_ID))
                .thenReturn(Optional.of(userTeacher));

        when(userRepository.save(userTeacher))
                .thenReturn(userTeacher);

        UserServiceModel userServiceModelResult
                = userServiceToTest.saveChangeFullName(userServiceModel);
        Assertions.assertEquals("Ivan Ivanov", userServiceModelResult.getFullName());
    }


    @Test
    public void saveChangeFullName_whenFullNameIsNull_shouldNotDoEnithing() {
        //change name
        UserServiceModel userServiceModel = modelMapper.map(userTeacher, UserServiceModel.class);
        userServiceModel.setFullName(null);

        UserServiceModel userServiceModelResult
                = userServiceToTest.saveChangeFullName(userServiceModel);

        Assertions.assertNull(userServiceModelResult.getFullName());

    }

    @Test
    public void saveChangeFullName_whenFullNameIsEmptyString_shouldNotDoEnithing() {
        //change name
        UserServiceModel userServiceModel = modelMapper.map(userTeacher, UserServiceModel.class);
        userServiceModel.setFullName("");

        UserServiceModel userServiceModelResult
                = userServiceToTest.saveChangeFullName(userServiceModel);
        Assertions.assertEquals("", userServiceModelResult.getFullName());

    }

    @Test
    public void changeRoleOfUser_whenUserIdNotExist_shouldThrow() {
        Assertions.assertThrows(
                IllegalStateException.class, () -> {
                    userServiceToTest.changeRoleOfUser(100L, 3L);
                }
        );

    }

//    @Test
//    public void changeRoleOfUserhangeRoleOfUser_whenAddNewRoleTeacherToUserStudent_shouldAddNewRoleTeacher() {
//
//        // user have role student
//        when(userRepository.findById(userStudent.getId()))
//                .thenReturn(Optional.of(userStudent));
//
//        UserRoleServiceModel userRoleServiceModelTeacher = modelMapper.map(this.userRoleTeacher, UserRoleServiceModel.class);
//        // new role is teacher
//        when(userRoleService.findById(userRoleTeacher.getId()))
//                .thenReturn(userRoleServiceModelTeacher);
//
//        // find to set role teacher
//
//        when(userRoleService.findByRole(UserRole.TEACHER))
//                .thenReturn(userRoleServiceModelTeacher);
//
//
////        UserRoleServiceModel userRoleServiceModelAdmin = modelMapper.map(userRoleAdmin, UserRoleServiceModel.class);
////        when(userRoleService.findByRole(UserRole.ADMIN))
////                .thenReturn(userRoleServiceModelAdmin);
//
//
//        when(userRepository.save(userStudent))
//                .thenReturn(userStudent);
//
//
//        UserServiceModel userServiceModel = userServiceToTest.changeRoleOfUser(2L, 2L);
//
//        List<UserRole> userRoleResult
//                = userServiceModel.getRoles()
//                .stream().map(UserRoleServiceModel::getRole).collect(Collectors.toList());
//
//        List<UserRole> roleSaved = userTeacher.getRoles()
//                .stream().map(UserRoleEntity::getRole).collect(Collectors.toList());
//
//        Assertions.assertEquals(2, userServiceModel.getRoles().size());
//        Assertions.assertEquals(roleSaved, userRoleResult);
//    }

}
