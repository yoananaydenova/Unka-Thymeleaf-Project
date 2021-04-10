package com.yoanan.unka.service.impl;

import com.yoanan.unka.model.entity.ExerciseEntity;
import com.yoanan.unka.model.entity.LessonEntity;
import com.yoanan.unka.model.service.ExerciseServiceModel;
import com.yoanan.unka.model.service.LessonServiceModel;
import com.yoanan.unka.repository.ExerciseRepository;
import com.yoanan.unka.service.ExerciseService;
import com.yoanan.unka.service.LessonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {

    ExerciseService exerciseServiceToTest;

    @Mock
    ExerciseRepository exerciseRepository;
    @Mock
    LessonService lessonService;

    ModelMapper modelMapper;

    @BeforeEach
    public void SetUp() {
        modelMapper = new ModelMapper();
        exerciseServiceToTest = new ExerciseServiceImpl(
                exerciseRepository,
                lessonService,
                modelMapper
        );
    }


    @Test
    public void findAllExercisesByLessonId_whenNOTFoundSavedExerciseForLessonId_shouldReturnEmptyList() {

        when(exerciseRepository
                .findAllByLesson_Id(1L))
                .thenReturn(new ArrayList<>());

        List<ExerciseServiceModel> allExercisesByLessonId = exerciseServiceToTest.findAllExercisesByLessonId(1L);

        Assertions.assertEquals(0, allExercisesByLessonId.size());
    }


    @Test
    public void findAllExercisesByLessonId_whenFoundSavedExerciseForLessonId_shouldReturnCorrectList() {

        LessonEntity lesson = new LessonEntity();
        lesson.setId(1L);

        ExerciseEntity exercise1 = new ExerciseEntity();
        exercise1.setLesson(lesson);
        exercise1.setTitle("First Exercise");

        ExerciseEntity exercise2 = new ExerciseEntity();
        exercise2.setLesson(lesson);
        exercise2.setTitle("Second Exercise");

        List<ExerciseEntity> listExercise = List.of(exercise1, exercise2);


        when(exerciseRepository
                .findAllByLesson_Id(1L))
                .thenReturn(listExercise);

        List<ExerciseServiceModel> allExercisesByLessonId = exerciseServiceToTest.findAllExercisesByLessonId(1L);

        Assertions.assertEquals(2, allExercisesByLessonId.size());
        Assertions.assertEquals(listExercise.get(0).getTitle(), allExercisesByLessonId.get(0).getTitle());
        Assertions.assertEquals(listExercise.get(1).getTitle(), allExercisesByLessonId.get(1).getTitle());

    }


    @Test
    public void isUniqueExerciseWithNameAndLessonId_whenNameIsUniqueForLesson_shouldReturnTrue() {

        when(exerciseRepository.existsByTitleAndLesson_Id("First Exercise", 1L))
                .thenReturn(true);

        Assertions.assertTrue(exerciseServiceToTest.isUniqueExerciseWithNameAndLessonId("First Exercise", 1L));
    }

    @Test
    public void findExerciseById_whenFindExercise_shouldReturnCorrect() {

        ExerciseEntity exercise1 = new ExerciseEntity();
        exercise1.setId(1L);
        exercise1.setTitle("First Exercise");

        when(exerciseRepository.findById(1L))
                .thenReturn(Optional.of(exercise1));

        ExerciseServiceModel exerciseResult = exerciseServiceToTest.findExerciseById(exercise1.getId());
        Assertions.assertEquals(exercise1.getId(), exerciseResult.getId());
        Assertions.assertEquals(exercise1.getTitle(), exerciseResult.getTitle());
    }

    @Test
    public void findExerciseById_whenNOTFindExercise_shouldThrow() {
        Assertions.assertThrows(
                IllegalStateException.class, () -> {
                    exerciseServiceToTest.findExerciseById(10L);
                }
        );
    }


//    @Test
//    public void addExercise_shouldAddExercise() {
//        LessonServiceModel lessonServiceModel = new LessonServiceModel();
//        lessonServiceModel.setId(5L);
//
//        ExerciseServiceModel exerciseServiceModel = new ExerciseServiceModel();
//        exerciseServiceModel.setId(1L);
//        exerciseServiceModel.setLesson(lessonServiceModel);
//        exerciseServiceModel.setTitle("Title");
//
//
//
//       when(lessonService.findLessonById(5L))
//                .thenReturn(lessonServiceModel);
//
//        ExerciseEntity exerciseEntity = modelMapper.map(exerciseServiceModel, ExerciseEntity.class);
//
//      when(exerciseRepository.save(exerciseEntity))
//                .thenReturn(exerciseEntity);
//
//        exerciseServiceToTest.addExercise(exerciseServiceModel);

//        Assertions.assertEquals(exerciseServiceModel.getId(), result.getId());
//        Assertions.assertEquals(exerciseServiceModel.getTitle(), result.getTitle());
//    }
}
