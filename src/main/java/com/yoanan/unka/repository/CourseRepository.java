package com.yoanan.unka.repository;

import com.yoanan.unka.model.entity.CourseEntity;
import com.yoanan.unka.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

   // boolean existsByNameAndTeacher(String courseName, UserEntity teacher);
    boolean existsByNameAndTeacher_Username(String courseName, String teacherUsername);

    List<CourseEntity> findAllByTeacher_Username(String username);

    Page<CourseEntity> findAllByTeacher_Username(String username, Pageable pageable);
}
