package com.gas.web.service;

import com.gas.web.entity.Patent;

import java.util.List;
import java.util.Map;

public interface IPatentService {
    Map<String, Object> patentFindAll();

    Patent patentFindById(Integer id);

    List<Patent> patentFindByAge(String enrollmentTime);

    Patent patentAdd(Patent patent);

    Patent patentUpdate(Patent patent);

    void patentDelete(Integer id);

    void patentDeleteBatch(List<Patent> patList);

    Map<String, Object> findByNameLike(String name);

    Map<String, Object> findByDynamicCases(String secondname,String keyword);

    Map<String, Object> findByPatnameLike(String patname);
}
