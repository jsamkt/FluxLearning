package com.company;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class MainWebFlux {

    public static void main(String[] args) {
        SpringApplication.run(MainWebFlux.class, args);
    }

    public static class WeatherEvent {
        private final String temprature;
        private final String humidity;
        private final String windSpeed;
        private final LocalDateTime date;

        public WeatherEvent(String temprature, String humidity, String windSpeed, LocalDateTime date) {
            this.temprature = temprature;
            this.humidity = humidity;
            this.windSpeed = windSpeed;
            this.date = date;
        }

        public String getTemprature() {
            return temprature;
        }

        public String getHumidity() {
            return humidity;
        }

        public String getWindSpeed() {
            return windSpeed;
        }

        public LocalDateTime getDate() {
            return date;
        }
    }

    public static record SimpleGenerationValue(Long id, String value) {
    }

    @Service
    public static class GeneratorService {

        public Flux<SimpleGenerationValue> generateValues() {
            return Flux.generate(
                    () -> 1,
                    (state, sink) -> {
                        sink.next(new SimpleGenerationValue((long) state, UUID.randomUUID().toString()));
                        if (state == 10000) {
                            sink.complete();
                        }
                        return state + 1;
                    }
            );
        }

    }

    @Service
    public static class WeatherService {
        public Flux<WeatherEvent> createWeather() {
            Flux<Long> interval = Flux.interval(Duration.ofMillis(100));
            Flux<WeatherEvent> weatherEvent = Flux.fromStream(Stream.generate(() -> new WeatherEvent(
                    getTemprature(),
                    getHumidity(),
                    getWindSpeed(),
                    LocalDateTime.now()
            )));

            return Flux.zip(interval, weatherEvent, (key, value) -> value)
                    .doOnNext(System.out::println);
        }

        public Mono<WeatherEvent> createOneWeather() {
            System.out.println("createOneWeather");
            return Mono.just(
                    new WeatherEvent(
                            getTemprature(),
                            getHumidity(),
                            getWindSpeed(),
                            LocalDateTime.now()
                    )
            );
        }

        private String getWindSpeed() {
            String[] windSpeeds = "100 km/h,101 km/h, 102 km/h,103 km/h, 104 km/h".split(",");
            return windSpeeds[new Random().nextInt(windSpeeds.length)];
        }

        private String getHumidity() {
            String[] humidity = "40%,41%, 42%,42%,44%,45%,46%".split(",");
            return humidity[new Random().nextInt(humidity.length)];
        }

        private String getTemprature() {
            String[] tempratures = "19C,19.5C,20C,20.5C, 21C,21.5 C,22C,22.5C,23C,23.5C,24 C"
                    .split(",");
            return tempratures[new Random()
                    .nextInt(tempratures.length)];
        }
    }

    @Component
    public static class RequestHandler {

        @Autowired
        private WeatherService weatherService;

        @Autowired
        private GeneratorService generatorService;

        @NotNull
        public Mono<ServerResponse> streamWeather(ServerRequest serverRequest) {
            return ServerResponse.ok()
                    .contentType(MediaType.TEXT_EVENT_STREAM)
                    .body(weatherService.createWeather(), WeatherEvent.class);
        }

        @NotNull
        public Mono<ServerResponse> generatedStream(ServerRequest serverRequest) {
            return ServerResponse.ok()
                    .contentType(MediaType.TEXT_EVENT_STREAM)
                    .body(generatorService.generateValues(), SimpleGenerationValue.class);
        }

        @NotNull
        public Mono<ServerResponse> oneWeather(ServerRequest request) {
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(weatherService.createOneWeather(), WeatherEvent.class);
        }
    }

    @Configuration
    public static class RequestRouter {

        @Bean
        public RouterFunction<ServerResponse> routes(RequestHandler requestHandler) {
            return RouterFunctions
                    .route()
                    .GET("/weatherstream", requestHandler::streamWeather)
                    .GET("/weatherstream3", requestHandler::oneWeather)
                    .GET("/generatedstream", requestHandler::generatedStream)
                    .build();
        }
    }

}
