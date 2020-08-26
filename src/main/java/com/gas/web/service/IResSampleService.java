package com.gas.web.service;

import com.gas.web.entity.ResSample;

import java.util.Map;

public interface IResSampleService {
    ResSample resSampleUpdate(ResSample resample);

    Map<String, Object> findByUser(String user);

    ResSample sampleFindById(Integer id);
}
