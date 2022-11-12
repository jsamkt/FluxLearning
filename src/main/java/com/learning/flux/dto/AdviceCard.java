package com.learning.flux.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class AdviceCard extends Card{

    private BigDecimal adviceValue;


    public AdviceCard() {
    }

    public AdviceCard(String userId, BigDecimal longitude, BigDecimal latitude, Long currentDate, BigDecimal adviceValue) {
        super(userId, longitude, latitude, currentDate, "ADVICE");
        this.adviceValue = adviceValue;
    }

    public BigDecimal getAdviceValue() {
        return adviceValue;
    }

    public void setAdviceValue(BigDecimal adviceValue) {
        this.adviceValue = adviceValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AdviceCard that = (AdviceCard) o;
        return Objects.equals(adviceValue, that.adviceValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), adviceValue);
    }

    @Override
    public String toString() {
        return "AdviceCard{" +
                "adviceValue=" + adviceValue +
                '}'
                + "extends " + super.toString();
    }
}
