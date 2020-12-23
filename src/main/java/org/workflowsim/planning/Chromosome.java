package org.workflowsim.planning;

import java.util.ArrayList;

/***
 * 染色体类 表示一个个体   里面包含基因
 */
public class Chromosome {
    private ArrayList<Integer> gene;  //基因
    private  double f;

    public Chromosome(ArrayList<Integer> gene, double f) {
        this.gene = gene;
        this.f = f;
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
}
