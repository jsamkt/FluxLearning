package com.learning.flux.client;


import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BaseCardClient extends BaseClient {
    public BaseCardClient(String baseUrl) {
        super(baseUrl);
    }

    public <T> Flux<T> getCards(String userId,
                                BigDecimal longitude,
                                BigDecimal latitude,
                                Long currentDate,
                                MediaType accept,
                                Class<? extends T> clazz) {
        return getData("/",
                createHeaders(userId, longitude, latitude),
                createQuery(currentDate),
                accept,
                HttpMethod.GET,
                clazz);
    }

    private static MultiValueMap<String, String> createHeaders(
            String userId,
            BigDecimal longitude,
            BigDecimal latitude
    ) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("userId", userId);
        headers.add("longitude", longitude.toString());
        headers.add("latitude", latitude.toString());
        return headers;
    }

    private static Map<String, String> createQuery(Long currentDate) {
        Map<String, String> query = new HashMap<>();
        query.put("currentDate", currentDate.toString());
        return query;
    }


}
