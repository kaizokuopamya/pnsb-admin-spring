package com.itl.pns.repository;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.entity.ConfigMasterEntity;

@Repository
@Transactional(value = "jpaTransactionManager",propagation = Propagation.REQUIRES_NEW)
public interface ConfigRepository extends JpaRepository<ConfigMasterEntity, Serializable>{
	
	ConfigMasterEntity findByid(int id);

	ConfigMasterEntity findByConfigKeyAndAppId(String configKey,BigDecimal appid);
	
	List<ConfigMasterEntity> findByConfigKey(String configKey);

	
	
}
