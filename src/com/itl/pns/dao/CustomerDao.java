package com.itl.pns.dao;

import java.sql.Date;
import java.util.List;

import com.itl.pns.bean.CountryRestrictionBean;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.EmailRequestBean;
import com.itl.pns.bean.RegistrationDetailsBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.WalletPointsBean;
import com.itl.pns.entity.ChannelMasterEntity;
import com.itl.pns.entity.CustomerEntity;
import com.itl.pns.entity.CustomerOtherInfoEntity;
import com.itl.pns.entity.OfferDetailsEntity;
import com.itl.pns.entity.RMMASTER;
import com.itl.pns.entity.SecurityQuestionMaster;

public interface CustomerDao {

	List<CountryRestrictionBean> getcountryRestriction(CountryRestrictionBean bean);

	Boolean updateCountryRestrictionStatus(CountryRestrictionBean countryRestrictionBean);

	List<RegistrationDetailsBean> getCustomerDetailsById(int id);

	List<RegistrationDetailsBean> getCustomerDetails(DateBean datebean);

	List<OfferDetailsEntity> getOfferDetails(int statusId);

	List<WalletPointsBean> getWalletPointsData();

	List<WalletPointsBean> getWalletPoints();

	List<WalletPointsBean> getWalletPointsById(int id);

	List<SecurityQuestionMaster> getSecurityQuestions();

	List<SecurityQuestionMaster> getSecurityQuestionById(int id);

	List<OfferDetailsEntity> getOfferDetailsByid(int id);

	List<RegistrationDetailsBean> getCustByCifMobileName(RegistrationDetailsBean detailsBean);

	public boolean saveRMMasterData(RMMASTER rmMaster);

	public List<RMMASTER> getRMMasterData();

	public boolean updateRMMasterData(RMMASTER rmMaster);

	public List<RMMASTER> getRMMasterDataById(int id);

	public boolean deletetRMMasterById(RMMASTER rmMaster);

	public List<ChannelMasterEntity> getChannelList();

	public List<RegistrationDetailsBean> getCustomerType();

	public boolean saveCustOtherInfo(CustomerOtherInfoEntity custOtherInfo);

	public List<CustomerOtherInfoEntity> getCustOtherInfo();

	public boolean updateCustOtherInfo(CustomerOtherInfoEntity custOtherInfo);

	public List<CustomerOtherInfoEntity> getCustOtherInfoById(int id);

	List<CustomerOtherInfoEntity> getCustOtherInfoByCustId(int custId);

	public ResponseMessageBean isRMNameExist(RMMASTER rmMasterData);

	public ResponseMessageBean isUpdateRMNameExist(RMMASTER rmMasterData);

	public boolean sendEmailWithAttachment(EmailRequestBean emailRequestBean);

	public List<RegistrationDetailsBean> getAllCustomers();

	public boolean saveBulkCustomers(List<CustomerEntity> custDataList);

	public ResponseMessageBean checkQuestionExist(SecurityQuestionMaster securityQuestionMaster);

	public ResponseMessageBean checkUpdateQuestionExist(SecurityQuestionMaster securityQuestionMaster);

	List<RegistrationDetailsBean> getAllPendingCustomers();

	public int updateCustomerData(CustomerEntity registrationDetails);

	public boolean updateWorngAAttempsOfCustomers(CustomerEntity customer);

	public boolean sendCustomizeEmailToBulkUsers(EmailRequestBean emailBean);

	public List<RegistrationDetailsBean> getAllCustomerDetails();

	public ResponseMessageBean customerValidation(String mobile, String cif, String accountNumber, String emailId,
			String referenceNumber, String entityId);

	public List<CustomerEntity> getRegistrationDetails(DateBean datebean);

	public List<CustomerEntity> getRetailCustomerDetails(DateBean dateBean);

	public ResponseMessageBean linkDLinkAccounts(String mobile, String accountNumberData, String referenceNumber,
			String entityId);
}
