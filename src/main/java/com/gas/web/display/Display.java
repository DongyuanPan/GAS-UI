package com.gas.web.display;

import com.gas.web.bean.Res;
import com.gas.web.bean.Schedule;
import com.gas.web.bean.Task;

import java.util.ArrayList;
import java.util.List;

public class Display {
    public ParkingApr getResources() {
        return resources;
    }

    public void setResources(ParkingApr resources) {
        this.resources = resources;
    }

    public Flight getTasks() {
        return tasks;
    }

    public void setTasks(Flight tasks) {
        this.tasks = tasks;
    }

    private ParkingApr resources;
    private Flight tasks;

    public Display() {

    }

    public Display(Schedule schedule) {
        setParkingApron(schedule);
        setFlight(schedule);
    }

    public void setParkingApron(Schedule schedule) {
        resources = new ParkingApr();
        List<Res> resList = schedule.getResList();
        List<String> dimensions = new ArrayList<>();
        dimensions.add("Name");
        dimensions.add("Type");
//        dimensions.add("Near Bridge");
        List<List<Object>> data = new ArrayList<>();
        for (Res res : resList) {
            List<Object> record = new ArrayList<>();
            record.add(res.getName());
            record.add(res.getType());
//            record.add(true);
            data.add(record);
        }
        resources.setDimensions(dimensions);
        resources.setData(data);
    }

    public void setFlight(Schedule schedule) {
        tasks = new Flight();
        List<Task> taskList = schedule.getTaskList();
        List<String> dimensions = new ArrayList<>();
        dimensions.add("Resources Index");
        dimensions.add("Start Time");
        dimensions.add("Finish Time");
        dimensions.add("id");
        dimensions.add("sucs");
        dimensions.add("pres");
        dimensions.add("highlight");
        dimensions.add("Name");
        dimensions.add("Color");

        List<List<Object>> data = new ArrayList<>();
        for (Task task : taskList) {
            List<Object> record = new ArrayList<>();
            record.add(task.getIndexRes());
            record.add(task.getStartTime());
            record.add(task.getEndTime());
            record.add(task.getId());
            record.add(task.getSucList());
            record.add(task.getPreList());
            record.add(task.isHightlight());
            record.add(task.getName());
            record.add(task.getColorNum());
            data.add(record);
        }
        tasks.setDimensions(dimensions);
        tasks.setData(data);
    }
}
