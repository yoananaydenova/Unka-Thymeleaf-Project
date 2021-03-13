package com.yoanan.unka.model.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


public class CourseAddBindingModel {

    private String name;
    private BigDecimal price;
    private String description;
    private MultipartFile img;
    private Set<String> categories = new HashSet<>();

    public CourseAddBindingModel() {
    }

    @NotEmpty
    @Length(min = 5, max=30, message = "Името на курса трябва да бъде от 5 до 30 символа!")
    public String getName() {
        return name;
    }

    public CourseAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @NotNull(message = "Трябва да въведете цена на курса! Ако искате да е безплатен - въведете нула.")
    @DecimalMin(value = "0", message = "Цената трябва да е положително число!")
    public BigDecimal getPrice() {
        return price;
    }

    public CourseAddBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Length(max = 500, message = "Описанието на курса трябва да е до 500 символа!")
    public String getDescription() {
        return description;
    }

    public CourseAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getImg() {
        return img;
    }

    public CourseAddBindingModel setImg(MultipartFile img) {
        this.img = img;
        return this;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public CourseAddBindingModel setCategories(Set<String> categories) {
        this.categories = categories;
        return this;
    }
}
