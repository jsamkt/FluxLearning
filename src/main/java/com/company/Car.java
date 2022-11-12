package com.company;

public class Car {

    private String mark;
    private String model;
    private String color;

    private Wheel wheel;


    public Car(String mark, String model, String color, Wheel wheel) {
        this.mark = mark;
        this.model = model;
        this.color = color;
        this.wheel = wheel;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Wheel getWheel() {
        return wheel;
    }

    public void setWheel(Wheel wheel) {
        this.wheel = wheel;
    }

    public void ride () {
        System.out.println("Hello, I'm " + mark + ", " + model + ". And I'm " + color);
        System.out.println("My wheel size is " + wheel.getSize());
    }
}
