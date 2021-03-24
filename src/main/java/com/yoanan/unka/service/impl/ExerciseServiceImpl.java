package com.yoanan.unka.service.impl;

import com.yoanan.unka.model.entity.ExerciseEntity;
import com.yoanan.unka.model.entity.LessonEntity;
import com.yoanan.unka.model.service.ExerciseServiceModel;
import com.yoanan.unka.repository.ExerciseRepository;
import com.yoanan.unka.service.ExerciseService;
import com.yoanan.unka.service.LessonService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final LessonService lessonService;
    private final ModelMapper modelMapper;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, LessonService lessonService, ModelMapper modelMapper) {
        this.exerciseRepository = exerciseRepository;
        this.lessonService = lessonService;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<ExerciseServiceModel> findAllExercisesByLessonId(Long id) {

        return exerciseRepository
                .findAllByLesson_Id(id)
                .stream()
                .map(exercise -> modelMapper.map(exercise, ExerciseServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isUniqueExerciseWithNameAndLessonId(String title, Long lessonId) {
        return exerciseRepository.existsByTitleAndLesson_Id(title, lessonId);
    }

    @Override
    public void addExercise(ExerciseServiceModel exerciseServiceModel) {
        ExerciseEntity exerciseEntity = modelMapper.map(exerciseServiceModel, ExerciseEntity.class);
        LessonEntity lessonEntityById = lessonService.findLessonEntityById(exerciseServiceModel.getLesson().getId());
        exerciseEntity.setLesson(lessonEntityById);
        System.out.println();
        exerciseEntity.setId(null);
        exerciseRepository.save(exerciseEntity);
    }
}
