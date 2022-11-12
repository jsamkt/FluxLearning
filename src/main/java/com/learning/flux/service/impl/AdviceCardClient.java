package com.learning.flux.service.impl;

import com.learning.flux.client.BaseCardClient;
import com.learning.flux.dto.AdviceCard;
import com.learning.flux.service.CardClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AdviceCardClient implements CardClient<AdviceCard> {
    private final BaseCardClient client;

    public AdviceCardClient(@Value("${hub.adviceUrl}") String url) {
        this.client = new BaseCardClient(url);
    }


    @Override
    public Flux<AdviceCard> getCards(String userId, BigDecimal latitude, BigDecimal longitude, Long currentDate) {
        return client.<AdviceCard>getCards(
                userId,
                latitude,
                longitude,
                currentDate,
                MediaType.APPLICATION_JSON,
                AdviceCard.class
        );
    }
}
