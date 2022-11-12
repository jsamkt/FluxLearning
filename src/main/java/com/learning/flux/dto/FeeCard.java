package com.learning.flux.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class FeeCard extends Card{

    private BigDecimal feeValue;


    public FeeCard() {
    }

    public FeeCard(String userId, BigDecimal longitude, BigDecimal latitude, Long currentDate, BigDecimal feeValue) {
        super(userId, longitude, latitude, currentDate, "FEE");
        this.feeValue = feeValue;
    }

    public BigDecimal getFeeValue() {
        return feeValue;
    }

    public void setFeeValue(BigDecimal feeValue) {
        this.feeValue = feeValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FeeCard feeCard = (FeeCard) o;
        return Objects.equals(feeValue, feeCard.feeValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), feeValue);
    }

    @Override
    public String toString() {
        return "FeeCard{" +
                "feeValue=" + feeValue +
                '}';
    }
}
