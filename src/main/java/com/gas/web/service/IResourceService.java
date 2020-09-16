package com.gas.web.service;

import com.gas.web.entity.Resource;

import java.util.List;
import java.util.Map;

public interface IResourceService {
    Map<String, Object> resourceFindAll();

    Resource resourceFindById(Integer id);

    Resource resourceAdd(Resource resource);

    Resource resourceUpdate(Resource resource);

    void resourceDelete(Integer id);

    void resourceDeleteBatch(List<Resource> resList);

    Map<String, Object> findByNameLike(String name);

    Map<String, Object> vmFindAll(Integer id);

    void deleteAll(Integer resId);

    void updateRes(String resId, int size);
}
