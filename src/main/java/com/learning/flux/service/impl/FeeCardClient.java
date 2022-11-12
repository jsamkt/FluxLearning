package com.learning.flux.service.impl;

import com.learning.flux.client.BaseCardClient;
import com.learning.flux.dto.FeeCard;
import com.learning.flux.service.CardClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@Service
public class FeeCardClient implements CardClient<FeeCard> {
    private final BaseCardClient client;

    public FeeCardClient(@Value("${hub.feeUrl}") String url) {
        this.client = new BaseCardClient(url);
    }


    @Override
    public Flux<FeeCard> getCards(String userId, BigDecimal latitude, BigDecimal longitude, Long currentDate) {
        return client.getCards(
                userId,
                latitude,
                longitude,
                currentDate,
                MediaType.APPLICATION_JSON,
                FeeCard.class
        );
    }
}
