package com.gas.web.dao;

import com.gas.web.entity.Resource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ResourceDao extends JpaRepository<Resource,Integer> {

    @Modifying
    @Transactional
    @Query(value = "delete r from resource r where r.id in (:idList)", nativeQuery = true)
    void deleteBatch(List<Integer> idList);
    @Query(value = "select r.id from resource r where r.vmname like %?1%", nativeQuery = true)
    List<Integer> findByNameLike(String name);
    List<Resource> findAll( Specification<Resource> specification);
}