package com.gas.web.service;

import com.gas.web.dao.HostDao;
import com.gas.web.entity.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostServiceImpl implements IHostService{
    private final HostDao hostDao;

    @Autowired
    public HostServiceImpl(HostDao hostDao) {
        this.hostDao = hostDao;
    }

    @Override
    public void save(List<Host> hostList) {
        hostDao.saveAll(hostList);
    }
}
