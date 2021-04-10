package com.yoanan.unka.service.impl;

import com.yoanan.unka.config.IAuthenticationFacade;
import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.entity.UserRoleEntity;
import com.yoanan.unka.model.entity.enums.UserRole;
import com.yoanan.unka.model.service.UserRegisterServiceModel;
import com.yoanan.unka.model.service.UserServiceModel;
import com.yoanan.unka.repository.UserRepository;
import com.yoanan.unka.service.UserRoleService;
import com.yoanan.unka.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UnkaUserDetailsService unkaUserDetailsService;
    private final IAuthenticationFacade authenticationFacade;


    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, PasswordEncoder passwordEncoder, ModelMapper modelMapper, UnkaUserDetailsService unkaUserDetailsService, IAuthenticationFacade authenticationFacade) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.unkaUserDetailsService = unkaUserDetailsService;
        this.authenticationFacade = authenticationFacade;
    }


    @Override
    public void seedUsers() {

//        if (userRepository.count() == 0) {
//            UserRoleEntity studentRole = userRoleRepository.save(new UserRoleEntity().setRole(UserRole.STUDENT));
//            UserRoleEntity teacherRole = userRoleRepository.save(new UserRoleEntity().setRole(UserRole.TEACHER));
//            UserRoleEntity adminRole = userRoleRepository.save(new UserRoleEntity().setRole(UserRole.ADMIN));
//            UserRoleEntity rootAdminRole = userRoleRepository.save(new UserRoleEntity().setRole(UserRole.ROOT_ADMIN));
//
//
//            // Student
//            UserEntity student = new UserEntity()
//                    .setUsername("student")
//                    .setFullName("First Student")
//                    .setPassword(passwordEncoder.encode("123456"))
//                    .setIncomeTeaching(BigDecimal.ZERO)
//                    .setRoles(List.of(studentRole));
//
//            UserEntity teacher = new UserEntity()
//                    .setUsername("teacher")
//                    .setFullName("First Teacher")
//                    .setIncomeTeaching(BigDecimal.ZERO)
//                    .setPassword(passwordEncoder.encode("123456"));
//            // Teacher has 2 roles
//            teacher.setRoles(List.of(teacherRole, studentRole));
//
//            UserEntity admin = new UserEntity()
//                    .setUsername("admina")
//                    .setFullName("Admin Adminov")
//                    .setIncomeTeaching(BigDecimal.ZERO)
//                    .setPassword(passwordEncoder.encode("123456"));
//
//            // Admin has 4 roles
//            admin.setRoles(List.of(rootAdminRole, adminRole, teacherRole, studentRole));
//
//            userRepository.saveAll(List.of(student, teacher, admin));
//        }
    }

    @Override
    public UserServiceModel registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel) {

        UserEntity newUser = modelMapper.map(userRegisterServiceModel, UserEntity.class);

        // Save user in DB
        newUser.setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()));

        //register first user as root admin or student
        List<UserRoleEntity> roles = getUserRoles();
        newUser.setRoles(roles);

        newUser = userRepository.save(newUser);
        // Login user

        UserDetails principal = unkaUserDetailsService.loadUserByUsername(newUser.getUsername());

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        principal,
                        newUser.getPassword(),
                        principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return modelMapper.map(newUser, UserServiceModel.class);
    }

    private List<UserRoleEntity> getUserRoles() {

        if (userRepository.count() == 0) {
            return userRoleService.findAllRoles()
                    .stream()
                    .map(role -> modelMapper.map(role, UserRoleEntity.class))
                    .collect(Collectors.toList());

        } else {
           return List.of(modelMapper.map(userRoleService.findByRole(UserRole.STUDENT), UserRoleEntity.class));
        }

    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }


    @Override
    public void addRole(String username, UserRole role) {

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User with that username" + username + " not found!"));

        UserRoleEntity userRoleEntity =
                modelMapper.map(userRoleService.findByRole(UserRole.TEACHER), UserRoleEntity.class);


        UserDetails principal = unkaUserDetailsService.loadUserByUsername(username);

        //Check user don`t have role
        boolean existRole = principal.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));

        //Add role to user
        if (principal != null && !existRole) {
            userEntity.addRole(userRoleEntity);
            userRepository.save(userEntity);
        }
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException("User with username " + username + " not found!"));
    }

    @Override
    public List<UserServiceModel> findAll() {

        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean currentUserIsTeacher() {
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();
        return userRepository.existsUserEntityByUsernameAndRoles_Role(username, UserRole.TEACHER);
    }

    @Override
    public boolean currentUserIsAdmin() {
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();
        return userRepository.existsUserEntityByUsernameAndRoles_Role(username, UserRole.ADMIN);
    }


    @Override
    public UserServiceModel findUserById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id " + userId + " does not exist!!!"));

        return modelMapper.map(userEntity, UserServiceModel.class);
    }

    @Override
    public boolean findUserByIdIsLogedUser(Long userId) {
        UserEntity userEntityById = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id " + userId + " does not exist!!!"));

        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        UserEntity userEntityFromDB = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User with that username" + username + " not found!"));

        return userEntityById.getUsername().equals(userEntityFromDB.getUsername());
    }

    @Override
    public UserServiceModel saveChangeFullName(UserServiceModel userServiceModel) {
        String fullName = userServiceModel.getFullName();
        if (fullName != null && !fullName.trim().isEmpty()) {
            UserEntity userEntity = userRepository.findById(userServiceModel.getId())
                    .orElseThrow(() -> new IllegalStateException("User with id " + userServiceModel.getId() + " does not exist!!!"));

            userEntity.setFullName(fullName);
            UserEntity savedUser = userRepository.save(userEntity);
           return modelMapper.map(savedUser, userServiceModel.getClass());
        }
        return userServiceModel;
    }

    @Override
    public UserServiceModel changeRoleOfUser(Long userId, Long newRoleId) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id " + userId + " does not exist!!!"));

        List<UserRoleEntity> roles = userEntity.getRoles();

        UserRoleEntity newUserRoleEntity = modelMapper.map(userRoleService.findById(newRoleId), UserRoleEntity.class);

        //Check user have role
        boolean existNewRoleForUser = roles.stream()
                .anyMatch(a -> a.getId().equals(newRoleId));

        boolean isNewRoleTeacher = newUserRoleEntity.getRole().equals(UserRole.TEACHER);
        boolean isNewRoleAdmin = newUserRoleEntity.getRole().equals(UserRole.ADMIN);


        //Add role to user
        if (!existNewRoleForUser) {
            UserRoleEntity userRoleTEACHER = modelMapper.map(userRoleService.findByRole(UserRole.TEACHER), UserRoleEntity.class);

            if (isNewRoleTeacher) {
                userEntity.addRole(userRoleTEACHER);

            } else if (isNewRoleAdmin) {
                UserRoleEntity userRoleADMIN = modelMapper.map(userRoleService.findByRole(UserRole.ADMIN), UserRoleEntity.class);
                // User is Student
                if (roles.size() == 1) {

                    userEntity.addRole(userRoleTEACHER);
                    userEntity.addRole(userRoleADMIN);

                    //User is teacher
                } else if (roles.size() == 2) {
                    userEntity.addRole(userRoleADMIN);
                }

            }

            //Remove role Admin if user has not root_admin-> user will be only teacher
        } else {

            boolean userIsRootAdmin = userRepository.existsUserEntityByUsernameAndRoles_Role(userEntity.getUsername(), UserRole.ROOT_ADMIN);

            if (isNewRoleTeacher && roles.size() == 3 && !userIsRootAdmin) {
                UserRoleEntity userRoleAdminToRemove = userEntity.getRoles()
                        .stream()
                        .filter(r -> r.getRole().equals(UserRole.ADMIN))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("User role ADMIN not found!"));
                userEntity.getRoles().remove(userRoleAdminToRemove);
            }
        }

        return modelMapper.map(userRepository.save(userEntity), UserServiceModel.class);
    }

    @Override
    public String findFullName() {
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User with that username" + username + " not found!"));

        return userEntity.getFullName();

    }


}
