package com.itl.pns.corp.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.ProductBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.dao.CorpProductDao;
import com.itl.pns.dao.impl.TicketsDaoImpl;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.Product;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Repository
@Transactional
public class CorpProductDaoImpl implements CorpProductDao {

	static Logger logger = Logger.getLogger(TicketsDaoImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<ProductBean> getCorpProducts() {

		List<ProductBean> list = null;
		try {
			String sqlQuery = "   select p.id,p.productname as productName ,p.description as description ,p.createdon as createdon,"
					+ "  p.appid as appId,a.shortname as appName ,p.PRODTYPE,p.statusid as statusId,s.name as statusName,p.createdby as createdby, um.USERID as createdByName "
					+ "  from productmaster p"
					+ "  inner join appmaster a  on p.appid=a.id inner join user_master um on p.createdby = um.id"
					+ "  inner join statusmaster s  on p.statusid=s.id  where p.appId=2 order by p.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("productName").addScalar("description")
					.addScalar("appId", StandardBasicTypes.BIG_DECIMAL).addScalar("createdon").addScalar("appName")
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL).addScalar("statusName")
					.addScalar("PRODTYPE", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL).addScalar("createdByName")
					.setResultTransformer(Transformers.aliasToBean(ProductBean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<ProductBean> getCorpProductById(int id) {

		List<ProductBean> list = null;
		try {
			String sqlQuery = "  select p.id,p.productname as productName ,p.description as description ,p.createdon as createdon,"
					+ "  p.appid as appId,a.shortname as appName ,p.PRODTYPE,p.statusid as statusId,s.name as statusName, aw.remark, aw.userAction "
					+ "  from productmaster p"
					+ "  inner join appmaster a  on p.appid=a.id left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=p.id"
					+ "  inner join statusmaster s  on p.statusid=s.id " + "  where p.id=:id";

			// logger.info("sql is --- > "+sqlQuery.toString());

			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("productName").addScalar("description")
					.addScalar("appId", StandardBasicTypes.BIG_DECIMAL).addScalar("appName").addScalar("createdon")
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL).addScalar("statusName")
					.addScalar("PRODTYPE", StandardBasicTypes.BIG_DECIMAL).addScalar("remark")
					.addScalar("userAction", StandardBasicTypes.BIG_DECIMAL).setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(ProductBean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean saveCorpProductDetails(Product product) {
		Session session = sessionFactory.getCurrentSession();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(product.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		int userStatus = product.getStatusId();
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(product.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				product.setStatusId(statusId);
			}

			session.save(product);
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<ProductBean> list = getCorpProducts();
				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedByUserId(product.getUser_ID());
				adminData.setCreatedByRoleId(product.getRole_ID());
				adminData.setPageId(product.getSubMenu_ID()); // set submenuId as pageid
				adminData.setCreatedBy(new BigDecimal(product.getCreatedby()));
				adminData.setContent(mapper.writeValueAsString(product));
				adminData.setActivityId(product.getSubMenu_ID()); // set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
				adminData.setRemark(product.getRemark());
				adminData.setActivityName(product.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setTableName("PRODUCTMASTER");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(product.getSubMenu_ID(), list.get(0).getId(),
						new BigDecimal(product.getCreatedby()), product.getRemark(), product.getRole_ID(),
						mapper.writeValueAsString(product));
			}

			return true;
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
	}

	@Override
	public ResponseMessageBean checkCorpProduct(Product product) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlChannelNameExit = "SELECT count(*) FROM PRODUCTMASTER WHERE Lower(PRODUCTNAME) =:name and appid=:appId";
			List isChannelNameExit = getSession().createSQLQuery(sqlChannelNameExit)
					.setParameter("appId", product.getAppId())
					.setParameter("name", product.getProductName().toLowerCase()).list();

			if (!(isChannelNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Product Already Exist For Same Channel");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both not exist");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	public ResponseMessageBean checkCorpUpdateProduct(Product product) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlChannelNameExit = "SELECT count(*) FROM PRODUCTMASTER WHERE Lower(PRODUCTNAME) =:name AND  ID!=:id  and appid=:appId";
			List isChannelNameExit = getSession().createSQLQuery(sqlChannelNameExit).setParameter("id", product.getId())
					.setParameter("appId", product.getAppId())
					.setParameter("name", product.getProductName().toLowerCase()).list();

			if (!(isChannelNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Product Name Already Exist For Same Channel");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both not exist");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return rmb;
	}
}
