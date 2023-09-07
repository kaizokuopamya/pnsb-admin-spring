package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.ImpsCustBean;
import com.itl.pns.bean.ImpsOtpLog;
import com.itl.pns.bean.ImpsRevisionBean;
import com.itl.pns.bean.ImpsStationBean;
import com.itl.pns.bean.ImpsTransactionFeeRevisionBean;
import com.itl.pns.bean.ImpsTransactionFeeSetupBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.TransLogBean;
import com.itl.pns.entity.ImpsStatusEntity;
import com.itl.pns.entity.SyslogEntity;
import com.itl.pns.impsEntity.CreditPoolAccountConfEntity;
import com.itl.pns.impsEntity.DebitPoolAccountConfEntity;
import com.itl.pns.impsEntity.DeliveryChannelsEntity;
import com.itl.pns.impsEntity.EeuserEntity;
import com.itl.pns.impsEntity.EeuserRolesEntity;
import com.itl.pns.impsEntity.IfscCodeMasterEntity;
import com.itl.pns.impsEntity.ImpsMasterEntity;
import com.itl.pns.impsEntity.ImpsReportEntity;
import com.itl.pns.impsEntity.ImpsRolesEntity;
import com.itl.pns.impsEntity.ImpsTaskEntity;
import com.itl.pns.impsEntity.ImpsTransactionFeeSetUpEntity;
import com.itl.pns.impsEntity.PermissionEntity;
import com.itl.pns.impsEntity.ReportCatrgoryEntity;
import com.itl.pns.impsEntity.RolePermsEntity;
import com.itl.pns.impsEntity.ScheduleEntity;
import com.itl.pns.impsEntity.SmsTemplatesEntity;
import com.itl.pns.impsEntity.SysconfigEntity;

public interface ImpsServiceDao {

	public List<ImpsStatusEntity> getImpsStatusList();

	public List<ImpsStatusEntity> getImpsStatusById(ImpsStatusEntity impsStatusData);

	public boolean deleteImpsStatusById(ImpsStatusEntity impsStatusData);

	public List<ImpsStatusEntity> getImpsStatusByState(ImpsStatusEntity impsStatusData);

	public boolean updateImpsStatusData(ImpsStatusEntity impsStatusData);

	public List<SyslogEntity> getSyslogs();

	public ResponseMessageBean getStationCount();

	public ResponseMessageBean getTransLogs(DateBean dateObj);

	public ResponseMessageBean getAllTransLog(DateBean dateObj);

	public ResponseMessageBean getImpsTransLog(DateBean dateObj);

	public List<SysconfigEntity> getSysConfigDataById(SysconfigEntity sysConfigReq);

	public List<SysconfigEntity> getAllSysConfigData();

	public boolean insertSysConfigData(SysconfigEntity sysConfigReq);

	public boolean updateSysConfigData(SysconfigEntity sysConfigReq);

	public boolean deleteSysConfigData(SysconfigEntity sysConfigReq);

	public List<EeuserRolesEntity> getEeuserDataByEeuser(EeuserRolesEntity eeUserRoleData);

	public boolean insertEeuserRole(EeuserRolesEntity eeUserRoleData);

	public boolean updateEeuserData(EeuserEntity eeuserData);

	public boolean deleteEeuserRole(EeuserRolesEntity eeuserData);

	public List<PermissionEntity> getPermissionByName(PermissionEntity permissionReq);

	public boolean updatePermission(PermissionEntity permissionReq);

	public boolean insertPermission(PermissionEntity permissionReq);

	public boolean deletePermission(PermissionEntity permissionReq);

	public boolean insertRolePerms(RolePermsEntity rolePermsReq);

	public boolean deleteRolePerms(RolePermsEntity rolePermsReq);

	public List<ImpsRolesEntity> getAllImpsRoles();

	public List<ImpsRolesEntity> getAllImpsRolesById(ImpsRolesEntity impsRolesReq);

	public boolean updateImpsRole(ImpsRolesEntity impsRoleReq);

	public boolean insertImpsRole(ImpsRolesEntity impsRoleReq);

	public boolean deleteImpsRole(ImpsRolesEntity impsRoleReq);

	public List<ImpsStationBean> getStationsByName(ImpsStationBean impsStationData);
	
	public List<ImpsStationBean> getAllStations();

	public List<SmsTemplatesEntity> getSmsTemplates();

	public List<SmsTemplatesEntity> getSmsTemplatesById(SmsTemplatesEntity smsTempReq);

	public boolean updateSmsTemplate(SmsTemplatesEntity impsStationData);

	public List<ImpsTransactionFeeSetupBean> getTransFeeSetupByApplyFeeAndTransType(ImpsTransactionFeeSetupBean transactionFeeSetupReq);

	public ResponseMessageBean getTransactionFeeRevisionCount(ImpsTransactionFeeRevisionBean transFreeReq);

	public List<IfscCodeMasterEntity> getAllIfscCodeDetails();

	public List<IfscCodeMasterEntity> getAllIfscCodeDetailsById(IfscCodeMasterEntity ifscCodeData);

	public boolean insertIfscCode(IfscCodeMasterEntity ifscCodeMaster);

	public boolean updateIfscCode(IfscCodeMasterEntity ifscCodeMaster);

	public boolean deleteIfscCode(IfscCodeMasterEntity ifscCodeMaster);

	public List<DeliveryChannelsEntity> getAllDeliveryChannelDetails();

	public List<DeliveryChannelsEntity> getAllDeliveryChannelDetailsById(DeliveryChannelsEntity deliveryChannelsReq);

	public boolean insertDeliveryChannel(DeliveryChannelsEntity deliverChannelReq);

	public boolean updateDeliveryChannel(DeliveryChannelsEntity deliverChannelReq);

	public boolean deleteDeliveryChannel(DeliveryChannelsEntity deliverChannelReq);

	public List<ImpsRevisionBean> getAllRevisionDetails();

	public List<CreditPoolAccountConfEntity> getCreditPoolAccDetails();

	public List<CreditPoolAccountConfEntity> getCreditPoolAccDetailsById(CreditPoolAccountConfEntity creditPoolAccReq);

	public List<DebitPoolAccountConfEntity> getDebitPoolAccDetailsBYId(DebitPoolAccountConfEntity debitPoolAccReq);

	public boolean insertCreditPoolAccDetails(CreditPoolAccountConfEntity creditPoolAccReq);

	public boolean updateCreditPoolAccDetails(CreditPoolAccountConfEntity creditPoolAccReq);

	public boolean deleteCreditPoolAccDetails(CreditPoolAccountConfEntity creditPoolAccReq);

	public List<DebitPoolAccountConfEntity> getDebitPoolAccDetails();

	public boolean insertDebitPoolAccDetails(DebitPoolAccountConfEntity debitPoolAccReq);

	public boolean updateDebitPoolAccDetails(DebitPoolAccountConfEntity debitPoolAccReq);

	public boolean deleteDebitPoolAccDetails(DebitPoolAccountConfEntity debitPoolAccReq);

	public List<ImpsReportEntity> getAllReports();

	public List<ImpsReportEntity> getAllReportsById(ImpsReportEntity impsReportReq);

	public boolean insertReportData(ImpsReportEntity impsReportReq);

	public boolean updateReportData(ImpsReportEntity impsReportReq);

	public boolean deleteReportData(ImpsReportEntity impsReportReq);

	public List<ImpsTaskEntity> getAllTasks();

	public List<ImpsTaskEntity> getAllTasksById(ImpsTaskEntity taskReq);

	public boolean inserTaskData(ImpsTaskEntity taskReq);

	public boolean updateTaskData(ImpsTaskEntity taskReq);

	public boolean deleteTaskData(ImpsTaskEntity taskReq);

	public List<ScheduleEntity> getScheduleData();

	public List<ScheduleEntity> getScheduleDataById(ScheduleEntity scheduleReq);

	public boolean insertScheduleData(ScheduleEntity scheduleReq);

	public boolean updateScheduleData(ScheduleEntity scheduleReq);

	public boolean deleteScheduleData(ScheduleEntity scheduleReq);

	public List<ReportCatrgoryEntity> getReportCategory();

	public List<ReportCatrgoryEntity> getReportCategoryById(ReportCatrgoryEntity reportCatReq);

	public boolean insertReportCategory(ReportCatrgoryEntity reportCatReq);

	public boolean updateReportCategory(ReportCatrgoryEntity reportCatReq);

	public boolean deleteReportCategory(ReportCatrgoryEntity reportCatReq);

	ResponseMessageBean getImpsCustDetails(ImpsCustBean impsCustReq);

	public ResponseMessageBean getImpsTransLogByRRN(TransLogBean transLogData);

	public List<ImpsOtpLog> getOtpLogsDetails(ImpsOtpLog otpLogReq);

	public List<ImpsTransactionFeeSetupBean> getTransFeeSetupByApplyFeeAndTransTypeById(ImpsTransactionFeeSetupBean transactionFeeSetupReq);

	public boolean updateTransFeeSetup(ImpsTransactionFeeSetUpEntity transactionFeeSetupReq);

	public boolean insertImpsMasterDetails(ImpsMasterEntity impsMasterData);

	public boolean updateImpsMasterDetails(ImpsMasterEntity impsMasterData);

	public List<ImpsMasterEntity> getImpsMasterDetails();

	public List<ImpsMasterEntity> getImpsMasterDetailsById(ImpsMasterEntity impsMasterData);

}
