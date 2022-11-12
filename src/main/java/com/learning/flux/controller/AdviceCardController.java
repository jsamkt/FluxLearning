package com.learning.flux.controller;

import com.learning.flux.dto.AdviceCard;
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
@RequestMapping("/AdviceCards")
public class AdviceCardController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AdviceCard> getAdviceCard(
    ) throws InterruptedException {
        System.out.println("AdviceCardController");

        TimeUnit.SECONDS.sleep(5);
        return Stream.of(1, 2, 3, 4, 5)
                .map(i -> new AdviceCard(
                        "userId",
                        BigDecimal.TEN,
                        BigDecimal.TEN,
                        33L,
                        BigDecimal.valueOf(new Random().nextDouble())
                ))
                .collect(Collectors.toList());
    }
}
