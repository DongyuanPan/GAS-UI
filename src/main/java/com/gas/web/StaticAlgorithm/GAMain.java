package com.gas.web.StaticAlgorithm;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.gas.web.controller.EchartsController;
import com.gas.web.util.FileUtil;
import com.gas.web.util.PauseUtil;
import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import org.workflowsim.*;
import org.workflowsim.utils.ClusteringParameters;
import org.workflowsim.utils.OverheadParameters;
import org.workflowsim.utils.Parameters;
import org.workflowsim.utils.ReplicaCatalog;
import org.workflowsim.utils.Parameters.ClassType;

public class GAMain {

   /* protected static List<CondorVM> createVM(int userId, int vms) {
        //Creates a container to store VMs. This list is passed to the broker later
        LinkedList<CondorVM> list = new LinkedList<>();
        //VM Parameters
        long size = 10000; //image size (MB)
        int ram = 512; //vm memory (MB)
        int mips = 1000;
        long bw = 1000;
        int pesNumber = 1; //number of cpus
        String vmm = "Xen"; //VMM name

        //create VMs
        CondorVM[] vm = new CondorVM[vms];
        for (int i = 0; i < vms; i++) {
            double ratio = 1.0;
            vm[i] = new CondorVM(i, 0,userId, mips * ratio, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
            list.add(vm[i]);
        }
        return list;
    }*/

    static int totalvm=0;
    public static List<CondorVM> createVM(int userId, int vms,long s,int r,int m,long band,int cpu) {
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
            //double ratio = bwRandom.nextDouble();
            double ratio=0.2;
            vm[i] = new CondorVM(lastnum,0, userId, mips * ratio, pesNumber, ram, (long) (bw * ratio), size, vmm, new CloudletSchedulerSpaceShared());
            list.add(vm[i]);
            i++;
        }
        return list;
    }


    //创建虚拟机
    public List<CondorVM> getDatacenterVM(Map<String, com.gas.web.entity.Vm> map, int userId ){
        List<CondorVM> vmlist=new ArrayList<>();
        for (com.gas.web.entity.Vm virtual:map.values()) {
            int count=virtual.getCount();
            long mirror=virtual.getMirror();
            int ram=virtual.getRam();
            int mips=virtual.getMips();
            long bandwidth=virtual.getBw();
            int cpu=virtual.getCpu();
            List<CondorVM> vmlistemp = createVM(userId, count, mirror, ram, mips, bandwidth, cpu);
            vmlist.addAll(vmlist.size(),vmlistemp);
        }
        return vmlist;
    }


    ////////////////////////// STATIC METHODS ///////////////////////
    /**
     * Creates main() to run this example This example has only one datacenter
     * and one storage
     */
    public  void process(String workflowPath, Map<String, com.gas.web.entity.Vm> map, String fileLastName) {
        try {
            // First step: Initialize the WorkflowSim package.
            /**
             * However, the exact number of vms may not necessarily be vmNum If
             * the data center or the host doesn't have sufficient resources the
             * exact vmNum would be smaller than that. Take care.
             */
            int vmNum = 5;//number of vms;
            /**
             * Should change this based on real physical path
             */
            //String daxPath = "config\\dax\\Montage_25.xml";
            String daxPath = workflowPath;

            File daxFile = new File(daxPath);
            if (!daxFile.exists()) {
                Log.printLine("Warning: Please replace daxPath with the physical path in your working environment!");
                return;
            }

            /**
             * Since we are using MINMIN scheduling algorithm, the planning
             * algorithm should be INVALID such that the planner would not
             * override the result of the scheduler
             */
            Parameters.SchedulingAlgorithm sch_method = Parameters.SchedulingAlgorithm.MINMIN;
            Parameters.PlanningAlgorithm pln_method = Parameters.PlanningAlgorithm.INVALID;
            ReplicaCatalog.FileSystem file_system = ReplicaCatalog.FileSystem.SHARED;

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

            // before creating any entities.
            int num_user = 1;   // number of grid users
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false;  // mean trace events

            // Initialize the CloudSim library
            CloudSim.init(num_user, calendar, trace_flag);

            WorkflowDatacenter datacenter0 = createDatacenter("Datacenter_0");

            /**
             * Create a WorkflowPlanner with one schedulers.
             */
            WorkflowPlanner wfPlanner = new WorkflowPlanner("planner_0", 1);
            /**
             * Create a WorkflowEngine.
             */
            WorkflowEngine wfEngine = wfPlanner.getWorkflowEngine();
            /**
             * Create a list of VMs.The userId of a vm is basically the id of
             * the scheduler that controls this vm.
             */
            //List<CondorVM> vmlist0 = createVM(wfEngine.getSchedulerId(0), Parameters.getVmNum());
            List<CondorVM> vmlist0 = getDatacenterVM(map, wfEngine.getSchedulerId(0));

            /**
             * Submits this list of vms to this WorkflowEngine.
             */
            wfEngine.submitVmList(vmlist0, 0);

            /**
             * Binds the data centers with the scheduler.
             */
            wfEngine.bindSchedulerDatacenter(datacenter0.getId(), 0);

            JSONObject jsonObject = new JSONObject();
            // GA main
            Integer groupSize = 20;
            Double crossoverProbability = 0.8;
            Double mutationProbability = 0.3;
            GA ga = new GA(groupSize, vmlist0, crossoverProbability, mutationProbability);
            Chromosome theBest;
            // 初始化种群
            ArrayList<Chromosome> group = ga.initGroup();

            for (int i = 0; i < 1000; ++i) {

                if (i != 0) {
                    // 交叉
                    group = ga.crossover(group);

                    // 变异
                    group = ga.mutation(group);
                }

                Double maxF = Double.MIN_VALUE;
                // 计算适应度函数
                for (Chromosome chromosome:group) {
                    chromosome.calFitness();
                    maxF = Math.max(maxF, chromosome.getF());
                }

                // 转换适应度函数，让轮盘赌能够按照完成时间早的被选择的概率大
                for (Chromosome chromosome:group) {
                    chromosome.setF(maxF - chromosome.getF());
                }

                if (i != 0) {
                    // 选择
                    group = ga.selectNextGroup(group, groupSize);
                }

                Chromosome chromosome = ga.best(group);

                printJobList(chromosome.result);
                System.out.println("第" + i + "次迭代:   " + chromosome.getFinishTime());
//                // 睡眠？
                jsonObject.put("code", 200);
                jsonObject.put("data", EchartsController.toDisplay(vmlist0, chromosome.result));
                jsonObject.put("iteratorTimes", i);
                jsonObject.put("finishtime",chromosome.getFinishTime());
                String path = "src\\main\\resources\\StaticAlgorithmResult";
                String fileName = "GA-"+fileLastName;
                if (i == 0) {
                    fileName = "GA-init-"+fileLastName; // 应为表示算法的变量 Parameter.SchedulingAlgorithm
                }

                FileUtil.createJsonFile(jsonObject.toJSONString(), path, fileName);

                if(EchartsController.endGAflag) {
                    System.out.println("endGAMain");
                    return;
                }
                //while (EchartsController.continueGAflag == false);

                Thread.sleep(2000);
                PauseUtil.pauseThread();
            }


        } catch (Exception e) {
            Log.printLine("The simulation has been terminated due to an unexpected error: " + e);
        }
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
            if (job.getClassType() == ClassType.STAGE_IN.value) {
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
