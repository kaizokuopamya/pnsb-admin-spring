package com.itl.pns.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itl.pns.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Serializable> {

	public CustomerEntity findBymobile(String string);

	@Query(value = "select c.ID, c.APPID, c.MOBILE FROM CUSTOMERS c WHERE STATUSID =:statusId AND c.MOBILE is not null and c.MOBILE<>'NA' and c.MOBILE<>'na'", nativeQuery = true)
	public List<Object[]> findByStatusId(@Param("statusId") Integer statusId);

	@Query(value = " FROM CustomerEntity WHERE mobile =:mobile and statusid =:statusId")
	public CustomerEntity findByMobileNoAndStatusid(@Param("mobile") String mobile,
			@Param("statusId") Integer statusId);

}
