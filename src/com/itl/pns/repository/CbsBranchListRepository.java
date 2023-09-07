package com.itl.pns.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.CbsBranchList;

@Repository
public interface CbsBranchListRepository extends JpaRepository<CbsBranchList, Serializable> {

	@Query(value = "SELECT distinct branchzone, zonecode FROM CbsBranchList WHERE branchCode=:id AND zonecode IS NOT NULL ORDER BY zonecode")
	public List<Object[]> getZonalffice(@Param("id") String id1);

	@Query(value = " FROM CbsBranchList WHERE zonecode IS NOT NULL ORDER BY branchCode")
	public List<CbsBranchList> getBranchOffice();

	@Query(value = "SELECT COUNT(*) FROM CbsBranchList WHERE branchCode=:id AND zonecode IS NOT NULL")
	public int getBranchAvailability(@Param("id") String id1);

	@Query(value = " FROM CbsBranchList WHERE branchCode=:id AND zonecode IS NOT NULL")
	public List<CbsBranchList> getCbsBranchData(@Param("id") String id1);

	@Query(value = "SELECT branchName FROM CbsBranchList WHERE branchCode=:id AND zonecode IS NOT NULL")
	public String getCbsBranchName(@Param("id") String branchCode);

	@Query(value = "SELECT distinct branchCode FROM CbsBranchList WHERE zonecode =:id and zonecode IS NOT NULL ORDER BY branchCode")
	public List<String> getBranchDetail( @Param("id") String repBranch);

	@Query(value = " FROM CbsBranchList WHERE zonecode=:id AND zonecode IS NOT NULL ORDER BY branchCode")
	public List<CbsBranchList> getCbsBranchOfficeZonal( @Param("id") String id1);

	@Query(value = "SELECT distinct zonecode FROM CbsBranchList WHERE zonecode IS NOT NULL ORDER BY zonecode")
	public List<String> getZonalCode();

	@Query(value = "SELECT distinct branchzone FROM CbsBranchList WHERE zonecode=:id AND zonecode IS NOT NULL")
	public String getCbsZonalName(@Param("id") String branchCode);

	@Query(value = "SELECT distinct branchzone, zonecode FROM CbsBranchList WHERE zonecode IS NOT NULL ORDER BY zonecode")
	public List<Object[]> getZonalDetails();
	
	

}
