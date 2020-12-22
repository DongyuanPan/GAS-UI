/**
 * Copyright 2012-2013 University Of Southern California
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gas.web.controller;
import com.alibaba.fastjson.JSONArray;
import com.gas.web.entity.Vm;
import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import org.workflowsim.*;
import org.workflowsim.examples.scheduling.DataAwareSchedulingAlgorithmExample;
import org.workflowsim.utils.ClusteringParameters;
import org.workflowsim.utils.OverheadParameters;
import org.workflowsim.utils.Parameters;
import org.workflowsim.utils.ReplicaCatalog;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

/**
 * This FCFS Scheduling Algorithm
 *
 * @author Weiwei Chen
 * @since WorkflowSim Toolkit 1.1
 * @date Nov 9, 2013
 */
public class SchedulingAlgorithm {

    public List<CondorVM> CondorVMList;
    public List<Job> taskList;
    static int totalvm=0;
    public List<CondorVM> getCondorVMList() {
        return CondorVMList;
    }

    public void setCondorVMList(List<CondorVM> condorVMList) {
        CondorVMList = condorVMList;
    }

    public List<Job> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Job> taskList) {
        this.taskList = taskList;
    }

    ////////////////////////// STATIC METHODS ///////////////////////

    /**
     * Creates main() to run this example This example has only one datacenter
     * and one storage
     */
    public static List<CondorVM> SchedulecreateVM(int userId, int vms,long s,int r,int m,long band,int cpu) {
        //Creates a container to store VMs. This list is passed to the broker later
        int lastnum=totalvm;
        int i=0;
        totalvm=totalvm+vms;
        LinkedList<CondorVM> list = new LinkedList<>();
        //VM Parameters
        long size = s; //image size (MB)10000
        int ram =r; //vm memory (MB)512
        int mips = m;
        long bw = band;
        int pesNumber = cpu; //number of cpus
        String vmm = "Xen"; //VMM name
        //create VMs
        CondorVM[] vm = new CondorVM[vms];
        Random bwRandom = new Random(System.currentTimeMillis());
        for ( ; lastnum <totalvm; lastnum++) {
            double ratio = bwRandom.nextDouble();
            vm[i] = new CondorVM(lastnum, userId, mips * ratio, pesNumber, ram, (long) (bw * ratio), size, vmm, new CloudletSchedulerSpaceShared());
            list.add(vm[i]);
            i++;
        }
        return list;
    }
   //创建虚拟机
    public List<CondorVM> getDatacenterVM(Map<String,Vm> map,WorkflowEngine wfEngine ){
        List<CondorVM> vmlist=new ArrayList<>();
        for (Vm virtual:map.values()) {
            int count=virtual.getCount();
            long mirror=virtual.getMirror();
            int ram=virtual.getRam();
            int mips=virtual.getMips();
            long bandwidth=virtual.getBw();
            int cpu=virtual.getCpu();
           List<CondorVM> vmlistemp = SchedulecreateVM(wfEngine.getSchedulerId(0), count, mirror, ram, mips, bandwidth, cpu);
            vmlist.addAll(vmlist.size(),vmlistemp);
        }
        return vmlist;
    }

    public void process(String path, int vmNumber, int algorithm, Map<String,Vm> map, int centerNum) {
        try {
            // First step: Initialize the WorkflowSim package.
            /**
             * However, the exact number of vms may not necessarily be vmNum If
             * the data center or the host doesn't have sufficient resources the
             * exact vmNum would be smaller than that. Take care.
             */
            int vmNum = vmNumber;//number of vms;

            /**
             * Should change this based on real physical path
             */
            String daxPath = path;

            File daxFile = new File(daxPath);
            if (!daxFile.exists()) {
                Log.printLine("Warning: Please replace daxPath with the physical path in your working environment!");
                return;
            }

            // 根据下拉列表的索引选择 相应的 SchedulingAlgorithm
            Parameters.SchedulingAlgorithm sch_method = Parameters.SchedulingAlgorithm.FCFS;
            Parameters.AlgorithmIndex = algorithm;

            // TO-DO: 改为根据下拉列表的索引选择 相应的 PlanningAlgorithm
            Parameters.PlanningAlgorithm pln_method = Parameters.PlanningAlgorithm.INVALID;
            ReplicaCatalog.FileSystem file_system = ReplicaCatalog.FileSystem.LOCAL;

            /**
             * No overheads
             */
            OverheadParameters op = new OverheadParameters(0, null, null, null, null, 0);

            /**
             * No Clustering
             */
            ClusteringParameters.ClusteringMethod method = ClusteringParameters.ClusteringMethod.NONE;
            ClusteringParameters cp = new ClusteringParameters(0, 0, method, null);

            /**
             * Initialize static parameters
             */
            Parameters.init(vmNum, daxPath, null,
                    null, op, cp, sch_method, pln_method,
                    null, 0);
            ReplicaCatalog.init(file_system);

            // before creating any entities
            int num_user = 1;   // number of grid users
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false;  // mean trace events

            // Initialize the CloudSim library
            CloudSim.init(num_user, calendar, trace_flag);

            /**
             * Create a datacenter.
             */
            WorkflowDatacenter datacenter0 = createDatacenter("Datacenter_0");

            /**
             * Create a WorkflowPlanner with one schedulers.
             */
            WorkflowPlanner wfPlanner = new WorkflowPlanner("planner_00", 1);

            /**
             * Create a WorkflowEngine.
             */
            WorkflowEngine wfEngine = wfPlanner.getWorkflowEngine();

            /**
             * Create a list of VMs.The userId of a vm is basically the id of
             * the scheduler that controls this vm.
             */
            List<CondorVM> vmlist0 = getDatacenterVM(map,wfEngine);

            /**
             * Submits this list of vms to this WorkflowEngine.
             */
            wfEngine.submitVmList(vmlist0, 0);
            /**
             * Binds the data centers with the scheduler.
             */
            wfEngine.bindSchedulerDatacenter(datacenter0.getId(), 0);
            CloudSim.startSimulation();
            List<Job> outputList0 = wfEngine.getJobsReceivedList();
            CloudSim.stopSimulation();
            CondorVMList = vmlist0;
            taskList = outputList0;
            printJobList(outputList0);
        } catch (Exception e) {
            Log.printLine("The simulation has been terminated due to an unexpected error");
        }
        totalvm=0;

    }

    protected static WorkflowDatacenter createDatacenter(String name) {

        // Here are the steps needed to create a PowerDatacenter:
        // 1. We need to create a list to store one or more
        //    Machines
        List<Host> hostList = new ArrayList<>();

        // 2. A Machine contains one or more PEs or CPUs/Cores. Therefore, should
        //    create a list to store these PEs before creating
        //    a Machine.
        for (int i = 1; i <= 20; i++) {
            List<Pe> peList1 = new ArrayList<>();
            int mips = 2000;
            // 3. Create PEs and add these into the list.
            //for a quad-core machine, a list of 4 PEs is required:
            peList1.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
            peList1.add(new Pe(1, new PeProvisionerSimple(mips)));

            int hostId = 0;
            int ram = 2048; //host memory (MB)
            long storage = 1000000; //host storage
            int bw = 10000;
            hostList.add(
                    new Host(
                            hostId,
                            new RamProvisionerSimple(ram),
                            new BwProvisionerSimple(bw),
                            storage,
                            peList1,
                            new VmSchedulerTimeShared(peList1))); // This is our first machine
            //hostId++;
        }

        // 4. Create a DatacenterCharacteristics object that stores the
        //    properties of a data center: architecture, OS, list of
        //    Machines, allocation policy: time- or space-shared, time zone
        //    and its price (G$/Pe time unit).
        String arch = "x86";      // system architecture
        String os = "Linux";          // operating system
        String vmm = "Xen";
        double time_zone = 10.0;         // time zone this resource located
        double cost = 3.0;              // the cost of using processing in this resource
        double costPerMem = 0.05;		// the cost of using memory in this resource
        double costPerStorage = 0.1;	// the cost of using storage in this resource
        double costPerBw = 0.1;			// the cost of using bw in this resource
        LinkedList<Storage> storageList = new LinkedList<>();	//we are not adding SAN devices by now
        WorkflowDatacenter datacenter = null;

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);

        // 5. Finally, we need to create a storage object.
        /**
         * The bandwidth within a data center in MB/s.
         */
        int maxTransferRate = 15;// the number comes from the futuregrid site, you can specify your bw

        try {
            // Here we set the bandwidth to be 15MB/s
            HarddriveStorage s1 = new HarddriveStorage(name, 1e12);
            s1.setMaxTransferRate(maxTransferRate);
            storageList.add(s1);
            datacenter = new WorkflowDatacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datacenter;
    }

    /**
     * Prints the job objects
     *
     * @param list list of jobs
     */
    protected static void printJobList(List<Job> list) {
        String indent = "    ";
        Log.printLine();
        Log.printLine("========== OUTPUT ==========");
        Log.printLine("Job ID" + indent + "Task ID" + indent + "STATUS" + indent
                + "Data center ID" + indent + "VM ID" + indent + indent
                + "Time" + indent + "Start Time" + indent + "Finish Time" + indent + "Depth");
        DecimalFormat dft = new DecimalFormat("###.##");
        for (Job job : list) {
            Log.print(indent + job.getCloudletId() + indent + indent);
            if (job.getClassType() == Parameters.ClassType.STAGE_IN.value) {
                Log.print("Stage-in");
            }
            for (Task task : job.getTaskList()) {
                Log.print(task.getCloudletId() + ",");
            }
            Log.print(indent);

            if (job.getCloudletStatus() == Cloudlet.SUCCESS) {
                Log.print("SUCCESS");
                Log.printLine(indent + indent + job.getResourceId() + indent + indent + indent + job.getVmId()
                        + indent + indent + indent + dft.format(job.getActualCPUTime())
                        + indent + indent + dft.format(job.getExecStartTime()) + indent + indent + indent
                        + dft.format(job.getFinishTime()) + indent + indent + indent + job.getDepth());
            } else if (job.getCloudletStatus() == Cloudlet.FAILED) {
                Log.print("FAILED");
                Log.printLine(indent + indent + job.getResourceId() + indent + indent + indent + job.getVmId()
                        + indent + indent + indent + dft.format(job.getActualCPUTime())
                        + indent + indent + dft.format(job.getExecStartTime()) + indent + indent + indent
                        + dft.format(job.getFinishTime()) + indent + indent + indent + job.getDepth());
            }
        }
    }
}
