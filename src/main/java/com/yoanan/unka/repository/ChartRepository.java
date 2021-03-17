package com.yoanan.unka.repository;

import com.yoanan.unka.model.entity.ChartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartRepository extends JpaRepository<ChartEntity, Long> {
}
