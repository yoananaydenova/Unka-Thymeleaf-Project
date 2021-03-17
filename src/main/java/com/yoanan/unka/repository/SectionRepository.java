package com.yoanan.unka.repository;

import com.yoanan.unka.model.entity.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<SectionEntity, Long> {

    SectionEntity findByNumber(int number);
}
