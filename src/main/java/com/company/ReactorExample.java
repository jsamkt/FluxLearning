package com.company;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ReactorExample {

//    static class A {
//
//    }
//
//    static class B extends A {
//
//    }
//
//    static class C extends B {
//
//    }
//
//    public static void fExtendsB(List<? extends B> list) {
//        B b = list.get(0);
//    }
//
//    public static void fSuperB(List<? super B> list){
//        Object object = list.get(0);
//    }
//
//    public static void foo(){
//        List<? extends B> listExtendsB = new ArrayList<>();
//        List<? super B> listSuperB = new ArrayList<>();
//        List<A> listA = new ArrayList<>();
//        List<B> listB = new ArrayList<>();
//        List<C> listC = new ArrayList<>();
//
//        fExtendsB(listExtendsB);
//        fExtendsB(listSuperB);
//        fExtendsB(listA);
//        fExtendsB(listB);
//        fExtendsB(listC);
//
//        fSuperB(listExtendsB);
//        fSuperB(listSuperB);
//        fSuperB(listA);
//        fSuperB(listB);
//        fSuperB(listC);
//
//
//        listExtendsB.add(new A());
//        listExtendsB.add(new B());
//        listExtendsB.add(new C());
//
//        listSuperB.add(new A());
//        listSuperB.add(new B());
//        listSuperB.add(new C());
//
//        listA.add(new A());
//        listA.add(new B());
//        listA.add(new C());
//
//        listB.add(new A());
//        listB.add(new B());
//        listB.add(new C());
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//        foo();
//        Flux<Integer> first = Flux.range(0, 10)
//                .doOnNext(i -> System.out.println("Do on next: " + i))
//                ;
//        Flux<Integer> second = first
//                .filter(i -> i % 2 == 0)
//                .map(i -> i * i)
//                .publish();
//
//        System.out.println("***************before second*******************");
//        second.subscribe(i -> System.out.println("Second sout: " + i));
//        System.out.println("***************before first*******************");
//        first.subscribe(i -> System.out.println("First sout : " + i));
//        System.out.println("***************before third*******************");
//        first.subscribe(i -> System.out.println("=========Third sout: " + i));
//
//        TimeUnit.SECONDS.sleep(10);
//    }

    public static void main(String[] args) throws InterruptedException {
        int request = 10;

//        Scheduler schedulerA = Schedulers.newParallel("Scheduler A", 200);
//        Scheduler schedulerB = Schedulers.newParallel("Scheduler B");
//        Scheduler schedulerC = Schedulers.newParallel("Scheduler C");

//        Flux<Integer> integerFlux = Flux.range(0, 100)
//                .publishOn(schedulerA)
//                .subscribeOn(schedulerA);


//                integerFlux.subscribe(subscriber(100));
//                integerFlux.subscribe(subscriber(1));

//        schedulerA.dispose();
//        schedulerB.dispose();

        CountDownLatch cdl = new CountDownLatch(1);
        Scheduler scheduler = Schedulers.newParallel("TEST", 10);

        Disposable subscribe = Flux.range(0, 10)
                .flatMap(i ->
                        Mono.fromCallable(() -> {
                                    System.out.println("                Do on next: %s - [%s]".formatted(i, Thread.currentThread().getName()));
                                    sleep(2);
                                    return i;
                                }
                        ).publishOn(scheduler)
                )
//                .flatMap(i -> Flux.just(i).subscribeOn(scheduler))
                .doOnNext(i -> System.out.println("Do on next: %s - [%s]".formatted(i, Thread.currentThread().getName())))
//                .publishOn(Schedulers.boundedElastic())
//                .subscribeOn(Schedulers.boundedElastic())
                .doFinally((s) -> {
                    cdl.countDown();
                    System.out.println("Do on finally [%s]".formatted(Thread.currentThread().getName()));
                })
                .subscribe(i -> System.out.println("Subscribe: %s - [%s]".formatted(i, Thread.currentThread().getName())));
        cdl.await();
        scheduler.dispose();
    }

    private static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            System.out.println("ERROR");
            throw new RuntimeException(e);
        }
    }

    public static Subscriber<Integer> subscriber(int request) {
        return new Subscriber<Integer>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                subscription.request(request);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(Thread.currentThread().getName() + " : " + integer);
//                        try {
//                            TimeUnit.SECONDS.sleep(1);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
                subscription.request(request);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}
