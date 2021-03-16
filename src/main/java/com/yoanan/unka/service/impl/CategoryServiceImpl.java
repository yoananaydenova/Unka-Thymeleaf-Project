package com.yoanan.unka.service.impl;

import com.yoanan.unka.model.entity.CategoryEntity;
import com.yoanan.unka.model.service.CategoryServiceModel;
import com.yoanan.unka.repository.CategoryRepository;
import com.yoanan.unka.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategory() {

        if (categoryRepository.count() == 0) {

            CategoryEntity other = new CategoryEntity("Друго", "Дисциплини, които не са включени в изброените");
            CategoryEntity economic = new CategoryEntity("Икономика", "Икономически дисциплини");
            CategoryEntity accounting = new CategoryEntity("Счетоводство", "Счетоводство и контрол");
            CategoryEntity law = new CategoryEntity("Право", "Право");

            categoryRepository.saveAll(List.of(other, economic, accounting, law));
        }

    }

    @Override
    public List<CategoryServiceModel> findAllCategories() {
        return categoryRepository
                .findAllByOrderByIdDesc()
                .stream()
                .map(csm -> modelMapper.map(csm, CategoryServiceModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public String findById(Long categoryId) {
        return categoryRepository
                .findById(categoryId)
                .orElseThrow(() ->
                        new IllegalStateException("Category not found! Please seed the category!"))
                .getName();
    }
}
