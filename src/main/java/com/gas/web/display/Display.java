package com.gas.web.display;

import com.gas.web.bean.Res;
import com.gas.web.bean.Schedule;
import com.gas.web.bean.Task;

import java.util.ArrayList;
import java.util.List;

public class Display {
    private ParkingApron parkingApron;
    private Flight flight;

    public Display() {

    }

    public Display(Schedule schedule) {
        setParkingApron(schedule);
        setFlight(schedule);
    }

    public ParkingApron getParkingApron() {
        return parkingApron;
    }

    public void setParkingApron(ParkingApron parkingApron) {
        this.parkingApron = parkingApron;
    }

    public void setParkingApron(Schedule schedule) {
        parkingApron = new ParkingApron();
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
        parkingApron.setDimensions(dimensions);
        parkingApron.setData(data);
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setFlight(Schedule schedule) {
        flight = new Flight();
        List<Task> taskList = schedule.getTaskList();
        List<String> dimensions = new ArrayList<>();
        dimensions.add("Parking Apron Index");
        dimensions.add("Arrival Time");
        dimensions.add("Departure Time");
        dimensions.add("id");
        dimensions.add("sucs");
        dimensions.add("pres");
        dimensions.add("highlight");
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
            data.add(record);
        }
        flight.setDimensions(dimensions);
        flight.setData(data);
    }
}
