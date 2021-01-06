package com.gas.web.StaticAlgorithm;

import com.alibaba.fastjson.JSONObject;
import com.gas.web.controller.EchartsController;
import com.gas.web.util.FileUtil;
import com.gas.web.util.PauseUtil;
import org.workflowsim.CondorVM;
import org.workflowsim.Job;
import org.workflowsim.Task;
import org.workflowsim.WorkflowParser;
import org.workflowsim.clustering.BasicClustering;

import java.util.*;

public class GA extends PlanningAlgorithmBase {
    public List<CondorVM> vmList; // 由外部传入
    public WorkflowParser parser;
    public ArrayList<Integer> topologyIDtoTask;

    public Integer groupSize;
    public Integer genLen;
    public Integer genMaxValue;
    public Double crossoverProbability;
    public Double mutationProbability;

    // 必须有无参构造函数，反射才能创建次类
    public GA() {
        this.groupSize = 20;
        this.vmList = new ArrayList<>();
        this.genMaxValue = vmList.size();
        this.crossoverProbability = 0.8;
        this.mutationProbability = 0.1;
    }

    public GA(Integer groupSize, List<CondorVM> vmList, Double crossoverProbability, Double mutationProbability) {
        this.groupSize = groupSize;
        this.vmList = vmList;
        this.genMaxValue = vmList.size();
        this.crossoverProbability = crossoverProbability;
        this.mutationProbability = mutationProbability;
    }

    //初始化种群
    public ArrayList<Chromosome>  initGroup() {
        //Log.printLine("init group");
        ArrayList<Chromosome> group = new ArrayList<>();
        // 按照 任务的顺序（只按照一个拓扑来求解，应当再对拓扑也进行搜索更好）初始化虚拟机
        for (int i = 0; i < groupSize; ++i) {
            List<Job> jobList = getNewJobList();
            this.genLen = jobList.size();

            ArrayList<Integer> chm = new ArrayList<>();
            Random random =new Random();
            for (int j = 0; j < genLen; ++j) {
                int value = random.nextInt(genMaxValue);
                chm.add(value);
            }
            Chromosome chromosome = new Chromosome(chm, jobList, vmList);
            group.add(chromosome);
        }
        return group;
    }

    //交叉后生成 孩子 将孩子加入种群
    public  ArrayList<Chromosome> crossover(ArrayList<Chromosome> fatherGroup) {
        //Log.printLine("crossover");
        ArrayList<Chromosome> sonGroup = new ArrayList<Chromosome>();
        sonGroup.addAll(fatherGroup);
        Random random = new Random();
        for(int k = 0; k < fatherGroup.size() / 2; k++) {
            if(crossoverProbability > random.nextDouble()) {
                int i,j = 0;
                do {
                    i = random.nextInt(fatherGroup.size());
                    j = random.nextInt(fatherGroup.size());
                } while(i == j);

                int s = random.nextInt(genLen);

                ArrayList<Integer> parent1 = fatherGroup.get(i).getGene();
                ArrayList<Integer> parent2 = fatherGroup.get(j).getGene();

                ArrayList<Integer> son1 = new ArrayList<>();
                ArrayList<Integer> son2 = new ArrayList<>();
                for (int n = 0; n < genLen; ++n) {
                    if (n < s) {
                        son1.add(parent1.get(n));
                        son2.add(parent2.get(n));
                    } else {
                        son1.add(parent2.get(n));
                        son2.add(parent1.get(n));
                    }
                }

                sonGroup.add(new Chromosome(son1, getNewJobList(), vmList));
                sonGroup.add(new Chromosome(son2, getNewJobList(), vmList));
            }
        }
        return sonGroup;
    }

    //变异
    public ArrayList<Chromosome> mutation(ArrayList<Chromosome> fatherGroup) {
        //Log.printLine("mutation");
        ArrayList<Chromosome> sonGroup = new ArrayList<Chromosome>();
        sonGroup.addAll(fatherGroup);
        Random random = new Random();
        for(Chromosome c : fatherGroup) {
            //让基因最优秀的不参加变异
            if (random.nextDouble() < mutationProbability){
                ArrayList<Integer> newGene = new ArrayList<>();
                for (Integer item:c.getGene()) {
                    newGene.add(new Integer(item.intValue()));
                }
                int i = 0,j = 0;
                do {
                    i = random.nextInt(newGene.size());
                    j = random.nextInt(newGene.size());
                } while(i == j);

                int temp = newGene.get(i);
                newGene.set(i, newGene.get(j));
                newGene.set(j, temp);
                sonGroup.add(new Chromosome(newGene, getNewJobList(), vmList));
            }

        }

        return sonGroup;
    }

    public ArrayList<Chromosome> selectNextGroup(ArrayList<Chromosome> fatherGroup, int sonGroupSize) {
        //Log.printLine("selectNextGroup");
        ArrayList<Chromosome> sonGroup = new ArrayList<Chromosome>();
        int totalFitness = 0;
        double[] fitness = new double[fatherGroup.size()];
        for(Chromosome chrom : fatherGroup) {
            totalFitness += chrom.getF();
        }
        int index = 0;
        //计算适应度
        for(Chromosome chrom : fatherGroup) {
            fitness[index] = chrom.getF() / ((double)totalFitness);
            index++;
        }

        //计算累加适应度
        for(int i = 1; i < fitness.length; i++) {
            fitness[i] = fitness[i-1]+fitness[i];
        }
        // 选择最好的保留
        Chromosome best = best(fatherGroup);
        sonGroup.add(new Chromosome(best.getGene(), best.jobList, vmList, best.getF(), best.result, best.getFinishTime()));
        //轮盘赌选择
        for(int i = 0; i < sonGroupSize; i++) {
            Random random = new Random();
            double probability = random.nextDouble();
            int choose;
            for(choose = 1; choose < fitness.length - 1; choose++) {
                if(probability < fitness[choose])
                    break;
            }
            sonGroup.add(new Chromosome(fatherGroup.get(choose).getGene(), fatherGroup.get(choose).jobList, vmList,
                    fatherGroup.get(choose).getF(), fatherGroup.get(choose).result, fatherGroup.get(choose).getFinishTime()));
        }
        return sonGroup;
    }

    public Chromosome best(ArrayList<Chromosome> group) {
        Chromosome bestOne = group.get(0);
        for(Chromosome c : group) {
            if(c.getFinishTime() < bestOne.getFinishTime())
                bestOne = c;
        }
        return bestOne;
    }

    // 返回一个独立的jobList 给个体（染色体）
    // 更好的方法是 使用深拷贝
    public List<Job> getNewJobList() {
        // 每次都解析，让每个个题的任务列表互相不影响，但是vmList 是共用一个引用副本的
        // 解析xml的工作流任务
        WorkflowParser parser = new WorkflowParser(0); // daxPath 是通过全局变量传递的不需要传
        parser.parse();
        List<Task> taskList = parser.getTaskList();

        // 保持和workflowsim的以job为单位处理（job是task 的批量提交），直接使用一个job一个task的聚合方式
        BasicClustering clustering = new BasicClustering();
        clustering.setTaskList(taskList);
        clustering.run(); // 之后 通过 clustering.getJobList() 可以得到转化为Job的Task 依赖关系等都会保留 以Job为单位处理
        List<Job> jobList = clustering.getJobList();

        Collections.sort(jobList, new Comparator<Task>() {
            @Override
            public int compare(Task task, Task t1) {
                return task.getDepth() - t1.getDepth();
            }
        });
//        for (Job job:jobList) {
//            System.out.print(job.getTaskList().get(0).getTaskName() + " ");
//        }
//        System.out.println();
        return jobList;
    }

    /**
     * The main function
     */
    @Override
    public void run(String workflowPath, List<CondorVM> vmList, String algorithm, String fileLastName) throws Exception {
        JSONObject jsonObject = new JSONObject();
        // GA main
        Integer groupSize = 20;
        Double crossoverProbability = 0.8;
        Double mutationProbability = 0.3;
        GA ga = new GA(groupSize, vmList, crossoverProbability, mutationProbability);
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

            System.out.println("第" + i + "次迭代:   " + chromosome.getFinishTime());
            jsonObject.put("code", 200);
            jsonObject.put("data", EchartsController.toDisplay(vmList, chromosome.result));
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
    }
}
