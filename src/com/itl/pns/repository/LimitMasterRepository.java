package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.itl.pns.entity.LimitMasterEntity;

@Repository
@Transactional
public interface LimitMasterRepository extends JpaRepository<LimitMasterEntity, Serializable>{
	
	@Query(value = "from LimitMasterEntity where APPID=:Id AND CUSTOMERID=0 AND TYPE='G'  ORDER BY LIMIT_NAME")
	public List<LimitMasterEntity> getLimitMasterDetails(@Param("Id") BigDecimal Id);

	@Query(value = "from LimitMasterEntity where id=:Id AND CUSTOMERID=0 AND TYPE='G'")
	public List<LimitMasterEntity> getLimitMasterDetailsById(@Param("Id") BigDecimal limitMasterId);

}
