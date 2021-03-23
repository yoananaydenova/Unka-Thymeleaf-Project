package com.yoanan.unka.repository;

import com.yoanan.unka.model.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {

    boolean existsByTitleAndCourse_Id(String title, Long courseId);

    List<LessonEntity> findAllByCourse_Id(Long id);

}
