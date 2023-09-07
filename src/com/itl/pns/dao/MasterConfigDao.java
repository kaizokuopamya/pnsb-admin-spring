package com.itl.pns.dao;

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
import com.itl.pns.entity.MaskingRulesEntity;
import com.itl.pns.entity.OfferDetailsEntity;
import com.itl.pns.entity.Product;
import com.itl.pns.entity.SchedularTransMasterEntity;
import com.itl.pns.entity.StatusMasterEntity;

public interface MasterConfigDao {

	List<LanguageJson> getLanguageJson();

	List<LanguageJson> getLanguageJsonByLangCode(LanguageJson languagejson);

	List<LanguageJson> getLanguageJsonByLangText(LanguageJson languagejson);

	List<LanguageJsonBean> getLanguageJsonById(long id);

	List<ConfigMasterEntity> findAllConfiguration();

	List<ConfigMasterEntity> getConfigById(BigInteger aid);

	List<ConfigMasterEntity> getConfigByConfigKey(String configValue);

	List<StatusMasterEntity> getStatus();

	List<AppMasterEntity> getAppMasterList();

	List<OfferDetailsEntity> getOfferDetails();

	List<TransactionLogBean> getTransactionLog();

	List<TransactionLogBean> getTransactionLogByDate(DateBean datebean);

	public List<ProductBean> getProducts();

	public List<ProductBean> getProductById(int id);

	public boolean saveProductDetails(Product product);

	public List<ProductBean> getProductType();

	List<FacilityStatusBean> getAllFacilityStatusById(int id);

	List<FacilityStatusBean> getAllFacilityStatus();

	List<MaskingRulesEntity> findMaskingRulesList();

	List<MaskingRulesEntity> getMaskingRulesId(int id);

	public ResponseMessageBean checkProduct(Product product);

	public ResponseMessageBean checkUpdateProduct(Product product);

	public List<UserAccountLeadsBean> getCustAccountDetailsById(int id);

	public List<UserAccountLeadsBean> getCustAllAccountDetails();

	public List<LanguageJson> getDistinctLanguageJsonCode();

	public ResponseMessageBean isEnglsihTextExist(LanguageJson languagejson);

	public ResponseMessageBean isUpdateEnglsihTextExist(LanguageJson languagejson);

	public ResponseMessageBean isFacilityStatusExist(ActivityMaster activitymaster);

	public ResponseMessageBean isUpdateFacilityStatusExist(ActivityMaster activitymaster);

	public ResponseMessageBean checkConfigKeyExist(ConfigMasterEntity configMasterEntity);

	public ResponseMessageBean checkUpdateConfigKeyExist(ConfigMasterEntity configMasterEntity);

	public boolean updateLanguageJsonList(List<LanguageJson> languagejson);

	public List<CertificateConfigEntity> getCertificateConfigMaster();

	public List<CertificateConfigEntity> getCertificateConfigById(int id);

	public boolean addCertificateConfigMaster(CertificateConfigEntity certificateConfig);

	public boolean updateCertificateConfigMaster(CertificateConfigEntity certificateConfig);

	public ResponseMessageBean checkConfigKeyExist(CertificateConfigEntity configMasterEntity);

	public boolean updateAccountSchemeMaster(AccountSchemeMasterEntity accountSchemeMaster);

	public List<AccountSchemeMasterEntity> getAccountSchemeMasterById(int id);

	public List<AccountSchemeMasterEntity> getAccountSchemeMaster();

	public boolean addAccountSchemeMaster(AccountSchemeMasterEntity accountSchemeMaster);

	public boolean addSchedulatTransMaster(SchedularTransMasterEntity schedularData);

	public boolean updateSchedulatTransMaster(SchedularTransMasterEntity schedularData);

	public List<SchedularTransMasterEntity> getSchedulatTransMaster();

	public List<SchedularTransMasterEntity> getSchedulatTransMasterById(int id);

	public List<SchedularTransMasterEntity> getScheduleTransactionDetails();
	
	List<AppMasterEntity> getAppListForLimitMaster();

}
