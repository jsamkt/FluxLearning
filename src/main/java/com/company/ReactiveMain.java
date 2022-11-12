package com.company;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ReactiveMain {

    public static void main(String[] args) throws InterruptedException {


        Flux.merge(
                Flux.range(1, 10),
                Flux.range(100, 10)
        );

        Flux<Object> generate = Flux.generate(() -> 1,
                (value, sink) -> {
                    sink.next(value);
                    return value + 1;
                });

        Flux.merge(
                generate,
                Flux.range(100, 100)
        )
                .take(10)
                .subscribe(System.out::println);

    }
}
