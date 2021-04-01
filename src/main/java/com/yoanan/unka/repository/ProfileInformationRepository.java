package com.yoanan.unka.repository;

import com.yoanan.unka.model.entity.ProfileInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileInformationRepository extends JpaRepository<ProfileInformationEntity, Long> {

    Optional<ProfileInformationEntity>findByUser_Username(String username);

    Optional<ProfileInformationEntity>findByUser_Id(Long userId);

//    boolean existsByUser_Username(String username);
}
