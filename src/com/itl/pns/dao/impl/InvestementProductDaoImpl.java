package com.itl.pns.dao.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.AdminWorkFlowDao;
import com.itl.pns.dao.InvestementProductDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.InvestementProductsEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EncryptDeryptUtility;

@Repository
@Transactional
public class InvestementProductDaoImpl implements InvestementProductDao {

	static Logger LOGGER = Logger.getLogger(HolidayDaoImpl.class);

	@Autowired
	AdminWorkFlowDao adminWorkFlowDao;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<InvestementProductsEntity> getInvestementProducts() {
		List<InvestementProductsEntity> list = null;

		try {
			String sqlQuerry = "select ip.id as id,ip.productName as productName,ip.LOGO as logoClob, ip.productLink as productLink, "
					+ " ip.jsonKey as jsonKey,ip.statusId as statusId, ip.createdon as createdon,ip.createdby as createdby,um.userid as createdByName,"
					+ " ip.updatedon as updatedon,ip.UPDATEDBY as updatedby ,s.name as statusName from INVESTMENT_PRODUCTS ip left join user_master um on um.id=ip.createdby  "
					+ " inner join statusmaster s on s.id=ip.statusid order by ip.id desc";

			list = getSession().createSQLQuery(sqlQuerry).addScalar("id").addScalar("productName").addScalar("logoClob")
					.addScalar("productLink").addScalar("jsonKey").addScalar("statusId").addScalar("createdon")
					.addScalar("createdby").addScalar("createdByName").addScalar("updatedon").addScalar("updatedby")
					.addScalar("statusName")
					.setResultTransformer(Transformers.aliasToBean(InvestementProductsEntity.class)).list();

			for (InvestementProductsEntity cm : list) {
				try {
					String image1 = EncryptDeryptUtility.clobStringConversion(cm.getLogoClob());
					cm.setLogo(image1);
					cm.setLogoClob(null);

				} catch (IOException e) {
					LOGGER.error("Exception Occured ", e);
				} catch (SQLException e1) {
					LOGGER.error("Exception Occured ", e1);
				}
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<InvestementProductsEntity> getInvestementProductById(InvestementProductsEntity investementProductData) {
		List<InvestementProductsEntity> list = null;

		try {
			String sqlQuerry = "select ip.id as id,ip.productName as productName,ip.LOGO as logoClob, ip.productLink as productLink, "
					+ " ip.jsonKey as jsonKey,ip.statusId as statusId, ip.createdon as createdon,ip.createdby as createdby,"
					+ " ip.updatedon as updatedon,ip.UPDATEDBY as updatedby ,s.name as statusName,aw.remark,aw.userAction from INVESTMENT_PRODUCTS ip "
					+ " inner join statusmaster s on s.id=ip.statusid "
					+ "left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='INVESTMENT_PRODUCTS' where ip.id=:id";

			list = getSession().createSQLQuery(sqlQuerry).addScalar("id").addScalar("productName").addScalar("logoClob")
					.addScalar("productLink").addScalar("jsonKey").addScalar("statusId").addScalar("createdon")
					.addScalar("createdby").addScalar("updatedon").addScalar("updatedby").addScalar("statusName")
					.addScalar("remark").addScalar("userAction").setParameter("id", investementProductData.getId())
					.setResultTransformer(Transformers.aliasToBean(InvestementProductsEntity.class)).list();

			for (InvestementProductsEntity cm : list) {
				try {
					String image1 = EncryptDeryptUtility.clobStringConversion(cm.getLogoClob());
					cm.setLogo(image1);
					cm.setLogoClob(null);

				} catch (IOException e) {
					LOGGER.error("Exception Occured ", e);
				} catch (SQLException e1) {
					LOGGER.error("Exception Occured ", e1);
				}
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean addInvestementProducts(InvestementProductsEntity investementProductData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = investementProductData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(investementProductData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(investementProductData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				investementProductData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																					// ADMIN_CHECKER_PENDIN
			}
			investementProductData.setCreatedon(new Date());
			investementProductData.setUpdatedon(new Date());
			session.save(investementProductData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<InvestementProductsEntity> list = getInvestementProducts();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setCreatedByUserId(investementProductData.getUser_ID());
				adminData.setCreatedByRoleId(investementProductData.getRole_ID());
				adminData.setPageId(investementProductData.getSubMenu_ID()); // set
																				// submenuId
																				// as
																				// pageid
				adminData.setCreatedBy(investementProductData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(investementProductData));
				adminData.setActivityId(investementProductData.getSubMenu_ID()); // set
																					// submenuId
																					// as
																					// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(investementProductData.getRemark());
				adminData.setActivityName(investementProductData.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setTableName("INVESTMENT_PRODUCTS");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(investementProductData.getSubMenu_ID(),
						list.get(0).getId(), investementProductData.getCreatedby(), investementProductData.getRemark(),
						investementProductData.getRole_ID(), mapper.writeValueAsString(investementProductData));
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateInvestementProducts(InvestementProductsEntity investementProductData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = investementProductData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(investementProductData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(investementProductData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				investementProductData.setStatusId(BigDecimal.valueOf(statusId));
			}

			investementProductData.setUpdatedon(new Date());
			session.update(investementProductData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(investementProductData.getId().intValue(),
								investementProductData.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setCreatedOn(investementProductData.getCreatedon());
				adminData.setCreatedByUserId(investementProductData.getUser_ID());
				adminData.setCreatedByRoleId(investementProductData.getRole_ID());
				adminData.setPageId(investementProductData.getSubMenu_ID()); // set
																				// submenuId
																				// as
																				// pageid
				adminData.setCreatedBy(investementProductData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(investementProductData));
				adminData.setActivityId(investementProductData.getSubMenu_ID()); // set
																					// submenuId
																					// as
																					// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(investementProductData.getRemark());
				adminData.setActivityName(investementProductData.getActivityName());
				adminData.setActivityRefNo(investementProductData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("INVESTMENT_PRODUCTS");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

					// Save data to admin work flow history
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(investementProductData.getSubMenu_ID(),
							investementProductData.getId(), investementProductData.getCreatedby(),
							investementProductData.getRemark(), investementProductData.getRole_ID(),
							mapper.writeValueAsString(investementProductData));
				}

			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(investementProductData.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), investementProductData.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public ResponseMessageBean isInvestementProductExist(InvestementProductsEntity investementProductData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = " SELECT count(*) FROM INVESTMENT_PRODUCTS WHERE Lower(PRODUCTNAME) =:productName and statusid=3";
			String sqlLinkNameExist = " SELECT count(*) FROM INVESTMENT_PRODUCTS WHERE Lower(PRODUCTLINK) =:productLink and statusid=3";

			List nameExit = getSession().createSQLQuery(sqlNameExist)
					.setParameter("productName", investementProductData.getProductName().toLowerCase()).list();

			List LinkExit = getSession().createSQLQuery(sqlLinkNameExist)
					.setParameter("productLink", investementProductData.getProductLink().toLowerCase()).list();

			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Prooduct Name Is Already Exist ");
			} else if (!(LinkExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Prooduct Link Is Already Exist ");
			} else {

				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	public ResponseMessageBean isUpdateInvestementProductExist(InvestementProductsEntity investementProductData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = " SELECT count(*) FROM INVESTMENT_PRODUCTS WHERE Lower(PRODUCTNAME) =:productName AND id !=:id and statusid=3";
			String sqlLinkNameExist = " SELECT count(*) FROM INVESTMENT_PRODUCTS WHERE Lower(PRODUCTLINK) =:productLink AND id !=:id and statusid=3";

			List nameExit = getSession().createSQLQuery(sqlNameExist).setParameter("id", investementProductData.getId())
					.setParameter("productName", investementProductData.getProductName().toLowerCase()).list();

			List LinkExit = getSession().createSQLQuery(sqlLinkNameExist)
					.setParameter("id", investementProductData.getId())
					.setParameter("productLink", investementProductData.getProductLink().toLowerCase()).list();

			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Prooduct Name Is Already Exist ");
			} else if (!(LinkExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Prooduct Link Is Already Exist ");
			} else {

				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

}
