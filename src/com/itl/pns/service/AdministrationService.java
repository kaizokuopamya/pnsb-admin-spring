package com.itl.pns.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.itl.pns.bean.ActivityMasterBean;
import com.itl.pns.bean.AdminUserActivityLogs;
import com.itl.pns.bean.ChangePasswordBean;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.FraudReportingBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.UserAddEditRequestBean;
import com.itl.pns.bean.UserDetailsBean;
import com.itl.pns.entity.AccountOpeningDetailsEntity;
import com.itl.pns.entity.AccountTypesEntity;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkflowHistoryEntity;
import com.itl.pns.entity.CbsBranchList;
import com.itl.pns.entity.OmniLimitMasterEntity;
import com.itl.pns.entity.Role;
import com.itl.pns.entity.User;
import com.itl.pns.entity.UserDetails;

public interface AdministrationService {

	public List<UserDetailsBean> getUserList();

	public Boolean createUser(UserAddEditRequestBean userAddEditReqBean);

	public boolean updateUserStatus(UserAddEditRequestBean userAddEditReqBean);

	public ResponseMessageBean checkUser(UserAddEditRequestBean userAddEditReqBean);

	List<UserDetailsBean> getUserDetails(int id);

	void addUserDetails(UserAddEditRequestBean userAddEditReqBean);

	boolean deleteUser(BigInteger id);

	public List<User> getTemplateByUserId(String userId);

	List<Role> getAllRoles();

	public List<Role> getRoleDetails(BigInteger id);

	public Boolean modifyRoleDetails(BigInteger roleid);

	public boolean addRoleDetails(Role role);

	public Boolean editRoleDetails(Role role);

	public boolean updateRolesStatus(Role role);

	public ResponseMessageBean getRoleByRoleCodeAndName(String code, String displayName);

	public List<Role> getActiveRoles(BigInteger roleType);

	User editUser(UserAddEditRequestBean userAddEditReqBean);

	ResponseMessageBean editUserDetails(UserAddEditRequestBean userAddEditReqBean);

	boolean resetUserPass(UserDetails user);

	boolean forgetPassword(ChangePasswordBean chanegebean);

	public boolean logoutByUserId(String userid);

	boolean changePassword(ChangePasswordBean chanegebean);

	public Boolean addActivitySetting(ActivitySettingMasterEntity activity);

	public Boolean updateActivitySettingDetails(ActivitySettingMasterEntity activity);

	public List<AdminUserActivityLogs> getAdminUserActivityLogsDetails();

	public List<AdminUserActivityLogs> getAdminPortalUserActivityLogsDetails();

	public List<AdminWorkflowHistoryEntity> getAdminWorkflowHistoryDetails();

	public List<AdminWorkflowHistoryEntity> getAdminWorkflowHistoryDetailsById(int refId, int pageId,
			BigDecimal createdBy);

	public boolean addAccountType(AccountTypesEntity accountTypeData);

	public boolean updateAccountType(AccountTypesEntity accountTypeData);

	public List<AccountTypesEntity> getAccountTypeById(int id);

	public List<AccountTypesEntity> getAllAccountTypes();

	User editUserImage(User user);

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

	public List<ActivityMasterBean> getDistictActivityMaster();

	public List<UserDetailsBean> getAllReporteesUsers(String username);

	public List<UserDetailsBean> getAllBranchUsersList(String id1, String id2);

	boolean updateBranchStatus(String userCode, String oldBranchCode, String newBranchCode, int i, String string,
			String string2);

	public List<User> getHeadOffice();

	public List<User> getZonalOffice(String id);

	public List<User> getBranchOffice(String id);

	public List<AccountOpeningDetailsEntity> getAccountOpeningDetailsCif(String cifNo);

	public List<AccountOpeningDetailsEntity> getAccountOpeningDetailsMobileNo(String id1);

	public List<AccountOpeningDetailsEntity> getAccountOpeningDetailsAccNo(String accNo);

	public List<FraudReportingBean> getFraudRepotingDetails(String date1, String date12);

	public List<Object[]> getCbsZonalOffice(String id1);

	public List<CbsBranchList> getCbsBranchOffice();

	public Boolean getBranchAvailability(String id1);

	public List<CbsBranchList> getCbsBranchData(String id1);

	public String getCbsBranchName(String branchCode);

	public List<Object[]> getAllMakerUsers();

	public User rejectApprovedUserMaster(String id1, String id2, String id3, String id4);

	public ResponseMessageBean rejectApprovedUserDetails(String id1, String id2, String id3, String id4);

	public List<UserDetailsBean> getZomnalUserList(String id1);

	public List<Object[]> getAccountOpeningDetails(String id1);

	public List<CbsBranchList> getCbsBranchOfficeZonal(String id1);

	public ResponseMessageBean checkUsers(User user);

	public List<Object[]> getAllMakerZonalUsers(String id1);

	public String getCbsZonalName(String valueOf);

	public List<Object[]> getZonalDetails();

}
