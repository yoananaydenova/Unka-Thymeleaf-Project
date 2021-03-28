package com.yoanan.unka.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "solutions")
public class SolutionEntity extends BaseEntity {

    private  ExerciseEntity exercise;
    private Long debitChartId;
    private Long creditChartId;
    private Double debitValue;
    private Double creditValue;

    public SolutionEntity() {
    }

    @OneToOne
    public ExerciseEntity getExercise() {
        return exercise;
    }

    public SolutionEntity setExercise(ExerciseEntity exercise) {
        this.exercise = exercise;
        return this;
    }

    @Column(name = "debit_chart_id", nullable = false)
    public Long getDebitChartId() {
        return debitChartId;
    }

    public SolutionEntity setDebitChartId(Long debitChartId) {
        this.debitChartId = debitChartId;
        return this;
    }

    @Column(name = "credit_chart_id", nullable = false)
    public Long getCreditChartId() {
        return creditChartId;
    }

    public SolutionEntity setCreditChartId(Long creditChartId) {
        this.creditChartId = creditChartId;
        return this;
    }


    @Column(name = "debit_value", nullable = false)
    public Double getDebitValue() {
        return debitValue;
    }

    public SolutionEntity setDebitValue(Double debitValue) {
        this.debitValue = debitValue;
        return this;
    }

    @Column(name = "credit_value", nullable = false)
    public Double getCreditValue() {
        return creditValue;
    }

    public SolutionEntity setCreditValue(Double creditValue) {
        this.creditValue = creditValue;
        return this;
    }
}
