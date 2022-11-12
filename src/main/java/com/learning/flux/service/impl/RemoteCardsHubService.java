package com.learning.flux.service.impl;

import com.learning.flux.dto.Card;
import com.learning.flux.dto.UserData;
import com.learning.flux.service.CardClient;
import com.learning.flux.service.CardService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class RemoteCardsHubService implements CardService {

    private final List<CardClient> cardClients;

    public RemoteCardsHubService(List<CardClient> cardClients) {
        this.cardClients = cardClients;
    }

    @Override
    public Flux<Card> getCardList(UserData userData) {
        return Flux.just(cardClients.toArray(new CardClient[0]))
                .flatMap(client -> getCards(client, userData));
    }

    private Flux<Card> getCards(CardClient client, UserData userData) {
        return client.getCards(
                userData.userId(),
                userData.latitude(),
                userData.longitude(),
                userData.currentDate()
        );
    }
}
