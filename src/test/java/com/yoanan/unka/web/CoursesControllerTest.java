package com.yoanan.unka.web;

import com.yoanan.unka.model.entity.CategoryEntity;
import com.yoanan.unka.model.entity.CourseEntity;
import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class CoursesControllerTest {

    private static final String COURSES_CONTROLLER_PREFIX = "/courses";

    private Long testCourseId;
    private Long testCategoryId;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private EnrolledCoursesRepository enrolledCoursesRepository;
    @Autowired
    private ProfileInformationRepository profileInformationRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        this.init();
    }

    private void init() {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("pesho").setPassword("xyz123").setFullName("petar petrov");
        userEntity = userRepository.save(userEntity);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("Economy");
        categoryEntity.setDescription("Description");

        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setName("Accounting");
        courseEntity.setPrice(BigDecimal.TEN);
        courseEntity.setImgUrl("https://indonesiaexpat.id/wp-content/uploads/2020/03/studying-960x640.jpg");
        courseEntity.setDescription("Some great course.");

        courseEntity.setTeacher(userEntity);
        courseEntity.setCategories(Set.of(categoryEntity));
        courseEntity = courseRepository.save(courseEntity);

        categoryEntity.setCourses(Set.of(courseEntity));
        categoryEntity = categoryRepository.save(categoryEntity);

        testCategoryId = categoryEntity.getId();

        testCourseId = courseEntity.getId();
    }

    @Test
    void shouldReturnValidStatusViewNameAndModelOfDetailCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                COURSES_CONTROLLER_PREFIX + "/course/{id}", testCourseId
        ))
                .andExpect(status().isOk())
                .andExpect(view().name("details-course"))
                .andExpect(model().attributeExists("courseViewModel"))
                .andExpect(model().attributeExists("lessonsList"))
                .andExpect(model().attributeExists("profileInformation"));

    }


    @Test
    void shouldReturnValidStatusViewNameAndModelOfFindAllPaginate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                COURSES_CONTROLLER_PREFIX + "/all"
        ))
                .andExpect(status().isOk())
                .andExpect(view().name("courses"))
                .andExpect(model().attributeExists("categoryName"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalItems"))
                .andExpect(model().attributeExists("sortField"))
                .andExpect(model().attributeExists("sortDir"))
                .andExpect(model().attributeExists("reverseSortDir"));

    }

    @Test
    void shouldReturnValidStatusViewNameAndModelOfFindAllPaginateWithCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                COURSES_CONTROLLER_PREFIX + "/all/{category}", testCategoryId
        ))
                .andExpect(status().isOk())
                .andExpect(view().name("courses"))
                .andExpect(model().attributeExists("categoryName"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalItems"))
                .andExpect(model().attributeExists("sortField"))
                .andExpect(model().attributeExists("sortDir"))
                .andExpect(model().attributeExists("reverseSortDir"));
    }


    @Test
    @WithMockUser(value = "pesho", roles = {"STUDENT"})
    void shouldReturnValidStatusViewNameAndModelOfFindMyCourses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                COURSES_CONTROLLER_PREFIX + "/my-courses"
        ))
                .andExpect(status().isOk())
                .andExpect(view().name("my-courses"))
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"STUDENT", "TEACHER"})
    void shouldReturnValidStatusViewNameAndModelOfAddPageWhenUserIsTeacher() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                COURSES_CONTROLLER_PREFIX + "/add"
        ))
                .andExpect(status().isOk())
                .andExpect(view().name("add-course"))
                .andExpect(model().attributeExists("courseAddBindingModel"));
    }

    @Test
    void shouldRedirectFromAddCourseToLoginPageWhenUserIsNotLoggedIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                COURSES_CONTROLLER_PREFIX + "/add"
        ))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/login"));
    }


//    @Test
//    @WithMockUser(value = "pesho", roles = {"STUDENT", "TEACHER"})
//    void addCoursePostRequest() throws Exception {
//
//        MockMultipartFile file = new MockMultipartFile("file", "your_pic.jpg", MediaType.IMAGE_JPEG_VALUE, "your_pic.jpg".getBytes());
//
//        mockMvc.perform(multipart(COURSES_CONTROLLER_PREFIX + "/add")
//                .param("img", file(file))
//                .param("name", "test course")
//                .param("price", "10")
//                .param("description", "Some description")
//                .param("categories", "1")
//                .param("categories", "2")
//                .with(csrf()))
//                .andExpect(status().is3xxRedirection());
//
//        Assertions.assertEquals(2, courseRepository.count());
//
//    }



}
