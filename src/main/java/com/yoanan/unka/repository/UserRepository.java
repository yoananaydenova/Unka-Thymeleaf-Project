package com.yoanan.unka.repository;

import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    boolean existsUserEntityByUsernameAndRoles_Role(String username, UserRole roleName);

    boolean existsUserEntityById(Long userId);


}
