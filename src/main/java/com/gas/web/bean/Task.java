package com.gas.web.bean;

public class Task {
    private int indexRes;
    private double startTime;
    private double endTime;
    private String name;

    public Task() {

    }

    public Task(int indexRes, double startTime, double endTime, String name) {
        this.indexRes = indexRes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
    }

    public int getIndexRes() {
        return indexRes;
    }

    public void setIndexRes(int indexRes) {
        this.indexRes = indexRes;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
