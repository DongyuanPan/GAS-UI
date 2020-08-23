package com.gas.web.service;

import com.gas.web.entity.Algorithm;

import java.util.List;
import java.util.Map;

public interface IAlgorithmService {
    Map<String, Object> algorithmFindAll();

    Algorithm algorithmFindById(Integer id);

     Algorithm algorithmAdd(Algorithm algorithm);

    Algorithm algorithmUpdate(Algorithm algorithm);

    void algorithmDelete(Integer id);

    void algorithmDeleteBatch(List<Algorithm> algorithmList);

    Map<String, Object> findByNameLike(String name);

}
