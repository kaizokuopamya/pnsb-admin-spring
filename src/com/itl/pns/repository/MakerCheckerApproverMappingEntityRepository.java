package com.itl.pns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.MakerCheckerApproverMappingEntity;

@Repository
public interface MakerCheckerApproverMappingEntityRepository
		extends JpaRepository<MakerCheckerApproverMappingEntity, Integer> {

}
