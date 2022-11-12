package com.learning.flux.controller;

import com.learning.flux.dto.FeeCard;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/FeeCards")
public class FeeCardController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FeeCard> getAdviceCard(
    ) throws InterruptedException {
        System.out.println("FeeCardController");

        TimeUnit.SECONDS.sleep(7);
        return Stream.of(1, 2, 3, 4, 5, 6, 7, 8)
                .map(i -> new FeeCard(
                        "",
                        BigDecimal.TEN,
                        BigDecimal.TEN,
                        22L,
                        BigDecimal.valueOf(new Random().nextDouble())
                ))
                .collect(Collectors.toList());
    }
}
