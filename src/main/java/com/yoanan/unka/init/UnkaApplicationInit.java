package com.yoanan.unka.init;

import com.yoanan.unka.service.CategoryService;
import com.yoanan.unka.service.ChartService;
import com.yoanan.unka.service.UserRoleService;
import com.yoanan.unka.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UnkaApplicationInit implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ChartService chartService;

    public UnkaApplicationInit(UserRoleService userRoleService, UserService userService, CategoryService categoryService, ChartService chartService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.chartService = chartService;
    }

    @Override
    public void run(String... args) throws Exception {
        userRoleService.seedRoles();
//        userService.seedUsers();
        categoryService.seedCategories();
        chartService.seedChars();
    }
}
