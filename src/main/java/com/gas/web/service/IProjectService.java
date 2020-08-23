package com.gas.web.service;

import com.gas.web.entity.Project;

import java.util.List;
import java.util.Map;

public interface IProjectService {
    Map<String, Object> projectFindAll();

    Project projectFindById(Integer id);

    Map<String, Object> projectFindByTitle(String title);

    Project projectAdd(Project project);

    Project projectUpdate(Project project);

    void projectDelete(Integer id);

    void projectDeleteBatch(List<Project> projectList);
}
