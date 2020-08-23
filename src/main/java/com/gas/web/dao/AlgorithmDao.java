package com.gas.web.dao;

import com.gas.web.entity.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface AlgorithmDao extends JpaRepository<Algorithm,Integer> {


    @Modifying
    @Transactional
    @Query(value = "delete s from algorithm s where s.id in (:idList)", nativeQuery = true)
    void deleteBatch(List<Integer> idList);
    @Query(value = "select a.id from algorithm a where a.name like %?1%", nativeQuery = true)
    List<Integer> findByNameLike(String name);
}
