package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.User;

@Repository
public interface UserMasterRepository extends JpaRepository<User, Serializable> {

	List<User> findByuserid(String userid);

	User findByid(BigInteger puserid);

	@Query(value = "select distinct branchCode as branchcode from User WHERE branchCode IS NOT NULL ORDER BY branchcode")
	public List<User> getHeadOffice();

	@Query(value = "select distinct reportingBranch from User WHERE branchCode = :id AND reportingBranch IS NOT NULL ORDER BY reportingBranch")
	public List<User> getZonalOffice(@Param("id") String id1);

	@Query(value = "Select distinct branchCode as branchcode from User where reportingbranch = :id AND branchCode IS NOT NULL ORDER BY branchcode")
	public List<User> getBranchOffice(@Param("id") String id1);

	@Query(value = "select count(*) from user_details d INNER JOIN user_master um ON d.USER_MASTER_ID = um.ID where d.email=:email and um.isdeleted='N' and um.statusid=3 and d.statusid=3", nativeQuery = true)
	public int userEmailExistOrNot(@Param("email") String email);

	@Query(value = "select count(*) from user_details d INNER JOIN user_master um ON d.USER_MASTER_ID = um.ID where d.PHONENUMBER=:mobileNo and d.MOBILE_NUMBER=:mobileNo and um.isdeleted='N' and um.statusid=3 and d.statusid=3", nativeQuery = true)
	public int userMobileExistOrNot(@Param("mobileNo") String mobileNo);

	@Query(value = "select count(*) from User where userid=:userid and isdeleted='N'")
	public String userExistOrNot(@Param("userid") String userid);

	@Query(value = " from User where userid=:userid and isdeleted='N'")
	public User userIdDetails(@Param("userid") String userid);

	@Modifying
	@Query(value = "SELECT UM.ID AS USERID1, UM.USERID,UD.FIRST_NAME,UD.LAST_NAME,UD.EMAIL,UD.PHONENUMBER,R.CODE,R.NAME AS ROLE,UM.LOGINTYPE,\r\n"
			+ "R.DESCRIPTION,UM.STATUSID,UM.ID AS USER_ID, UD.ID AS USER_DETAIL_ID, R.ID AS ROLE_ID, R.ROLETYPE,RT.NAME,R.STATUSID AS ROLESTATUS,\r\n"
			+ "UM.BRANCHCODE,CB.BRANCH_NAME,UM.REPORTINGBRANCH,CB.BRANCHZONE\r\n" + "FROM USER_MASTER UM \r\n"
			+ "INNER JOIN USER_DETAILS UD ON UM.ID= UD.USER_MASTER_ID \r\n"
			+ "INNER JOIN ROLES R ON UD.ROLE_ID=R.ID \r\n" + "INNER JOIN ROLE_TYPES RT ON R.ROLETYPE = RT.ID  \r\n"
			+ "INNER JOIN CBS_BRANCH_LIST CB ON UM.BRANCHCODE = CB.BRANCH_CODE and  UM.REPORTINGBRANCH = CB.ZONECODE \r\n"
			+ "WHERE UM.ISDELETED='N' and UM.STATUSID IN (36,122,123) ORDER BY UM.ID DESC", nativeQuery = true)
	public List<Object[]> getAllMakerUsers();

	@Query(value = "from User where id =:userid and statusid=36 and isdeleted='N'")
	public User getUserMakerDetails(@Param("userid") BigInteger bigInteger);

	@Modifying
	@Query(value = "select um.userid AS USERID,r.name as role,um.id as user_id, ud.first_name,ud.last_name,"
			+ " r.id as role_id,r.roletype as roletype,rt.name as roleTypeName,um.branchcode"
			+ " from user_master um inner join user_details ud on um.id= ud.user_master_id"
			+ " inner join  roles r on ud.role_id=r.id inner join role_types rt on r.roletype=rt.id"
			+ " where um.statusid=3 and r.statusid=3 and um.id =:userid", nativeQuery = true)
	public List<Object[]> userDetails(@Param("userid") Integer valueOf);

	@Modifying
	@Query(value = "SELECT UM.ID AS USERID1, UM.USERID,UD.FIRST_NAME,UD.LAST_NAME,UD.EMAIL,UD.PHONENUMBER,R.CODE,R.NAME AS ROLE,UM.LOGINTYPE,\r\n"
			+ "R.DESCRIPTION,UM.STATUSID,UM.ID AS USER_ID, UD.ID AS USER_DETAIL_ID, R.ID AS ROLE_ID, R.ROLETYPE,RT.NAME,R.STATUSID AS ROLESTATUS,\r\n"
			+ "UM.BRANCHCODE,CB.BRANCH_NAME,UM.REPORTINGBRANCH,CB.BRANCHZONE\r\n" + "FROM USER_MASTER UM \r\n"
			+ "INNER JOIN USER_DETAILS UD ON UM.ID= UD.USER_MASTER_ID \r\n"
			+ "INNER JOIN ROLES R ON UD.ROLE_ID=R.ID \r\n" + "INNER JOIN ROLE_TYPES RT ON R.ROLETYPE = RT.ID  \r\n"
			+ "INNER JOIN CBS_BRANCH_LIST CB ON UM.BRANCHCODE = CB.BRANCH_CODE and  UM.REPORTINGBRANCH = CB.ZONECODE \r\n"
			+ "WHERE UM.ISDELETED='N' and UM.STATUSID IN (36,122,123) and CB.ZONECODE =:zonecode ORDER BY UM.ID DESC", nativeQuery = true)
	public List<Object[]> getAllMakerZonalUsers(@Param("zonecode") String id1);

	@Query(value = "SELECT  ROLE_ID FROM USER_MASTER UM INNER JOIN USER_DETAILS UD ON UM.ID= UD.USER_MASTER_ID where UM.USERID =:id and UM.ISDELETED ='N' and um.statusid=3", nativeQuery = true)
	public String getUserRoleIds(@Param("id") String lowerCase);

}
