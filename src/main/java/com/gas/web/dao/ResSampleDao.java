package com.gas.web.dao;

import com.gas.web.entity.ResSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ResSampleDao extends JpaRepository<ResSample,Integer> {
    @Modifying
    @Transactional
    @Query(value = "select r.id from resSample r where r.user like %?1%", nativeQuery = true)
    List<Integer> findByUser(String user);
    @Modifying
    @Transactional
    @Query(value = "delete r from resource r where r.id in (:idList)", nativeQuery = true)
    void deleteBatch(List<ResSample> idList);
}
