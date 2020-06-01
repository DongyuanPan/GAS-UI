package com.gas.web.service;

import com.gas.web.entity.Student;
import com.gas.web.entity.Workflow;

import java.util.List;
import java.util.Map;

public interface IWorkflowService {
    Map<String, Object> workflowFindAll();

    Workflow workflowFindById(Integer id);

    List<Workflow> workflowFindByInformation(String information);

    Workflow workflowUpdate(Workflow workflow);

    Workflow workflowAdd(Workflow workflow);

    void workflowDelete(Integer id);

    void workflowDeleteBatch(List<Workflow> stuList);
}
