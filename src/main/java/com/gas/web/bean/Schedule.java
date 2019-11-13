package com.gas.web.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Schedule {

    @SerializedName("parkingApron")
    private List<Res> resList;
    @SerializedName("flight")
    private List<Task> taskList;

    public Schedule() {
        resList = new ArrayList<>();
        taskList = new ArrayList<>();
    }

    public Schedule(List<Res> resList, List<Task> taskList) {
        this.resList = resList;
        this.taskList = taskList;
    }

    public List<Res> getResList() {
        return resList;
    }

    public void setResList(List<Res> resList) {
        this.resList = resList;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
