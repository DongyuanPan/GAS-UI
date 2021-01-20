package com.gas.web.StaticAlgorithm;

import org.workflowsim.CondorVM;

import java.util.List;
import java.util.Map;

public abstract class PlanningAlgorithmBase {

    public PlanningAlgorithmBase() {
    }

    /**
     * The main function
     */
    public abstract void run(String workflowPath, List<CondorVM> vmList, String algorithm, String fileLastName) throws Exception;
}
