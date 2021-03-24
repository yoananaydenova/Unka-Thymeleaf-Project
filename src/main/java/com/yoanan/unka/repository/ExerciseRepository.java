package com.yoanan.unka.repository;

import com.yoanan.unka.model.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {

    List<ExerciseEntity>findAllByLesson_Id(Long lessonId);

    boolean existsByTitleAndLesson_Id(String title, Long lessonId);
}
