package com.learning.flux.controller;

import com.learning.flux.dto.FeeCard;
import com.learning.flux.dto.RegularCard;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/RegularCards")
public class RegularCardController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RegularCard> getAdviceCard() throws InterruptedException {
        System.out.println("RegularCardController");

        TimeUnit.SECONDS.sleep(2);
        return Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
                .map(i -> {
                    Map<String, Long> values = new HashMap<>();
                    values.put("index", Long.valueOf(i));
                    values.put("x", new Random().nextLong());
                    values.put("calculate", (long) (i & i >> 2));
                    return values;
                })
                .map(values -> new RegularCard(
                        "userId",
                        BigDecimal.ONE,
                        BigDecimal.ZERO,
                        55L,
                        values
                ))
                .collect(Collectors.toList());
    }
}
