package com.gas.web.StaticAlgorithm;

import org.cloudbus.cloudsim.Cloudlet;
import org.workflowsim.CondorVM;
import org.workflowsim.Job;
import org.workflowsim.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chromosome {
    public ArrayList<Integer> gene;  //基因
    public double f = -1.0;
    public List<Task> taskList;
    public List<CondorVM> vmList;
    public List<Job> result;
    public double finishTime = 0.0;

    public Chromosome(ArrayList<Integer> gene, List<Task> taskList, List<CondorVM> vmList) {
        this.gene = gene;
        this.taskList = taskList;
        this.vmList = vmList;
        this.result = new ArrayList<>();
    }

    public Chromosome(ArrayList<Integer> gene, List<Task> taskList, List<CondorVM> vmList, double f, List<Job> result, Double finishTime) {
        this.gene = gene;
        this.f = f;
        this.taskList = taskList;
        this.vmList = vmList;
        this.result = result;
        this.finishTime = finishTime;
    }

    public ArrayList<Integer> getGene() {
        return gene;
    }

    public void setGene(ArrayList<Integer> gene) {
        this.gene = gene;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public double getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
    }

    public void calFitness() throws Exception {
        // 判断是否已经计算过
        if (this.getF() > 0) {
            return;
        }
        Double f = 0.0;
        Map<Integer, Double> vmToTime = new HashMap<>();
        Map<Integer, Double> taskFinishTimeMap = new HashMap<>();
        for (int i = 0; i < vmList.size(); ++i) {
            vmToTime.put(i, 0.0);
        }

        for (int i = 0; i < taskList.size(); ++i) {
            Task task = taskList.get(i);
            Integer vmSeq = gene.get(i);
            Job newTask = new Job(task.getCloudletId(), task.getCloudletLength());

            Double readyTime = 0.0;
            for (Task parent:task.getParentList()) {
                if (taskFinishTimeMap.get(parent.getCloudletId()) > readyTime) {
                    readyTime = parent.getFinishTime();
                }
            }

            Double vmReadyTime = vmToTime.get(vmSeq);
            Double taskStartTime = vmReadyTime;
            Double cpuTime = task.getCloudletLength() / vmList.get(vmSeq).getMips();
            Double taskFinishTime = taskStartTime + cpuTime;

            // 更新虚拟机的可获得时间
            vmToTime.put(vmSeq, taskFinishTime);
            taskFinishTimeMap.put(task.getCloudletId(), taskFinishTime);

            newTask.setCloudletStatus(Cloudlet.SUCCESS);
            newTask.setDepth(task.getDepth());
            newTask.setVmId(vmList.get(vmSeq).getId());
            newTask.setExecStartTime(taskStartTime);  // 设置 任务执行状态必须放在 设置时间之前 不然时间会被重置
            newTask.setFinishTime(taskFinishTime);

            result.add(newTask);

            // 记录最终完成时间
            f = Math.max(f, taskFinishTime);
        }
        setF(f);
        setFinishTime(f);
    }
}
