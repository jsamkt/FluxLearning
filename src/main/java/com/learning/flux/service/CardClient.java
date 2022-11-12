package com.learning.flux.service;

import com.learning.flux.dto.Card;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

public interface CardClient<T extends Card> {

    Flux<T> getCards(String userId, BigDecimal latitude, BigDecimal longitude, Long currentDate);
}
