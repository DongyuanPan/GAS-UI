package com.gas.web.bean;

public class Task {
    private int indexRes;
    private long startTime;
    private long endTime;
    private String name;

    public Task() {

    }

    public Task(int indexRes, long startTime, long endTime, String name) {
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

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
