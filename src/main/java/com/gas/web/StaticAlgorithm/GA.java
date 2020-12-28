package com.gas.web.StaticAlgorithm;

import org.workflowsim.CondorVM;
import org.workflowsim.Task;
import org.workflowsim.WorkflowParser;
import java.util.*;

public class GA {
    public List<Task> taskList;
    public List<CondorVM> vmList; // 由外部传入
    public WorkflowParser parser;
    public ArrayList<Integer> topologyIDtoTask;

    public Integer groupSize;
    public Integer genLen;
    public Integer genMaxValue;
    public Double crossoverProbability;
    public Double mutationProbability;

    public GA(Integer groupSize, List<CondorVM> vmList, Double crossoverProbability, Double mutationProbability) {
        this.groupSize = groupSize;
        this.vmList = vmList;
        this.genMaxValue = vmList.size();
        this.crossoverProbability = crossoverProbability;
        this.mutationProbability = mutationProbability;
        this.parser = new WorkflowParser(0); // daxPath 是通过全局变量传递的不需要传
        parser.parse();
        this.taskList = parser.getTaskList();
        this.genLen = taskList.size();
        Collections.sort(this.taskList, new Comparator<Task>() {
            @Override
            public int compare(Task task, Task t1) {
                return task.getDepth() - t1.getDepth();
            }
        });
    }

    //初始化种群
    public ArrayList<Chromosome> initGroup() {
        //Log.printLine("init group");
        ArrayList<Chromosome> group = new ArrayList<>();
        // 按照 任务的顺序（只按照一个拓扑来求解，应当再对拓扑也进行搜索更好）初始化虚拟机
        for (int i = 0; i < groupSize; ++i) {
            ArrayList<Integer> chm = new ArrayList<>();
            Random random =new Random();
            for (int j = 0; j < genLen; ++j) {
                int value = random.nextInt(genMaxValue);
                chm.add(value);
            }
            Chromosome chromosome = new Chromosome(chm, taskList, vmList);
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

                sonGroup.add(new Chromosome(son1, taskList, vmList));
                sonGroup.add(new Chromosome(son2, taskList, vmList));
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
                sonGroup.add(new Chromosome(newGene, taskList, vmList));
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
        sonGroup.add(new Chromosome(best.getGene(), taskList, vmList, best.getF(), best.result, best.getFinishTime()));
        //轮盘赌选择
        for(int i = 0; i < sonGroupSize-1; i++) {
            Random random = new Random();
            double probability = random.nextDouble();
            int choose;
            for(choose = 1; choose < fitness.length - 1; choose++) {
                if(probability < fitness[choose])
                    break;
            }
            sonGroup.add(new Chromosome(fatherGroup.get(choose).getGene(), taskList, vmList,
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

}
