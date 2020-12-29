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
    public List<Job> jobList;
    public List<CondorVM> vmList;
    public List<Job> result;
    public double finishTime = 0.0;

    // 创建新的个体
    public Chromosome(ArrayList<Integer> gene, List<Job> jobList, List<CondorVM> vmList) {
        this.gene = gene;
        this.jobList = jobList;
        this.vmList = vmList;
        this.result = new ArrayList<>();
    }

    // 保留原来最优的个体
    public Chromosome(ArrayList<Integer> gene, List<Job> jobList, List<CondorVM> vmList, double f, List<Job> result, Double finishTime) {
        this.gene = gene;
        this.f = f;
        this.jobList = jobList;
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
        Map<Integer, Double> jobFinishTimeMap = new HashMap<>();
        for (int i = 0; i < vmList.size(); ++i) {
            vmToTime.put(i, 0.0);
        }

        for (int i = 0; i < jobList.size(); ++i) {
            Integer vmSeq = gene.get(i);
            // 创建 Job的备份 作为结果，防止每个基因的jobList 互相影响（因为Java 对象都是引用传递）
//            Job job = jobList.get(i);
//            Job newJob = new Job(job.getCloudletId(), job.getCloudletLength());
            Job newJob = jobList.get(i);

            Double readyTime = 0.0;
            for (Object item :newJob.getParentList()) {
                Job parent = (Job)item;
                if (jobFinishTimeMap.get(parent.getCloudletId()) > readyTime) {
                    readyTime = parent.getFinishTime();
                }
            }

            Double vmReadyTime = vmToTime.get(vmSeq);
            Double jobStartTime = Math.max(vmReadyTime, readyTime);
            // 对Job中所有的Task 进行处理， 目前设置只有一个Task
            for (Task task:newJob.getTaskList()) {
                // 每个Task 依次在VM上执行
                Double taskStartTime = Math.max(jobStartTime, vmToTime.get(vmSeq));
                Double cpuTime = task.getCloudletLength() / vmList.get(vmSeq).getMips();
                Double taskFinishTime = taskStartTime + cpuTime;

                // 更新虚拟机的可获得时间
                vmToTime.put(vmSeq, taskFinishTime);
                jobFinishTimeMap.put(newJob.getCloudletId(), taskFinishTime);

                task.setCloudletStatus(Cloudlet.SUCCESS);
                task.setVmId(vmList.get(vmSeq).getId());
                task.setExecStartTime(taskStartTime);  // 设置 任务执行状态必须放在 设置时间之前 不然时间会被重置
                task.setTaskFinishTime(taskFinishTime);
            }

            newJob.setCloudletStatus(Cloudlet.SUCCESS);
            newJob.setVmId(vmList.get(vmSeq).getId());
            newJob.setExecStartTime(jobStartTime);  // 设置 任务执行状态必须放在 设置时间之前 不然时间会被重置
            newJob.setFinishTime(jobFinishTimeMap.get(newJob.getCloudletId()));

            result.add(newJob);

            // 记录最终完成时间
            f = Math.max(f, jobFinishTimeMap.get(newJob.getCloudletId()));
        }
        setF(f);
        setFinishTime(f);
    }
}
