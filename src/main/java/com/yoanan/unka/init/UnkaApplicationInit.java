package com.yoanan.unka.init;

import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.entity.UserRoleEntity;
import com.yoanan.unka.model.entity.enums.UserRole;
import com.yoanan.unka.repository.UserRepository;
import com.yoanan.unka.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnkaApplicationInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UnkaApplicationInit(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        UserRoleEntity userRole = userRoleRepository.save(new UserRoleEntity().setRole(UserRole.USER));
        UserRoleEntity adminRole = userRoleRepository.save(new UserRoleEntity().setRole(UserRole.ADMIN));

        // Save simple user
        UserEntity userEntity = new UserEntity();
        userEntity.setName("user");
        userEntity.setPassword(passwordEncoder.encode("123"));
        userEntity.setRoles(List.of(userRole));

        UserEntity admin = new UserEntity();
        admin.setName("admin");
        admin.setPassword(passwordEncoder.encode("123"));
        // Admin has 2 roles
        admin.setRoles(List.of(adminRole, userRole));

        userRepository.save(userEntity);
        userRepository.save(admin);
    }
}
