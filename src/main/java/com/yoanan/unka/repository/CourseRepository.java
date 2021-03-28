package com.yoanan.unka.repository;

import com.yoanan.unka.model.entity.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    boolean existsByNameAndTeacher_Username(String courseName, String teacherUsername);

    boolean existsByIdAndCategories_Name(Long courseId, String categoryName);

    boolean existsByIdAndTeacher_Username(Long courseId, String username);

    List<CourseEntity> findAllByTeacher_Username(String username);

    Page<CourseEntity> findAllByTeacher_Username(String username, Pageable pageable);

    Page<CourseEntity> findAllByCategories_Id(Long categoryId, Pageable pageable);

    Page<CourseEntity> findAllByTeacher_UsernameAndCategories_Id(String username, Long categoryId, Pageable pageable);


}
