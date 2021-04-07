package com.yoanan.unka.web;

import com.yoanan.unka.model.entity.BaseEntity;
import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.entity.UserRoleEntity;
import com.yoanan.unka.model.entity.enums.UserRole;
import com.yoanan.unka.repository.UserRepository;
import com.yoanan.unka.repository.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.StrictMath.toIntExact;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    private Long TEST_USER1_ID, TEST_USER2_ID;
    private String TEST_USER1_USERNAME = "pesho", TEST_USER2_USERNAME = "marina";
    private String TEST_USER1_FULLNAME, TEST_USER2_FULLNAME;
    private Long STUDENT_ROLE_ID, TEACHER_ROLE_ID, ADMIN_ROLE_ID, ROOT_ADMIN_ROLE_ID;


    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();

        UserRoleEntity roleStudent = new UserRoleEntity();
        roleStudent.setRole(UserRole.STUDENT);
        roleStudent = userRoleRepository.save(roleStudent);
        STUDENT_ROLE_ID =roleStudent.getId();

        UserRoleEntity roleTeacher = new UserRoleEntity();
        roleTeacher.setRole(UserRole.TEACHER);
        roleTeacher = userRoleRepository.save(roleTeacher);
        TEACHER_ROLE_ID = roleTeacher.getId();

        UserRoleEntity roleAdmin = new UserRoleEntity();
        roleAdmin.setRole(UserRole.ADMIN);
        roleAdmin = userRoleRepository.save(roleAdmin);
        ADMIN_ROLE_ID = roleAdmin.getId();

        UserRoleEntity roleRootAdmin = new UserRoleEntity();
        roleRootAdmin.setRole(UserRole.ROOT_ADMIN);
        roleRootAdmin = userRoleRepository.save(roleRootAdmin);
        ROOT_ADMIN_ROLE_ID = roleRootAdmin.getId();

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setUsername(TEST_USER1_USERNAME).setPassword("xyz123").setFullName("pesho peshov");
        userEntity1.setRoles(List.of(roleStudent, roleTeacher, roleAdmin, roleRootAdmin));
        userEntity1 = userRepository.save(userEntity1);
        TEST_USER1_ID = userEntity1.getId();
        TEST_USER1_FULLNAME = userEntity1.getFullName();

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setUsername(TEST_USER2_USERNAME).setPassword("xyz123").setFullName("marina petrova");
        userEntity2.setRoles(List.of(roleStudent, roleTeacher, roleAdmin));
        userEntity2 = userRepository.save(userEntity2);
        TEST_USER2_ID = userEntity2.getId();
        TEST_USER2_FULLNAME = userEntity2.getFullName();

    }

    @AfterEach
    public void tearDown(){
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"STUDENT", "TEACHER", "ADMIN"})
    public void testFetchUsers() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/api/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").value(TEST_USER1_ID ))
                .andExpect(jsonPath("[0].username").value(TEST_USER1_USERNAME))
                .andExpect(jsonPath("[0].fullName").value(TEST_USER1_FULLNAME))
                .andExpect(jsonPath("[0].roleId").isArray())
                .andExpect(jsonPath("[0].roleId", hasSize(4)))
                .andExpect(jsonPath("[0].roleId", hasItem(toIntExact(STUDENT_ROLE_ID))))
                .andExpect(jsonPath("[0].roleId", hasItem(toIntExact(TEACHER_ROLE_ID))))
                .andExpect(jsonPath("[0].roleId", hasItem(toIntExact(ADMIN_ROLE_ID))))
                .andExpect(jsonPath("[0].roleId", hasItem(toIntExact(ROOT_ADMIN_ROLE_ID))))
                .andExpect(jsonPath("[1].id").value(TEST_USER2_ID ))
                .andExpect(jsonPath("[1].username").value(TEST_USER2_USERNAME))
                .andExpect(jsonPath("[1].fullName").value(TEST_USER2_FULLNAME))
                .andExpect(jsonPath("[1].roleId").isArray())
                .andExpect(jsonPath("[1].roleId", hasSize(3)))
                .andExpect(jsonPath("[1].roleId", hasItem(toIntExact(STUDENT_ROLE_ID))))
                .andExpect(jsonPath("[1].roleId", hasItem(toIntExact(TEACHER_ROLE_ID))))
                .andExpect(jsonPath("[1].roleId", hasItem(toIntExact(ADMIN_ROLE_ID))));
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"STUDENT", "TEACHER", "ADMIN"})
    public void testFetchUsersSize() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/api/all"))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"STUDENT"})
    public void testFetchUsersWithRoleStudentReturnForbiddenResponseStatus() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/api/all"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"TEACHER"})
    public void testFetchUsersWithRoleTeacherReturnForbiddenResponseStatus() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/api/all"))
                .andExpect(status().isForbidden());
    }
}
