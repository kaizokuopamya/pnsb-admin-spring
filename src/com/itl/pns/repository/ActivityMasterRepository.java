package com.itl.pns.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.ActivityMaster;

@Repository
public interface ActivityMasterRepository extends  JpaRepository<ActivityMaster, Integer>{

	List<ActivityMaster> findByappid(int i);

	List<ActivityMaster> findByappidOrderByActivitycode(int i);

}