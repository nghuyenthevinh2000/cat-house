package com.first_ulti.iot_android;

public class DataObj{

    private String receive_time;
    private Double temperature;
    private Double water;
    private Double food;

    public DataObj(){}

    public DataObj(String receive_time, Double temperature, Double water, Double food){
        this.receive_time = receive_time;
        this.temperature = temperature;
        this.water = water;
        this.food = food;
    }

    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setWater(Double water) {
        this.water = water;
    }

    public void setFood(Double food) {
        this.food = food;
    }

    public String getReceive_time(){
        return this.receive_time;
    }

    public Double getTemperature(){
        return this.temperature;
    }

    public Double getWater(){
        return this.water;
    }

    public Double getFood() {
        return this.food;
    }
}