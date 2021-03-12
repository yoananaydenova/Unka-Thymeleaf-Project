package com.yoanan.unka.model.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Set;


public class CourseAddBindingModel {

    private String name;
    private BigDecimal price;
    private String description;
    private MultipartFile img;
    private Set<String> categories;

    public CourseAddBindingModel() {
    }

    @NotEmpty
    @Length(min = 2, message = "Name of the course must be minimum 2 characters!")
    public String getName() {
        return name;
    }

    public CourseAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @NotEmpty
    @DecimalMin(value = "0", message = "The price must be positive number!")
    public BigDecimal getPrice() {
        return price;
    }

    public CourseAddBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @NotEmpty
    @Length(min = 2, message = "Description of the course must be minimum 2 characters!")
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
