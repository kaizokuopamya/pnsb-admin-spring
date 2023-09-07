package com.itl.pns.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.MaskingRulesEntity;

@Repository
public interface MaskingRulesRepository  extends JpaRepository<MaskingRulesEntity, Serializable> {

	

}
