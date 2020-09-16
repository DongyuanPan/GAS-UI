package com.gas.web.service;

import com.gas.web.entity.Vm;

import java.util.List;
import java.util.Map;

public interface IVmService {
    void save(List<Vm> vmList);
    Map<String, Vm> getAllVm(Integer id);
}
