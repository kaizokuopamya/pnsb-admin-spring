package com.itl.pns.corp.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.ProductBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.dao.CorpProductDao;
import com.itl.pns.corp.service.CorpProductService;
import com.itl.pns.dao.MasterConfigDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.Product;
import com.itl.pns.repository.ActivityMasterRepository;
import com.itl.pns.repository.ProductRepository;
import com.itl.pns.service.impl.LocationServiceImpl;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Service
@Component
public class CorpProductServiceImpl implements CorpProductService {
	static Logger LOGGER = Logger.getLogger(LocationServiceImpl.class);

	
	@Autowired
	CorpProductDao corpProductDao;
	
	@Autowired
	MasterConfigDao masterConfigDao;
	
	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;
	
	@Autowired
	AdminWorkFlowReqUtility adminWorkFLowReqUtility;
	
	@Autowired
	ActivityMasterRepository activitymasterrepository;
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public List<ProductBean> getCorpProducts() {
	
		return corpProductDao.getCorpProducts();
	}

	@Override
	public List<ProductBean> getCorpProductById(int id) {
		return corpProductDao.getCorpProductById(id);
	}

	@Override
	public boolean saveCorpProductDetails(Product product) {
	    try{
        	product.setCreatedon(new Date());
        	return corpProductDao.saveCorpProductDetails(product);
        
        }
        catch(Exception e){
        	LOGGER.info("Exception:", e);
        	return false;
        }
		
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void updateCorpProductDetails(Product product) {
		int userStatus = product.getStatusId();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(product.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData =  adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(product.getActivityName());
		
		try {
			if(roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				product.setStatusId(statusId);   //97-  ADMIN_CHECKER_PENDIN
			}
			
			Product prod = productRepository.getOne(product.getId());

			prod.setProductName(product.getProductName());
			prod.setDescription(product.getDescription());
			prod.setAppId(product.getAppId());
			prod.setStatusId(product.getStatusId());
			prod.setProdType(product.getProdType());

			productRepository.save(prod);
			
			if(roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(product.getId().intValue(),product.getSubMenu_ID());
				
				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			
				adminData.setCreatedOn(prod.getCreatedon());
				adminData.setCreatedByUserId(product.getUser_ID());
				adminData.setCreatedByRoleId(product.getRole_ID());
				adminData.setPageId(product.getSubMenu_ID());       //set submenuId as pageid
				adminData.setCreatedBy(BigDecimal.valueOf(prod.getCreatedby()));
				adminData.setContent(mapper.writeValueAsString(product));   
				adminData.setActivityId(product.getSubMenu_ID());  //set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
				adminData.setRemark(product.getRemark());
				adminData.setActivityName(product.getActivityName());
				adminData.setActivityRefNo(BigDecimal.valueOf(product.getId()));
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("PRODUCTMASTER");
				if(ObjectUtils.isEmpty(AdminDataList)){
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				}else if(!ObjectUtils.isEmpty(AdminDataList)){
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
					
					
				}
				//Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(product.getSubMenu_ID(),
				new BigDecimal(product.getId()), new BigDecimal(prod.getCreatedby()),
				product.getRemark(), product.getRole_ID(),mapper.writeValueAsString(product));
		
			}else{
				//if record is present in admin work flow then update status
				 adminWorkFlowReqUtility.getCheckerDataByctivityRefId(BigInteger.valueOf(product.getId()),
				 BigInteger.valueOf(userStatus),product.getSubMenu_ID());
			}
			
			
			

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		
	}

	@Override
	public ResponseMessageBean checkCorpProduct(Product product) {
		
		return corpProductDao.checkCorpProduct(product);
	}

	@Override
	public ResponseMessageBean checkCorpUpdateProduct(Product product) {
		return corpProductDao.checkCorpUpdateProduct(product);
	}
	
}
