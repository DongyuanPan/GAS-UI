package com.gas.web.dao;

import com.gas.web.entity.Resource;
import com.gas.web.entity.Vm;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface VmDao extends JpaRepository<Vm,Integer> {

    @Modifying
    @Transactional
    @Query(value = "delete v from vm v where v.id in (:idList)", nativeQuery = true)
    void deleteBatch(List<Integer> idList);
    @Query(value = "select v.id from vm v where v.resId=?1 and v.hostId=?2 ", nativeQuery = true)
    List<Integer> getVmByHostId(Integer resId, Integer hostId);

    @Modifying
    @Transactional
    @Query(value = "delete v from vm v where v.resId in (:resList)", nativeQuery = true)
    void deleteBatchByRes(List<Integer> resList);
}
