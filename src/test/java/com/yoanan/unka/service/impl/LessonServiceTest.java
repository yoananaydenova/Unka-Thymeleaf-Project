package com.yoanan.unka.service.impl;

import com.yoanan.unka.config.IAuthenticationFacade;
import com.yoanan.unka.model.entity.LessonEntity;
import com.yoanan.unka.model.service.LessonServiceModel;
import com.yoanan.unka.repository.LessonRepository;
import com.yoanan.unka.service.CloudinaryService;
import com.yoanan.unka.service.CourseService;
import com.yoanan.unka.service.LessonService;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTest {

    LessonService lessonServiceToTest;

    @Mock
    LessonRepository lessonRepository;
    @Mock
    CloudinaryService cloudinaryService;
    @Mock
    CourseService courseService;
    @Mock
    IAuthenticationFacade authenticationFacade;

    ModelMapper modelMapper;

    @BeforeEach
    public void SetUp() {
        modelMapper = new ModelMapper();
        lessonServiceToTest = new LessonServiceImpl(
                lessonRepository,
                cloudinaryService,
                courseService,
                authenticationFacade,
                modelMapper
        );
    }


    @Test
    public void existLessonByTitleAndCourseId_whenExist_shouldReturnTrue(){

        when(lessonRepository
                .existsByTitleAndCourse_Id("Title", 5L))
                .thenReturn(true);
        Assertions.assertTrue(lessonServiceToTest.existLessonByTitleAndCourseId("Title", 5L));
    }

    @Test
    public void findAllLessonsByCourseId_whenNotExist_shouldReturnEmptyList(){
        when(lessonRepository
                .findAllByCourse_Id( 5L))
                .thenReturn(new ArrayList<>());
        List<LessonServiceModel> allLessonsByCourseId = lessonServiceToTest.findAllLessonsByCourseId(5L);
        Assertions.assertEquals(0, allLessonsByCourseId.size());
    }

    @Test
    public void findAllLessonsByCourseId_whenExistLessons_shouldReturnCorrectList(){
        LessonEntity lesson1 = new LessonEntity();
        lesson1.setId(1L);
        lesson1.setTitle("First");

        LessonEntity lesson2 = new LessonEntity();
        lesson2.setId(2L);
        lesson2.setTitle("Second");

        when(lessonRepository
                .findAllByCourse_Id( 5L))
                .thenReturn(List.of(lesson1, lesson2));

        List<LessonServiceModel> allLessonsByCourseId = lessonServiceToTest.findAllLessonsByCourseId(5L);
        Assertions.assertEquals(2, allLessonsByCourseId.size());
        Assertions.assertEquals("First", allLessonsByCourseId.get(0).getTitle());
        Assertions.assertEquals("Second", allLessonsByCourseId.get(1).getTitle());
    }

    @Test
    public void findLessonById_shouldThrow(){
        Assertions.assertThrows(
                IllegalStateException.class, () -> {
                    lessonServiceToTest.findLessonById(10L);
                }
        );
    }

    @Test
    public void findLessonById_shouldReturnLesson(){
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setTitle("Title");
        lessonEntity.setId(10L);

        when(lessonRepository.findById(10L))
                .thenReturn(Optional.of(lessonEntity));

        LessonServiceModel lessonResult = lessonServiceToTest.findLessonById(10L);

        Assertions.assertEquals("Title", lessonResult.getTitle());
        Assertions.assertEquals(10L, lessonResult.getId());

    }


}
