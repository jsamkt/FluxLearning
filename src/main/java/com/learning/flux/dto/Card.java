package com.learning.flux.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Card {

    private String userId;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private Long currentDate;
    private String name;

    public Card() {
    }

    public Card(String userId, BigDecimal longitude, BigDecimal latitude, Long currentDate, String name) {
        this.userId = userId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.currentDate = currentDate;
        this.name = name;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Long currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(userId, card.userId) && Objects.equals(longitude, card.longitude) && Objects.equals(latitude, card.latitude) && Objects.equals(currentDate, card.currentDate) && Objects.equals(name, card.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, longitude, latitude, currentDate, name);
    }

    @Override
    public String toString() {
        return "Card{" +
                "userId='" + userId + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", currentDate=" + currentDate +
                ", name='" + name + '\'' +
                '}';
    }
}