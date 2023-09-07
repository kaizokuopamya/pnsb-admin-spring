package com.itl.pns.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.UserDeviceDetails;

@Repository
public interface DeviceMasterRepository extends JpaRepository<UserDeviceDetails, Integer> {

	@Query(value = " FROM UserDeviceDetails WHERE customerId =:custId and statusId =:statusId and appId in :appId")
	public UserDeviceDetails findByCustomerIdAndStatusIdAndAppId(@Param("custId") int customerId,
			@Param("statusId") int statusId, @Param("appId") List<BigDecimal> appId);

	@Query(value = " FROM UserDeviceDetails WHERE customerId =:custId and appId =:appId and statusId = '3'")
	UserDeviceDetails getUserDeviceDetails(@Param("custId") Integer custId, @Param("appId") BigDecimal appId);

	@Query(value = " FROM UserDeviceDetails WHERE statusId =:statusId and customerId in :custId and appId in :appId")
	public List<UserDeviceDetails> findByCustomerIdAndStatusIdAndAppId(@Param("statusId") int statusId,
			@Param("custId") List<Integer> customerId, @Param("appId") List<BigDecimal> appId);

}