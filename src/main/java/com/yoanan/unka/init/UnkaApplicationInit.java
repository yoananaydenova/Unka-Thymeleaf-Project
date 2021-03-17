package com.yoanan.unka.init;

import com.yoanan.unka.service.CategoryService;
import com.yoanan.unka.service.ChartService;
import com.yoanan.unka.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UnkaApplicationInit implements CommandLineRunner {

    private final UserService userService;
    private final CategoryService categoryService;
    private final ChartService chartService;

    public UnkaApplicationInit(UserService userService, CategoryService categoryService, ChartService chartService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.chartService = chartService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.seedUsers();
        categoryService.seedCategories();
        chartService.seedChars();
    }
}
