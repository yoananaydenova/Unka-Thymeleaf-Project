package com.yoanan.unka.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "solutions")
public class SolutionEntity extends BaseEntity {

    private  ExerciseEntity exercise;
    private int debitNumber;
    private int creditNumber;
    private BigDecimal debitValue;
    private BigDecimal creditValue;

    public SolutionEntity() {
    }

    @ManyToOne
    public ExerciseEntity getExercise() {
        return exercise;
    }

    public SolutionEntity setExercise(ExerciseEntity exercise) {
        this.exercise = exercise;
        return this;
    }

    public int getDebitNumber() {
        return debitNumber;
    }

    public SolutionEntity setDebitNumber(int debitNumber) {
        this.debitNumber = debitNumber;
        return this;
    }

    public int getCreditNumber() {
        return creditNumber;
    }

    public SolutionEntity setCreditNumber(int creditNumber) {
        this.creditNumber = creditNumber;
        return this;
    }

    public BigDecimal getDebitValue() {
        return debitValue;
    }

    public SolutionEntity setDebitValue(BigDecimal debitValue) {
        this.debitValue = debitValue;
        return this;
    }

    public BigDecimal getCreditValue() {
        return creditValue;
    }

    public SolutionEntity setCreditValue(BigDecimal creditValue) {
        this.creditValue = creditValue;
        return this;
    }
}
