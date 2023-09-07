package com.itl.pns.service;

import java.math.BigInteger;
import java.util.List;

import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.FacilityStatusBean;
import com.itl.pns.bean.LanguageJsonBean;
import com.itl.pns.bean.ProductBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.TransactionLogBean;
import com.itl.pns.bean.UserAccountLeadsBean;
import com.itl.pns.entity.AccountSchemeMasterEntity;
import com.itl.pns.entity.ActivityMaster;
import com.itl.pns.entity.AppMasterEntity;
import com.itl.pns.entity.CertificateConfigEntity;
import com.itl.pns.entity.ConfigMasterEntity;
import com.itl.pns.entity.LanguageJson;
import com.itl.pns.entity.LimitMasterEntity;
import com.itl.pns.entity.MaskingRulesEntity;
import com.itl.pns.entity.OfferDetailsEntity;
import com.itl.pns.entity.Product;
import com.itl.pns.entity.SchedularTransMasterEntity;
import com.itl.pns.entity.StatusMasterEntity;

public interface MasterConfigService {

	List<LanguageJson> getLanguageJson();

	boolean addLanguagejson(LanguageJson languagejson);

	List<LanguageJson> getLanguageJsonByLangCode(LanguageJson languagejson);

	List<LanguageJson> getLanguageJsonByLangText(LanguageJson languagejson);

	List<LanguageJsonBean> getLanguageJsonById(long id);

	List<ConfigMasterEntity> getConfigMaster();

	boolean addConfigMasterDetails(ConfigMasterEntity configMasterEntity);

	boolean addToadminWorkFlowReq(ConfigMasterEntity configMasterEntity, int userStatus);

	boolean updateConfigMaster(ConfigMasterEntity configMasterBean);

	boolean updateToadminWorkFlowReq(ConfigMasterEntity configMasterBean, int userStatus);

	List<ConfigMasterEntity> getConfigById(BigInteger id);

	List<StatusMasterEntity> getStatus();

	List<AppMasterEntity> getAppMasterList();

	boolean updateLanguageJson(LanguageJson languagejson);

	List<OfferDetailsEntity> getOfferDetails();

	List<TransactionLogBean> getAllTransactionLogByDate(DateBean datebean);

	public List<ProductBean> getProducts();

	public List<ProductBean> getProductById(int id);

	public boolean saveProductDetails(Product product);

	public void updateProductDetails(Product product);

	public List<ProductBean> getProductType();

	boolean addFacilityStatus(ActivityMaster activitymaster);

	void updateFacilityStatus(ActivityMaster activitymaster);

	boolean updateFacilityStatusDetails(ActivityMaster activitymaster);

	List<FacilityStatusBean> getAllFacilityStatusById(int id);

	List<FacilityStatusBean> getAllFacilityStatus();

	List<MaskingRulesEntity> getMaskingRulesList();

	boolean updateMaskingRules(MaskingRulesEntity maskingRulesEntity);

	boolean addMaskingRules(MaskingRulesEntity maskingRulesEntity);

	List<MaskingRulesEntity> getMaskingRulesId(int id);

	public ResponseMessageBean checkProduct(Product product);

	public ResponseMessageBean checkUpdateProduct(Product product);

	public boolean addLanguageJsonToAdminWorkFlow(LanguageJson languagejson, int userStatus);

	public boolean addMaskingRulesToAdminWorkFlow(MaskingRulesEntity maskingRulesEntity, int userStatus);

	public boolean addFacilityStatusToAdminWorkFlow(ActivityMaster activitymaster, int userStatus);

	public List<UserAccountLeadsBean> getCustAccountDetailsById(int id);

	public List<UserAccountLeadsBean> getCustAllAccountDetails();

	public List<LanguageJson> getDistinctLanguageJsonCode();

	public boolean updateLanguageJsonList(List<LanguageJson> languageJosn);

	public List<CertificateConfigEntity> getCertificateConfigMaster();

	public List<CertificateConfigEntity> getCertificateConfigById(int id);

	public boolean addCertificateConfigMaster(CertificateConfigEntity certificateConfig);

	public boolean updateCertificateConfigMaster(CertificateConfigEntity certificateConfig);

	public boolean updateAccountSchemeMaster(AccountSchemeMasterEntity accountSchemeMaster);

	public List<AccountSchemeMasterEntity> getAccountSchemeMasterById(int id);

	public List<AccountSchemeMasterEntity> getAccountSchemeMaster();

	public boolean addAccountSchemeMaster(AccountSchemeMasterEntity accountSchemeMaster);

	public boolean addSchedulatTransMaster(SchedularTransMasterEntity schedularData);

	public boolean updateSchedulatTransMaster(SchedularTransMasterEntity schedularData);

	public List<SchedularTransMasterEntity> getSchedulatTransMaster();

	public List<SchedularTransMasterEntity> getSchedulatTransMasterById(int id);

	public List<SchedularTransMasterEntity> getScheduleTransactionDetails();
	
	List<LimitMasterEntity> getLimitMasterDetails(String id1);

	List<AppMasterEntity> getAppListForLimitMaster();

	Boolean addLimitMasterList(LimitMasterEntity limitMaster);

	List<LimitMasterEntity> getLimitMasterDetailsById(String id1);

	Boolean editLimitMaster(LimitMasterEntity limitMaster);

}
