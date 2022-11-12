package com.learning.flux.controller.pipeline;

import com.learning.flux.dto.Card;
import io.netty.buffer.PooledByteBufAllocatorMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping(path = "/pipeline")
public class PipelineController {

    private final Scheduler shedulerB = Schedulers.fromExecutorService(Executors.newFixedThreadPool(10));
    private final Scheduler shedulerC = Schedulers.fromExecutorService(Executors.newFixedThreadPool(10));

    private final AtomicInteger counterA = new AtomicInteger(0);
    private final AtomicInteger counterB = new AtomicInteger(0);

    private final List<String> list = new ArrayList<>();

    private WebClient client = WebClient.builder()
            .baseUrl("http://localhost:8080/pipeline/")
            .build();

    @Autowired
    private PooledByteBufAllocatorMetric metric;

    @GetMapping(value = "/A/")
    public Mono<Void> getCardA() {
        System.out.println("Starting A controller: " + Thread.currentThread());

        return client
                .post()
                .uri("/B/")
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .body(generate().doOnNext(c -> print("A")), Card.class)
                .exchange()
                .then();
    }

    @PostMapping(path = "/B/",
            produces = MediaType.APPLICATION_STREAM_JSON_VALUE,
            consumes = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<Void> getCardB(
            @RequestBody Flux<Card> flux
    ) throws InterruptedException {
        System.out.println("Starting B controller: " + Thread.currentThread());
        Flux<Card> b_service_card =
//                Flux.merge(flux,
//                        generate()
//                                .doOnNext(c -> c.setName("B_service_card"))
//                )
                flux
                        .doOnNext(c -> counterA.decrementAndGet())
                        .doOnCancel(() -> doOnCancel("B"))
                        .doOnComplete(() -> doOnComplete("B"))
                        .doOnError((t) -> doOnError(t, "B"))
                        .doOnTerminate(() -> doOnTerminate("B"))
                        .doOnRequest(l -> doOnRequest(l, "B"))
//                        .take(10)
                        .publishOn(shedulerB)
                        .doOnNext(c -> {
                            counterB.incrementAndGet();
                            print("B");
                            boolean contains = list.contains(UUID.randomUUID().toString());
                            if (contains) {
                                System.out.println("Contains");
                            }
                        });
//                        .doOnNext(c -> System.out.println("Flux in B: " + Thread.currentThread() + "; count: " + counterB.get()));

        return client
                .post()
                .uri("/C/")
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .body(b_service_card, Card.class)
                .exchange()
                .then();
    }

    @PostMapping(value = "/C/",
            produces = MediaType.APPLICATION_STREAM_JSON_VALUE,
            consumes = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<Void> getCardC(
            @RequestBody Flux<Card> flux
    ) throws InterruptedException {
        System.out.println("Starting C controller: " + Thread.currentThread());
        return flux
//                .take(100)
                .doOnNext(c -> counterB.decrementAndGet())
                .doOnCancel(() -> doOnCancel("C"))
                .doOnComplete(() -> doOnComplete("C"))
                .doOnError((t) -> doOnError(t, "C"))
                .doOnTerminate(() -> doOnTerminate("C"))
                .doOnRequest(l -> doOnRequest(l, "C"))
                .publishOn(shedulerB)
                .doOnNext(c -> {
                    print("C");
                    boolean contains = list.contains(UUID.randomUUID().toString());
                    if (contains) {
                        System.out.println("Contains");
                    }
                })
//                .doOnNext(card -> System.out.println("Flux in C: " + Thread.currentThread()+"; count: " + counterC.get()))
                .then();
    }

    private Flux<Card> generate() {
//        Flux<Card> generate = Flux.interval(Duration.ofSeconds(1))
//                .map(i ->
//                        new Card(
//                                "A_service_card",
//                                BigDecimal.ONE,
//                                BigDecimal.ONE,
//                                i,
//                                "value"
//                        ));

//      Flux<Card> generate =  Flux.just(
//                new Card(
//                        "A_service_card",
//                        BigDecimal.ONE,
//                        BigDecimal.ONE,
//                        0L,
//                        "value"
//                ));

        Flux<Card> generate = Flux.generate(
                () -> 1,
                (value, sink) -> {
                    sink.next(
                            new Card(
                                    "A_service_card",
                                    BigDecimal.ONE,
                                    BigDecimal.ONE,
                                    0L,
                                    "value"
                            )
                    );
                    return value + 1;
                }
        );

        return generate
//                .doOnNext(c -> System.out.println("Flux in A: " + Thread.currentThread() + "; count: " + counterA.incrementAndGet()));
                .doOnNext(c -> counterA.incrementAndGet())
                .doOnCancel(() -> doOnCancel("A"))
                .doOnComplete(() -> doOnComplete("A"))
                .doOnError((t) -> doOnError(t, "A"))
                .doOnTerminate(() -> doOnTerminate("A"))
                .doOnRequest(l -> doOnRequest(l, "A"));
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 1; i++) {
            list.add(UUID.randomUUID().toString());
        }
        System.out.println("Initialization finish");
    }

//      .doOnCancel(() -> )
//            .doOnComplete(() -> )

    private void doOnComplete(String serviceName) {
        System.out.println("*".repeat(60) +
                "[%s] %s Completed".formatted(serviceName, Thread.currentThread()) +
                "*".repeat(60));
    }

    private void doOnCancel(String serviceName) {
        System.out.println("*".repeat(60) +
                "[%s] %s Canceled".formatted(serviceName, Thread.currentThread()) +
                "*".repeat(60)
        );
    }

    private void doOnError(Throwable t, String serviceName) {
        System.out.println("*".repeat(60) +
                "[%s] %s Error: []".formatted(serviceName, Thread.currentThread(), t) +
                "*".repeat(60));
    }

    private void doOnTerminate(String serviceName) {
        System.out.println("*".repeat(60) +
                "[%s] %s Terminated".formatted(serviceName, Thread.currentThread()) +
                "*".repeat(60));
    }

    private void doOnRequest(Long l, String serviceName) {
        System.out.println("*".repeat(60) +
                "[%s] %s Requested %s".formatted(serviceName, Thread.currentThread(), l) +
                "*".repeat(60));
    }


    private void print(String serviceName) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(
                """
                                                    Service: %s;
                                                    Thread: %s;
                        Count A: %s;
                        Count B: %s;
                                                    DirectBuffer: %s
                                                    HeapMemory: %s
                        ==================================================================
                        """
                        .formatted(
                                serviceName,
                                Thread.currentThread(),
                                counterA.get(),
                                counterB.get(),
                                metric.usedDirectMemory(),
                                metric.usedHeapMemory()));
    }
}
