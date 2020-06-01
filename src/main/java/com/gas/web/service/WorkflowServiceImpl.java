package com.gas.web.service;

import com.gas.web.dao.PaperDao;
import com.gas.web.dao.WorkflowDao;
import com.gas.web.entity.Paper;
import com.gas.web.entity.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkflowServiceImpl implements IWorkflowService {

    private final WorkflowDao workflowDao;

    @Autowired
    public WorkflowServiceImpl(WorkflowDao workflowDao) {
        this.workflowDao = workflowDao;
    }

    @Override
    public Map<String, Object> workflowFindAll() {
        //构造返回的 JSON 注意有格式要求   参见  resources/static/api/table.json
        Map<String, Object> res = new HashMap<>();
        List<Workflow> workflowData = workflowDao.findAll();
        String resCode = "0";
        if (workflowData == null)
            resCode = "1";
        res.put("code", resCode);
        res.put("msg", "");
        res.put("count", workflowData.size());
        res.put("data", workflowData);
        return res;
    }

    @Override
    public Workflow workflowFindById(Integer id) {
        Optional<Workflow> optional = workflowDao.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<Workflow> workflowFindByInformation(String information) {
        return workflowDao.findByInformation(information);
    }

    @Override
    public Workflow workflowAdd(Workflow workflow) {
        return workflowDao.save(workflow);
    }

    @Override
    public Workflow workflowUpdate(Workflow workflow) {
        return workflowDao.save(workflow);
    }

    @Override
    public void workflowDelete(Integer id) {
        workflowDao.deleteById(id);
    }

    @Override
    public void workflowDeleteBatch(List<Workflow> wfList) {
        List<Integer> idList = new ArrayList<>();
        for (Workflow workflow : wfList) {
            idList.add(workflow.getId());
        }
        workflowDao.deleteBatch(idList);
    }
}
