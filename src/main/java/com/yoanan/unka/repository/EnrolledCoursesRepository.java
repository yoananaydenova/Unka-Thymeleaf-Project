package com.yoanan.unka.repository;

import com.yoanan.unka.model.entity.EnrolledCoursesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnrolledCoursesRepository extends JpaRepository<EnrolledCoursesEntity, Long> {

    Optional<EnrolledCoursesEntity> findByStudent_Username(String username);

    Optional<EnrolledCoursesEntity> findByStudent_UsernameAndMyEnrolledCourses_Id(String username, Long id);
}
