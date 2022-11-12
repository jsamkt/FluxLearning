package com.company;

public class Fura extends Car {

    private Integer pricepTonna;

    public Fura(String mark, String model, String color, Wheel wheel, Integer pricepTonna) {
        super(mark, model, color, wheel);
        this.pricepTonna = pricepTonna;
    }

    @Override
    public void ride() {
//        super.ride();
        System.out.println("I have pricep with " + pricepTonna);
    }


    public void signal() {
        System.out.println("Bip bip");
    }
}
