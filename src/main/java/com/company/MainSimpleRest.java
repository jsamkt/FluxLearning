package com.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class MainSimpleRest {

    public static int getSum(int m, int n) {
        return m + n;
    }

    public static void doSomething() {
        //asdadadasdasd
        System.out.println("asdasdasdas");
    }

    public static void main(String[] s) {
//        List<String> list = new ArrayList<>();
        List<String> list = new LinkedList<>();

        int sum = getSum(1, 2);
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

    @Service
    public static class WeatherService {
        public WeatherEvent createWeather() {
            System.out.println("createWeather");
            return new WeatherEvent(
                    getTemprature(),
                    getHumidity(),
                    getWindSpeed(),
                    LocalDateTime.now()
            );
        }

        private String getWindSpeed() {
            String[] windSpeeds = "100 km/h,101 km/h, 102 km/h,103 km/h, 104 km/h" .split(",");
            return windSpeeds[new Random().nextInt(windSpeeds.length)];
        }

        private String getHumidity() {
            String[] humidity = "40%,41%, 42%,42%,44%,45%,46%" .split(",");
            return humidity[new Random().nextInt(humidity.length)];
        }

        private String getTemprature() {
            String[] tempratures = "19C,19.5C,20C,20.5C, 21C,21.5 C,22C,22.5C,23C,23.5C,24 C"
                    .split(",");
            return tempratures[new Random()
                    .nextInt(tempratures.length)];
        }
    }

    @RestController
    public static class SimpleRestController {

        @Autowired
        private WeatherService weatherService;

        @GetMapping(path = "weatherstream2")
        public WeatherEvent getWeather() {
            return weatherService.createWeather();
        }
    }
}
