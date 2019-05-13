package com.example.allomall.repostitory;

import com.example.allomall.entity.Associated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AssociatedRepostitory extends  JpaRepository<Associated,Long> {

    List<Associated> findAssociatedsByPid(Integer pid);

    Associated findAssociatedByPidAndMid(Integer pid,Integer mid);

    Associated findAssociatedByNumberAndPid(Integer number,Integer pid);

    @Transactional
    void deleteAssociatedByPidAndMid(Integer pid,Integer mid);

    @Transactional
    void deleteAssociatedById(Integer id);

}
