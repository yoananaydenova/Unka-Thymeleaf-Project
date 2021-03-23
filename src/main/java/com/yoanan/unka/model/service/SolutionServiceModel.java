package com.yoanan.unka.model.service;

import java.math.BigDecimal;

public class SolutionServiceModel extends BaseServiceModel{

    private int debitNumber;
    private int creditNumber;
    private BigDecimal debitValue;
    private BigDecimal creditValue;

    public SolutionServiceModel() {
    }

    public int getDebitNumber() {
        return debitNumber;
    }

    public SolutionServiceModel setDebitNumber(int debitNumber) {
        this.debitNumber = debitNumber;
        return this;
    }

    public int getCreditNumber() {
        return creditNumber;
    }

    public SolutionServiceModel setCreditNumber(int creditNumber) {
        this.creditNumber = creditNumber;
        return this;
    }

    public BigDecimal getDebitValue() {
        return debitValue;
    }

    public SolutionServiceModel setDebitValue(BigDecimal debitValue) {
        this.debitValue = debitValue;
        return this;
    }

    public BigDecimal getCreditValue() {
        return creditValue;
    }

    public SolutionServiceModel setCreditValue(BigDecimal creditValue) {
        this.creditValue = creditValue;
        return this;
    }
}
