package com.gas.web.bean;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private int indexRes;
    private double startTime;
    private double endTime;
    private int id;
    private String name;
    private List<Integer> sucList;
    private List<Integer> preList;

    public Task() {
        preList = new ArrayList<>();
        sucList = new ArrayList<>();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getSucList() {
        return sucList;
    }

    public void setSucList(List<Integer> sucList) {
        this.sucList = sucList;
    }

    public List<Integer> getPreList() {
        return preList;
    }

    public void setPreList(List<Integer> preList) {
        this.preList = preList;
    }
}
