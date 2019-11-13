package com.gas.web.controller;

import com.gas.web.bean.Res;
import com.gas.web.bean.Schedule;
import com.gas.web.bean.Task;
import com.gas.web.display.Display;
import com.gas.web.util.CreateFileUtil;
import com.google.gson.Gson;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.workflowsim.CondorVM;
import org.workflowsim.Job;
import org.workflowsim.utils.Parameters;

import org.cloudbus.cloudsim.Log;
import org.workflowsim.examples.clustering.*;
import org.workflowsim.examples.clustering.balancing.*;
import org.workflowsim.examples.failure.*;
import org.workflowsim.examples.failure.clustering.*;
import org.workflowsim.examples.planning.*;
import org.workflowsim.examples.cost.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class EchartsController {

    @ResponseBody
    @RequestMapping("/init")
    public HashMap<String, Object> init() {
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", "200");
        res.put("data", genJson());
        return res;
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/echarts")
    public String echarts() {
        return "echarts";
    }

    @ResponseBody
    @RequestMapping("/FCFSExample")
    public HashMap<String, Object> FCFSExample() {
        String[] args = new String[]{};
        HashMap<String, Object> res = new HashMap<>();
        List<CondorVM> vmList = new ArrayList<>();
        List<Job> jobList = new ArrayList<>();
        FCFSSchedulingAlgorithmExample f = new FCFSSchedulingAlgorithmExample();
        f.FCFS();

        res.put("code", "200");
        res.put("data", toDisplay(f.getCondorVMList(), f.getTaskList()));
        return res;
   }
      
    private Display toDisplay(List<CondorVM> vmList, List<Job> jobList) {
        Schedule schedule = new Schedule();
        List<Res> resList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();
        HashMap<Integer, Integer> idToIndex = new HashMap<>();
        for (int i = 0; i < vmList.size(); ++i) {
            CondorVM vm = vmList.get(i);
            Res res = new Res();
            res.setName(String.valueOf(vm.getId()));
            resList.add(res);
            idToIndex.put(vm.getId(), i);
        }
        for (Job job : jobList) {
            for (org.workflowsim.Task taskTmp : job.getTaskList()) {
                Task task = new Task();
                task.setIndexRes(idToIndex.get(job.getVmId()));
                task.setStartTime(taskTmp.getExecStartTime());
                task.setEndTime(taskTmp.getTaskFinishTime());
                task.setName(String.valueOf(taskTmp.getCloudletId()));
                taskList.add(task);
            }
        }
        schedule.setResList(resList);
        schedule.setTaskList(taskList);
        return new Display(schedule);
    }

    private Display genJson() {
        Schedule schedule = new Schedule();
        List<Res> resList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();
        resList.add(new Res("AB95", "W"));
        resList.add(new Res("AB97", "W"));
        resList.add(new Res("AB98", "W"));
        taskList.add(new Task(0, 14, 149, "Y1713"));
        taskList.add(new Task(0, 14, 149, "Y3803"));
        taskList.add(new Task(1, 14, 149, "Y3901"));
        taskList.add(new Task(1, 14, 149, "Y4654"));
        taskList.add(new Task(2, 14, 149, "Y8626"));
        taskList.add(new Task(2, 14, 1496, "Y0050"));
        schedule.setResList(resList);
        schedule.setTaskList(taskList);

        Gson gson = new Gson();
        Display display = new Display(schedule);
        String json = gson.toJson(display);
        return display;
//        System.out.println(json);

//        String jsonPath = "F:\\workspace\\IdeaProjects\\springboot_web\\src\\main\\resources\\static\\json";
//        CreateFileUtil.createJsonFile(json, jsonPath, "data");
    }



}
