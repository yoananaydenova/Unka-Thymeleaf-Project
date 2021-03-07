package com.yoanan.unka.init;

import com.yoanan.unka.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UnkaApplicationInit implements CommandLineRunner {

    private final UserService userService;

    public UnkaApplicationInit(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.seedUsers();
    }
}
