package com.learning.flux.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class RegularCard extends Card{

    private Map<String, Long> regularValues;

    public RegularCard() {
    }

    public RegularCard(String userId, BigDecimal longitude, BigDecimal latitude, Long currentDate, Map<String, Long> regularValues) {
        super(userId, longitude, latitude, currentDate, "REGULAR");
        this.regularValues = regularValues;
    }

    public Map<String, Long> getRegularValues() {
        return regularValues;
    }

    public void setRegularValues(Map<String, Long> regularValues) {
        this.regularValues = regularValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RegularCard that = (RegularCard) o;
        return Objects.equals(regularValues, that.regularValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), regularValues);
    }

    @Override
    public String toString() {
        return "RegularCard{" +
                "regularValues=" + regularValues +
                '}';
    }
}
