package com.itl.pns.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.itl.pns.bean.BulkUserUploadBean;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.UserDetailsBean;
import com.itl.pns.entity.BulkUsersCreationEntity;
import com.itl.pns.entity.BulkUsersExcelDetailsEntity;
import com.itl.pns.entity.User;


public interface BulkUsersCreationService {

	BulkUserUploadBean createBulkUsersFile(MultipartFile files, Integer id, List<UserDetailsBean> userDetails);

	List<BulkUsersCreationEntity> getBulkUsersData(String id);

	BulkUserUploadBean getBulkUsersValidateUp(List<BulkUsersCreationEntity> bulkUsersUp);

	BulkUserUploadBean getBulkUsersValidateIn(List<BulkUsersCreationEntity> bulkUsersIn);

	Boolean createUserMster(List<BulkUsersCreationEntity> sucess);

	void userDetails(BulkUsersCreationEntity sucess, Integer id);

	boolean editUser(List<BulkUsersCreationEntity> sucess);

	void editUserDetails(BulkUsersCreationEntity userAddEditReqBean, Integer id);

	Boolean getBulkUsersDataUpdate(List<BulkUsersCreationEntity> bulkUsers);

	//void getBulkUsers(Integer id1, List<BigDecimal> listId);
	

	String bulkUsersCreationReject(RequestParamBean requestBean);
	
	ResponseMessageBean checkUser(String user, String email);

	List<BulkUsersExcelDetailsEntity> getBulkUsersExcelDetails(RequestParamBean requestBean);

	Boolean bulkUserExcel(List<BulkUsersCreationEntity> sucess, String excelName, Integer userId, int successCounter, int errorCounter,int totalCounter,String userBranchCode);

	List<BulkUsersCreationEntity> getBulkUsersDataUp(Integer id2);

	List<BulkUsersCreationEntity> getBulkUsersDataIp(Integer id2);

	Boolean getBulkUsers(List<BulkUsersCreationEntity> bulkUsersCreationB);

	void getBulkUsersExcelDataUpdate(BigDecimal excelId, Date date, int successCount, int errorCount, Integer sflags);

	void getBulkUsersExcelDataReject(String id1, Date date, String id2, String id3);

	List<BulkUsersExcelDetailsEntity> getBulkUsersExcelStatus();

	Integer getBulkUsersDataMakerErrorCount(Integer excelId);

	List<BulkUsersCreationEntity> getBulkUsersExcelErrorDetails(String id1);

	List<BulkUsersExcelDetailsEntity> getBulkUsersExcelZonalStatus(String id1);

	List<UserDetailsBean> useruserDetails(Integer id1);
	
}
