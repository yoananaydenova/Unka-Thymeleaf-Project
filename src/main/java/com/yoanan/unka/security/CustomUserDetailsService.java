package com.yoanan.unka.security;

import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.repository.UserRepository;
import com.yoanan.unka.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name "
                        + username + " was not found!"));

        return mapToUserDetails(userEntity);
    }

    private UserDetails mapToUserDetails(UserEntity userEntity) {

        List<SimpleGrantedAuthority> authorities = userEntity
                .getRoles()
                .stream()
                .map(ur -> new SimpleGrantedAuthority("ROLE_" + ur.getRole().name()))
                .collect(Collectors.toList());


        User result = new User(
                userEntity.getName(),
                userEntity.getPassword(),
                authorities
        );
        return result;
    }
}
