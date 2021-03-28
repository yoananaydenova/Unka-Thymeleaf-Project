package com.yoanan.unka.repository;

import com.yoanan.unka.model.entity.SolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SolutionRepository extends JpaRepository<SolutionEntity, Long> {

    Optional<SolutionEntity> findByExercise_Id(Long id);
}
