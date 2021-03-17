package com.yoanan.unka.service;

import com.yoanan.unka.model.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {

    void seedCategories();

    List<CategoryServiceModel> findAllCategories();

    String findById(Long categoryId);
}
