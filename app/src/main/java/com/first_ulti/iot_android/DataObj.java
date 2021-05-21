package com.first_ulti.iot_android;

public class DataObj{

    private Integer food;
    private Integer temperature;
    private Integer water;
    private Integer counter;

    public DataObj(){}

    public DataObj(Integer food, Integer temperature, Integer water, Integer counter){
        this.counter = counter;
        this.temperature = temperature;
        this.water = water;
        this.food = food;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public void setWater(Integer water) {
        this.water = water;
    }

    public void setFood(Integer food) {
        this.food = food;
    }

    public Integer getCounter() {
        return this.counter;
    }

    public Integer getTemperature(){
        return this.temperature;
    }

    public Integer getWater(){
        return this.water;
    }

    public Integer getFood() {
        return this.food;
    }
}