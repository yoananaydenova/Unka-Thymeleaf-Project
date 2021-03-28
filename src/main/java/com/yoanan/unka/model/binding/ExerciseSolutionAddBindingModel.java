package com.yoanan.unka.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ExerciseSolutionAddBindingModel {


    private Long lessonId;
    private String title;
    private String description;
    private String text;

    private Long debitChartId;
    private Long creditChartId;
    private Double debitValue;
    private Double creditValue;

    public ExerciseSolutionAddBindingModel() {
    }

    @NotNull(message = "Избора на урок е задължителен!")
    @Min(value = 1, message = "Избора на урок е задължителен!")
    public Long getLessonId() {
        return lessonId;
    }

    public ExerciseSolutionAddBindingModel setLessonId(Long lessonId) {
        this.lessonId = lessonId;
        return this;
    }

    @NotNull(message = "Заглавието е задължително")
    @Length(min = 5, message = "Заглавието трябва да е минимум от 5 символа")
    public String getTitle() {
        return title;
    }

    public ExerciseSolutionAddBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    @Length(max = 100, message = "Изисквинията за изпълнение на задачата са максимум до 100 символа")
    public String getDescription() {
        return description;
    }

    public ExerciseSolutionAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    @NotNull(message = "Условието е задължително")
    @Length(min = 10, message = "Условието трябва да е минимум от 10 символа")
    public String getText() {
        return text;
    }

    public ExerciseSolutionAddBindingModel setText(String text) {
        this.text = text;
        return this;
    }

    @NotNull(message = "Въвеждането на сметка е зъдължително")
    public Long getDebitChartId() {
        return debitChartId;
    }

    public ExerciseSolutionAddBindingModel setDebitChartId(Long debitChartId) {
        this.debitChartId = debitChartId;
        return this;
    }

    @NotNull(message = "Въвеждането на сметка е зъдължително")
    public Long getCreditChartId() {
        return creditChartId;
    }

    public ExerciseSolutionAddBindingModel setCreditChartId(Long creditChartId) {
        this.creditChartId = creditChartId;
        return this;
    }

    @NotNull(message = "Въвеждането на сума е зъдължително")
    //@Digits(integer=10, fraction=2, message = "Валидни са стойности с 10 позиции преди запетаята и 2 след.")
    public Double getDebitValue() {
        return debitValue;
    }

    public ExerciseSolutionAddBindingModel setDebitValue(Double debitValue) {
        this.debitValue = debitValue;
        return this;
    }


    @NotNull(message = "Въвеждането на сума е зъдължително")
  //  @Digits(integer=10, fraction=2, message = "Валидни са стойности с 10 позиции преди запетаята и 2 след.")
    public Double getCreditValue() {
        return creditValue;
    }

    public ExerciseSolutionAddBindingModel setCreditValue(Double creditValue) {
        this.creditValue = creditValue;
        return this;
    }
}
