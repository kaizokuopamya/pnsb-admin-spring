package com.itl.pns.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.itl.pns.bean.ActivityMasterBean;
import com.itl.pns.bean.AdminUserActivityLogs;
import com.itl.pns.bean.ChangePasswordBean;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.FraudReportingBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.UserAddEditRequestBean;
import com.itl.pns.bean.UserDetailsBean;
import com.itl.pns.entity.AccountTypesEntity;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkflowHistoryEntity;
import com.itl.pns.entity.OmniLimitMasterEntity;
import com.itl.pns.entity.Role;
import com.itl.pns.entity.User;
import com.itl.pns.entity.UserCredentialsSessionEntity;

public interface AdministrationDao {


	public List<UserDetailsBean> getUserList();

	public boolean updateUserStatus(UserAddEditRequestBean userAddEditReqBean);
	
	public boolean updateUserStatusDetail(UserAddEditRequestBean userAddEditReqBean);

	public ResponseMessageBean checkUser(UserAddEditRequestBean userAddEditReqBean);

	List<UserDetailsBean> getUserDetails(int id);
	
	public List<ActivityMasterBean> getActivityMaster();

	
	List<Role> getAllRoles();
	


	public ResponseMessageBean getRoleByRoleCodeAndName(String code,
			String displayName);

	  boolean updateRolesStatus(Role role);

	public List<Role> getRoleDetails(BigInteger id);

	public List<Role> getActiveRoles(BigInteger roleType);

	boolean forgetPassword(ChangePasswordBean changebean);
	
	public boolean logoutByUserId(String userid);

	boolean changePassword(ChangePasswordBean changebean);

	public Boolean addActivitySetting(ActivitySettingMasterEntity activity);


	public Boolean updateActivitySettingDetails(ActivitySettingMasterEntity activity);

	public List<AdminUserActivityLogs> getAdminUserActivityLogsDetails();

	public List<AdminUserActivityLogs> getAdminPortalUserActivityLogsDetails();

	public List<AdminWorkflowHistoryEntity> getAdminWorkflowHistoryDetails();
	
	public List<AdminWorkflowHistoryEntity> getAdminWorkflowHistoryDetailsById(int refId, int pageId, BigDecimal createdBy);
	
	
	public boolean addAccountType(AccountTypesEntity accountTypeData);

	public boolean updateAccountType(AccountTypesEntity accountTypeData);

	public List<AccountTypesEntity> getAccountTypeById(int id);

	public List<AccountTypesEntity> getAllAccountTypes();
	

	public List<ActivitySettingMasterEntity> getAllActivitySetting();

	public List<ActivitySettingMasterEntity> getActivitySettingById(int id);
	
	public List<ActivitySettingMasterEntity> getAllActivitySettingByAppId(int appId);
	
	
	public List<ActivitySettingMasterEntity> getAllActivitySettingForAdmin();
	
	public List<ActivitySettingMasterEntity> getAllActivitySettingForAdminByAppID(int appId);
	
	public boolean addOmniLimitMaster(OmniLimitMasterEntity omniData);

	public boolean updateOmniLimitMaster(OmniLimitMasterEntity omniData);

	public List<OmniLimitMasterEntity> getOmniLimitMasterById(int id);

	public List<OmniLimitMasterEntity> getAllOmniLimitMaster();
	
	List<AdminUserActivityLogs> getAdminPortalUserActivityLogsDetailsByDate(DateBean datebean);
	
	public ResponseMessageBean checkIsRoleExist(String displayName);

	public List<Role> getRolesByDisplayName(Role role);
	
	public ResponseMessageBean isLimitExist(OmniLimitMasterEntity omniData);
	
	public ResponseMessageBean isUpdateLimitExist(OmniLimitMasterEntity omniData);
	
	public boolean saveToUserCredentialsSession(String email, String mobile, String userToken, BigDecimal userMasterId, String userName);
	
	public ResponseMessageBean validatePwdRestLink(UserCredentialsSessionEntity user);
	
	public boolean generateOTPForForgetPwd(UserCredentialsSessionEntity user);
	
	public ResponseMessageBean validateOtpAndChangePwd(UserCredentialsSessionEntity user);
	
	public Map<String, String> cbsFreezeAccount(Map<String, String> map) ;

	public List<UserDetailsBean> getAllReporteesUsers(String userCode);

	public List<UserDetailsBean> getAllBranchUsersList(String userCode, String branchCode);

	public boolean updateBranchStatus(String userCode, String oldBranchCode, String newBranchCode, int roleId, String mobileNumber, String emailId);

	boolean updateUserStatusDetail(String userCode, String oldBranchCode, String newBranchCode);

	public List<FraudReportingBean> getFraudRepotingDetails(String date1, String date2);

	public List<UserDetailsBean> getZomnalUserList(String id1);

	public ResponseMessageBean checkUsers(User user);

	
}
