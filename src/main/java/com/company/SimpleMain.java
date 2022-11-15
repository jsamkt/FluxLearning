package com.company;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SimpleMain {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        A a1 = new A("123123");
        A a2 = new A("asdasd");
        A a3 = new A("");
        A a4 = new A("xxx");
        A a5 = new A("ttt");
        A a6 = new A("acxc");
        A a7 = new A(";;;;");
        A a8 = new A("567890");
        A a9 = new A("yytytyt");
        A a10 = new A("yyyyy");

        Map<A, Integer> map = new HashMap<>();

        map.put(a1, 1);
        map.put(a2, 2);
        map.put(a3, 3);
        map.put(a4, 4);
        map.put(a5, 5);
        map.put(a6, 6);
        map.put(a7, 7);
        map.put(a8, 8);
        map.put(a9, 9);
        map.put(a10, 10);

        IntStream.range(0, 10_000)
                .mapToObj(s -> UUID.randomUUID().toString())
                .map(A::new)
                .forEach(a -> map.put(a, new Random().nextInt()));

        System.out.println(map.get(a6));
        System.out.println(map.get(a6));

    }

    public static class A {
        private final String value;

        public A(String value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
//            return new Random().nextInt();
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            return switch (obj) {
                case A a -> a.value.equals(value);
                default -> false;
            };
//            return true;
        }

        public String getValue() {
            return value;
        }
    }
}
