package com.gas.web.service;

import com.gas.web.dao.ProjectDao;
import com.gas.web.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectServiceImpl implements IProjectService {

    private final ProjectDao projectDao;

    @Autowired
    public ProjectServiceImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public Map<String, Object> projectFindAll() {
        //构造返回的 JSON 注意有格式要求   参见  resources/static/api/table.json
        Map<String, Object> res = new HashMap<>();
        List<Project> projectData = projectDao.findAll();
        String resCode = "0";
        if (projectData == null)
            resCode = "1";
        res.put("code", resCode);
        res.put("msg", "");
        res.put("count", projectData.size());
        res.put("data", projectData);
        return res;
    }

    @Override
    public Project projectFindById(Integer id) {
        Optional<Project> optional = projectDao.findById(id);
        return optional.orElse(null);
    }

    @Override
    public Map<String, Object> projectFindByTitle(String title) {
        Map<String, Object> res = new HashMap<>();
        List<Project> projectData = new ArrayList<Project>();
        List<Integer> projectId = projectDao.findByTitle(title);
        for (Integer id: projectId) {
            projectData.add(projectFindById(id));
        }
        String resCode = "0";
        if (projectData == null)
            resCode = "1";
        res.put("code", resCode);
        res.put("msg", "");
        res.put("count", projectData.size());
        res.put("data", projectData);
        return res;
    }

    @Override
    public Project projectAdd(Project project) {
        return projectDao.save(project);
    }

    @Override
    public Project projectUpdate(Project project) {
        return projectDao.save(project);
    }

    @Override
    public void projectDelete(Integer id) {
        projectDao.deleteById(id);
    }

    @Override
    public void projectDeleteBatch(List<Project> projectList) {
        List<Integer> idList = new ArrayList<>();
        for (Project project : projectList) {
            idList.add(project.getId());
        }
        projectDao.deleteBatch(idList);
    }
}
