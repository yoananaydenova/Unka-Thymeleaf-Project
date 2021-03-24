package com.yoanan.unka.model.binding;

import com.yoanan.unka.model.entity.LessonEntity;

import java.math.BigDecimal;

public class ExerciseSolutionAddBindingModel {

    private Long lessonId;
    private String title;
    private String description;
    private String text;

    private Integer debitNumber;
    private Integer creditNumber;
    private BigDecimal debitValue;
    private BigDecimal creditValue;

    public ExerciseSolutionAddBindingModel() {
    }

    public Long getLessonId() {
        return lessonId;
    }

    public ExerciseSolutionAddBindingModel setLessonId(Long lessonId) {
        this.lessonId = lessonId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ExerciseSolutionAddBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ExerciseSolutionAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getText() {
        return text;
    }

    public ExerciseSolutionAddBindingModel setText(String text) {
        this.text = text;
        return this;
    }

    public Integer getDebitNumber() {
        return debitNumber;
    }

    public ExerciseSolutionAddBindingModel setDebitNumber(Integer debitNumber) {
        this.debitNumber = debitNumber;
        return this;
    }

    public Integer getCreditNumber() {
        return creditNumber;
    }

    public ExerciseSolutionAddBindingModel setCreditNumber(Integer creditNumber) {
        this.creditNumber = creditNumber;
        return this;
    }

    public BigDecimal getDebitValue() {
        return debitValue;
    }

    public ExerciseSolutionAddBindingModel setDebitValue(BigDecimal debitValue) {
        this.debitValue = debitValue;
        return this;
    }

    public BigDecimal getCreditValue() {
        return creditValue;
    }

    public ExerciseSolutionAddBindingModel setCreditValue(BigDecimal creditValue) {
        this.creditValue = creditValue;
        return this;
    }
}
