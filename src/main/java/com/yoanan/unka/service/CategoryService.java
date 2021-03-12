package com.yoanan.unka.service;

import com.yoanan.unka.model.service.CategoryServiceModel;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategory();

    List<CategoryServiceModel> findAll();


}
