package com.yoanan.unka.model.binding;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SolutionAddBindingModel {

    private Long exerciseId;

    private Long debitChartId;
    private Long creditChartId;
    private Double debitValue;
    private Double creditValue;

    public SolutionAddBindingModel() {
    }

    @NotNull
    public Long getExerciseId() {
        return exerciseId;
    }

    public SolutionAddBindingModel setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
        return this;
    }

    @NotNull(message = "Въвеждането на сметка е зъдължително")
    public Long getDebitChartId() {
        return debitChartId;
    }

    public SolutionAddBindingModel setDebitChartId(Long debitChartId) {
        this.debitChartId = debitChartId;
        return this;
    }

    @NotNull(message = "Въвеждането на сметка е зъдължително")
    public Long getCreditChartId() {
        return creditChartId;
    }

    public SolutionAddBindingModel setCreditChartId(Long creditChartId) {
        this.creditChartId = creditChartId;
        return this;
    }

    @NotNull(message = "Въвеждането на сума е зъдължително")
    // @Digits(integer=10, fraction=2, message = "Валидни са стойности с 10 позиции преди запетаята и 2 след.")
    public Double getDebitValue() {
        return debitValue;
    }

    public SolutionAddBindingModel setDebitValue(Double debitValue) {
        this.debitValue = debitValue;
        return this;
    }



    @NotNull(message = "Въвеждането на сума е зъдължително")
  //  @Digits(integer=10, fraction=2, message = "Валидни са стойности с 10 позиции преди запетаята и 2 след.")
    public Double getCreditValue() {
        return creditValue;
    }

    public SolutionAddBindingModel setCreditValue(Double creditValue) {
        this.creditValue = creditValue;
        return this;
    }
}
