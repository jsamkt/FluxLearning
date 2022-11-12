package com.learning.flux.controller;

import com.learning.flux.dto.Card;
import com.learning.flux.dto.UserData;
import com.learning.flux.service.CardService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/hub")
public class HubController {

    public HubController(CardService cardService) {
        this.cardService = cardService;
    }

    private final CardService cardService;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Flux<Card> getCards(
            @RequestParam("userId") String userId,
            @RequestParam("longitude") BigDecimal longiture,
            @RequestParam("latitude") BigDecimal latitude,
            @RequestParam("currentDate") Long currentDate
    ) {
        return cardService.getCardList(
                new UserData(
                        userId,
                        longiture,
                        latitude,
                        currentDate
                )
        );
    }
}
