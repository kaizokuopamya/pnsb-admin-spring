package com.itl.pns.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.AccountOpeningDetailsEntity;

@Repository
public interface AccountOpeningDetailsRepository extends JpaRepository<AccountOpeningDetailsEntity, Serializable> {

	@Query(value = " from  AccountOpeningDetailsEntity where cif = :id1 and statusid=40 ORDER BY updatedon DESC")
	public List<AccountOpeningDetailsEntity> getAccountOpeningDetailsCif(@Param("id1") String cifNo);

	@Query(value = " from  AccountOpeningDetailsEntity where mobileNumber = :id1 and statusid=40 ORDER BY updatedon DESC")
	public List<AccountOpeningDetailsEntity> getAccountOpeningDetailsMobileNo(@Param("id1") String id1);

	@Query(value = " from  AccountOpeningDetailsEntity where accountnumber =:id1 and statusid=40 ORDER BY updatedon DESC")
	public List<AccountOpeningDetailsEntity> getAccountOpeningDetailsAccNo(@Param("id1") String accNo);

	@Query(value = "select acc.branchCode, acc.accountnumber, acc.cif, acc.id, acc.firstName,acc.middleName, acc.lastName, acc.mobileNumber, acc.email, acc.panNumber, acc.aadharNumber, acc.updatedon from  AccountOpeningDetailsEntity acc where branchCode =:id1 and statusid=40 and cif is not null ORDER BY updatedon DESC")
	public List<Object[]> getAccountOpeningDetails(@Param("id1") String id1);

}