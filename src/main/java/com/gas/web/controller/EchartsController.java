package com.gas.web.controller;

import com.gas.web.bean.Res;
import com.gas.web.bean.Schedule;
import com.gas.web.bean.Task;
import com.gas.web.display.Display;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.workflowsim.CondorVM;
import org.workflowsim.Job;

import org.workflowsim.examples.scheduling.*;

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

        FCFSSchedulingAlgorithmExample.main(args);

        res.put("code", "200");
        res.put("data", genJson());
        return res;
   }
      
    private Display toDisplay(List<CondorVM> vmList, List<Job> jobList) {
        Schedule schedule = new Schedule();
        List<Res> resList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();
        for (CondorVM vm : vmList) {
            Res res = new Res();
            res.setName(String.valueOf(vm.getId()));
            resList.add(res);
        }
        for (Job job : jobList) {
            for (org.workflowsim.Task taskTmp : job.getTaskList()) {
                Task task = new Task();
                task.setIndexRes(taskTmp.getVmId());
                task.setStartTime(taskTmp.getExecStartTime());
                task.setEndTime(taskTmp.getTaskFinishTime());
                task.setId(taskTmp.getCloudletId());
                // 读取后继任务和前驱任务的id
                List<Integer> sucList = task.getSucList();
                List<Integer> preList = task.getPreList();
                for (org.workflowsim.Task taskChild : taskTmp.getChildList()) {
                    sucList.add(taskChild.getCloudletId());
                }
                for (org.workflowsim.Task taskParent : taskTmp.getParentList()) {
                    preList.add(taskParent.getCloudletId());
                }
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
        taskList.add(new Task(0, 1496840400000L, 1496841300000L, "Y1713"));
        taskList.add(new Task(0, 1496850300000L, 1496870400000L, "Y3803"));
        taskList.add(new Task(1, 1496846400000L, 1496879400000L, "Y3901"));
        taskList.add(new Task(1, 1496883600000L, 1496890800000L, "Y4654"));
        taskList.add(new Task(2, 1496840400000L, 1496843400000L, "Y8626"));
        taskList.add(new Task(2, 1496850600000L, 1496865900000L, "Y0050"));
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
