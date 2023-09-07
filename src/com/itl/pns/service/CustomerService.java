package com.itl.pns.service;

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
import com.itl.pns.entity.WalletPoints;

public interface CustomerService {

	List<CountryRestrictionBean> getcountryRestriction(CountryRestrictionBean bean);

	Boolean updateCountryRestrictionStatus(CountryRestrictionBean countryRestrictionBean);

	List<RegistrationDetailsBean> getCustomerDetails(DateBean datebean);

	List<RegistrationDetailsBean> getCustomerDetailsById(int id);

	List<OfferDetailsEntity> getOfferDetails(int appid);

	Boolean updateRegistrationDetails(CustomerEntity registrationDetails);

	List<WalletPointsBean> getWalletPointsData();

	List<OfferDetailsEntity> getOfferDetailsByid(int id);

	boolean addWalletPoints(WalletPoints walletPoints);

	boolean addRewardPointToAdminWorkFlow(WalletPoints walletPoints, int userStatus);

	public boolean addSecurityQuestionsToAdminWorkFLow(SecurityQuestionMaster securityQuestionMaster, int userStatus);

	List<WalletPointsBean> getWalletPoints();

	boolean updateWalletPoints(WalletPoints walletPoints);

	List<WalletPointsBean> getWalletPointsById(int id);

	List<SecurityQuestionMaster> getSecurityQuestions();

	List<SecurityQuestionMaster> getSecurityQuestionById(int id);

	boolean addSecurityQuestions(SecurityQuestionMaster securityQuestionMaster);

	boolean updateSecurityQuestions(SecurityQuestionMaster securityQuestionMaster);

	List<RegistrationDetailsBean> getCustByCifMobileName(RegistrationDetailsBean detailsBean);

	boolean resetCustPass(CustomerEntity user);

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

	public List<CustomerOtherInfoEntity> getCustOtherInfoByCustId(int CustId);

	public ResponseMessageBean isRMNameExist(RMMASTER rmMasterData);

	public ResponseMessageBean isUpdateRMNameExist(RMMASTER rmMasterData);

	public boolean sendEmailWithAttachment(EmailRequestBean emailRequestBean);

	public List<RegistrationDetailsBean> getAllCustomers();

	public boolean saveBulkCustomers(List<CustomerEntity> custDataList);

	List<RegistrationDetailsBean> getAllPendingCustomers();

	public boolean updateWorngAAttempsOfCustomers(CustomerEntity customer);

	public List<RegistrationDetailsBean> getAllCustomerDetails();

	public ResponseMessageBean customerValidation(String mobile, String cif, String accountNumber, String emailId,
			String referenceNumber, String entityId);

	List<CustomerEntity> getRegistrationDetails(DateBean datebean);

	public List<CustomerEntity> getRetailCustomerDetails(DateBean dateBean);

	public ResponseMessageBean linkDLinkAccounts(String mobile, String accountNumberData, String referenceNumber,
			String entityId);
}
