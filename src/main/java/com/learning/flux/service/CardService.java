package com.learning.flux.service;

import com.learning.flux.dto.Card;
import com.learning.flux.dto.UserData;
import reactor.core.publisher.Flux;

public interface CardService {

    Flux<Card> getCardList(UserData userData);
}
