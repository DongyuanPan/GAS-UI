package com.gas.web.dao;

import com.gas.web.entity.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface WorkflowDao extends JpaRepository<Workflow,Integer> {

    List<Workflow> findByInformation(String information);

    @Modifying
    @Transactional
    @Query(value = "delete s from workflow s where s.id in (:idList)", nativeQuery = true)
    void deleteBatch(List<Integer> idList);
}
