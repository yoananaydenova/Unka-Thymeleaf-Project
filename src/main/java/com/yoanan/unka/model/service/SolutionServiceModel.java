package com.yoanan.unka.model.service;

import java.math.BigDecimal;

public class SolutionServiceModel extends BaseServiceModel{

    private ExerciseServiceModel exercise;
    private Long debitChartId;
    private Long creditChartId;
    private Double debitValue;
    private Double creditValue;

    public SolutionServiceModel() {
    }

    public ExerciseServiceModel getExercise() {
        return exercise;
    }

    public SolutionServiceModel setExercise(ExerciseServiceModel exercise) {
        this.exercise = exercise;
        return this;
    }

    public Long getDebitChartId() {
        return debitChartId;
    }

    public SolutionServiceModel setDebitChartId(Long debitChartId) {
        this.debitChartId = debitChartId;
        return this;
    }

    public Long getCreditChartId() {
        return creditChartId;
    }

    public SolutionServiceModel setCreditChartId(Long creditChartId) {
        this.creditChartId = creditChartId;
        return this;
    }

    public Double getDebitValue() {
        return debitValue;
    }

    public SolutionServiceModel setDebitValue(Double debitValue) {
        this.debitValue = debitValue;
        return this;
    }

    public Double getCreditValue() {
        return creditValue;
    }

    public SolutionServiceModel setCreditValue(Double creditValue) {
        this.creditValue = creditValue;
        return this;
    }
}
