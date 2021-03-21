package com.yoanan.unka.repository;

import com.yoanan.unka.model.entity.CourseEntity;
import com.yoanan.unka.model.entity.ShoppingCartEntity;
import com.yoanan.unka.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {

   Optional <ShoppingCartEntity> findByStudent_Username(String username);

   Optional<ShoppingCartEntity> findShoppingCartEntityByStudent_UsernameAndCoursesInCart_Id(String username,Long id);
}
