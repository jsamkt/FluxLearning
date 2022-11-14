package com.learning.flux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
public class FluxApplication {

    public static void main(String[] args) {

        Schedulers.onHandleError((thread, throwable) -> {
            System.out.println(
                    "#".repeat(50)
                            .concat(" ERROR [%s] in %s ".formatted(thread.getName(), thread.getName()))
                            .concat("#".repeat(50))
            );
            System.exit(0);
        });

        SpringApplication.run(FluxApplication.class, args);
    }
}
