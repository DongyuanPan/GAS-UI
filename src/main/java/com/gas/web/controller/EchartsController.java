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
import java.util.Random;

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

    @RequestMapping("/test")
    public String test() {
        return "test";
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
        HashMap<Integer, Integer> VMidToIndex = new HashMap<>();
        HashMap<Integer, Integer> TaskidToIndex = new HashMap<>();

        int index = 0;
        for (Job job : jobList) {
            for (org.workflowsim.Task taskTmp : job.getTaskList()) {
                TaskidToIndex.put(taskTmp.getCloudletId(), index);
                ++index;
            }
        }

        for (int i = 0; i < vmList.size(); ++i) {
            CondorVM vm = vmList.get(i);
            Res res = new Res();
            res.setName(String.valueOf(vm.getId()));
            res.setType(String.valueOf(vm.getHost()));
            resList.add(res);
            VMidToIndex.put(vm.getId(), i);
        }

        for (Job job : jobList) {
            for (org.workflowsim.Task taskTmp : job.getTaskList()) {
                Task task = new Task();
                task.setIndexRes(VMidToIndex.get(job.getVmId()));
                task.setStartTime(taskTmp.getExecStartTime());
                task.setEndTime(taskTmp.getTaskFinishTime());
                task.setId(taskTmp.getCloudletId());
                task.setName(taskTmp.getType());
                task.setColorNum(new Random().nextInt(11));
                // 读取后继任务和前驱任务的id
                List<Integer> sucList = task.getSucList();
                List<Integer> preList = task.getPreList();
                task.setHightlight(false);
                for (org.workflowsim.Task taskChild : taskTmp.getChildList()) {
                    sucList.add(TaskidToIndex.get(taskChild.getCloudletId()));
                }
                for (org.workflowsim.Task taskParent : taskTmp.getParentList()) {
                    preList.add(TaskidToIndex.get(taskParent.getCloudletId()));
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
        taskList.add(new Task(0, 14, 149, "Y1713",false,1));
        taskList.add(new Task(0, 154, 249, "Y3803",false,2));
        taskList.add(new Task(1, 14, 149, "Y3901",false,3));
        taskList.add(new Task(1, 154, 249, "Y4654",false,4));
        taskList.add(new Task(2, 14, 149, "Y8626",false,5));
        taskList.add(new Task(2, 149, 296, "Y0050",false,6));
        schedule.setResList(resList);
        schedule.setTaskList(taskList);

        Gson gson = new Gson();
        Display display = new Display(schedule);
        String json = gson.toJson(display);
        System.out.println(json);
        return display;
//        System.out.println(json);

//        String jsonPath = "F:\\workspace\\IdeaProjects\\springboot_web\\src\\main\\resources\\static\\json";
//        CreateFileUtil.createJsonFile(json, jsonPath, "data");
    }




}
