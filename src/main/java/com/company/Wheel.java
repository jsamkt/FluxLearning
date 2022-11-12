package com.company;

public class Wheel {

    private Integer size;
    private Boolean usage;

    public Wheel(Integer size, Boolean usage) {
        System.out.println("Creating new wheel");
        this.size = size;
        this.usage = usage;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getUsage() {
        return usage;
    }

    public void setUsage(Boolean usage) {
        this.usage = usage;
    }
}
