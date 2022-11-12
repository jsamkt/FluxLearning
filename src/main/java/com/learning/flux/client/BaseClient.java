package com.learning.flux.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class BaseClient {
    private WebClient client;

    public BaseClient(String baseUrl) {
        this.client = WebClient.create(baseUrl);
    }

    protected  <T> Flux<T> getData(String url,
                                                   MultiValueMap<String, String> headers,
                                                   Map<String, String> values,
                                                   MediaType mediaType,
                                                   HttpMethod method,
                                                   Class<? extends T> clazz) {
        return client.get()
                .uri(uriBuilder -> uriBuilder.path(url)
                        .build()
                )
                .exchangeToFlux(response -> response.bodyToFlux(clazz));
    }



}
