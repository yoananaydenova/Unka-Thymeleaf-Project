package com.yoanan.unka.web;

import com.yoanan.unka.model.service.BaseServiceModel;
import com.yoanan.unka.model.view.UserViewRestModel;
import com.yoanan.unka.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/users")
@RestController
public class UserRestController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserRestController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api")
    public ResponseEntity<List<UserViewRestModel>> findAll() {

        List<UserViewRestModel> usersList = userService
                .findAll()
                .stream()
                .map(user -> {

                    UserViewRestModel userViewRestModel = modelMapper.map(user, UserViewRestModel.class);

                    List<Long> rolesId = user
                            .getRoles()
                            .stream()
                            .map(BaseServiceModel::getId)
                            .collect(Collectors.toList());

                    userViewRestModel.setRoleId(rolesId);
                    return userViewRestModel;
                })
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(usersList);
    }



}
