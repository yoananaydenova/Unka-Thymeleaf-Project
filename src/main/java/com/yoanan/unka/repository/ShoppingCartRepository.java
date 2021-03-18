package com.yoanan.unka.repository;

import com.yoanan.unka.model.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {

   Optional <ShoppingCartEntity> findByStudent_Username(String username);
}
