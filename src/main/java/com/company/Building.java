package com.company;

public class Building {
    private Integer height;
    public Integer area;

    public Building() {
    }

    public Building(Integer x, Integer y) {
        this.height = x;
        this.area = y;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getArea() {
        return area;
    }

    public void asdasdasdasdasd(Integer asdasda) {
        this.area = asdasda;
    }

    public Integer getBrickAmount(){
        return (this.area * this.height) / 55;
    }
}