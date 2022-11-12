package com.learning.flux.service.impl;

import com.learning.flux.client.BaseCardClient;
import com.learning.flux.dto.RegularCard;
import com.learning.flux.service.CardClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@Service
public class RegularCardClient implements CardClient<RegularCard> {
    private final BaseCardClient client;

    public RegularCardClient(@Value("${hub.regularUrl}") String url) {
        this.client = new BaseCardClient(url);
    }


    @Override
    public Flux<RegularCard> getCards(String userId, BigDecimal latitude, BigDecimal longitude, Long currentDate) {
        return client.<RegularCard>getCards(
                userId,
                latitude,
                longitude,
                currentDate,
                MediaType.APPLICATION_JSON,
                RegularCard.class
        );
    }
}
