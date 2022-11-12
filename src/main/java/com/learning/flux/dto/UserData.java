package com.learning.flux.dto;

import java.math.BigDecimal;

public record UserData(String userId, BigDecimal longitude, BigDecimal latitude, Long currentDate) {}
