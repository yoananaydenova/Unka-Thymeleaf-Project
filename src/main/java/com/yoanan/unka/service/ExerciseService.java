package com.yoanan.unka.service;

import com.yoanan.unka.model.service.ExerciseServiceModel;

import java.util.List;

public interface ExerciseService {

    List<ExerciseServiceModel> findAllExercisesByLessonId(Long id);

    boolean isUniqueExerciseWithNameAndLessonId(String title, Long lessonId);

    ExerciseServiceModel addExercise(ExerciseServiceModel exerciseServiceModel);

    ExerciseServiceModel findExerciseById(Long id);
}
