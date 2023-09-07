package com.itl.pns.corp.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.itl.pns.corp.dao.CorpBankTokenDao;
import com.itl.pns.entity.BankTokenEntity;
import com.itl.pns.service.CalculatorService;
import com.itl.pns.service.impl.RestServiceCall;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Repository
@Transactional
public class CorpBankTokenDaoImpl implements CorpBankTokenDao {

	static Logger LOGGER = Logger.getLogger(CorpBankTokenDaoImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	RestServiceCall rest;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	CalculatorService calculatorService;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankTokenEntity> getBankTokenRequestForCorp(BankTokenEntity bankTokenEntity) {
		List<BankTokenEntity> list = null;

		try {
			String sqlquery = "select bk.id, bk.customerId as customerId,bk.branch_Code as branchCode, r.name as userRole, bk.accountNumber as accountNumber, bk.statusId , bk.referencenumber , bk.appId,bk.reqInitiatedFor, bk.createdBy, bk.createdOn,bk.remark as remarkGp, "
					+ "s.name as statusName, c.USER_DISP_NAME as customername, c.PERSONAL_PHONE as mobile, comp.CIF as cif, bk.TypeOfRequest as typeOfRequest, comp.companyName as companyName, comp.is_corporate as isCorporate, comp.approvalLevel as approvalLevel "
					+ "from BANKTOKEN bk inner join corp_users c on c.id=bk.customerId left join CORP_COMPANY_MASTER comp on comp.id=c.CORP_COMP_ID "
					+ "inner join STATUSMASTER s on s.id=bk.statusid left join corp_roles r on r.id=c.corpRoleId where bk.STATUSID in :statusList and bk.BRANCH_CODE =:branchCode order by bk.id desc";

			list = getSession().createSQLQuery(sqlquery).addScalar("id").addScalar("customerId").addScalar("branchCode")
					.addScalar("userRole").addScalar("accountNumber").addScalar("statusId").addScalar("referencenumber")
					.addScalar("appId").addScalar("reqInitiatedFor").addScalar("createdBy").addScalar("createdOn")
					.addScalar("remarkGp").addScalar("statusName").addScalar("customername").addScalar("mobile")
					.addScalar("cif").addScalar("typeOfRequest").addScalar("companyName")
					.addScalar("isCorporate", StandardBasicTypes.CHARACTER).addScalar("approvalLevel")
					.setParameterList("statusList", bankTokenEntity.getStatusList())
					.setParameter("branchCode", bankTokenEntity.getBranchCode())
					.setResultTransformer(Transformers.aliasToBean(BankTokenEntity.class)).list();
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankTokenEntity> getBankTokenByIdForCorp(int id) {
		List<BankTokenEntity> list = null;
		try {
			LOGGER.info("request id from banktoken...." + id);
			String sqlquery = "select bk.id, bk.customerId as customerId,bk.accountNumber as accountNumber, bk.statusId , bk.referencenumber , bk.appId,bk.reqInitiatedFor, bk.createdBy, bk.createdOn,bk.remark as remarkGp, "
					+ " s.name as statusName, c.USER_DISP_NAME as customername, c.PERSONAL_PHONE as mobile, comp.CIF as cif, bk.TypeOfRequest as typeOfRequest, "
					+ "comp.companyName as companyName , comp.is_corporate as isCorporate,bk.branch_Code as branchCode, r.name as roleName, comp.approvalLevel as approvalLevel from BANKTOKEN bk "
					+ "inner join STATUSMASTER s on s.id=bk.statusid inner join corp_users c on c.id=bk.customerId left join corp_roles r on r.id=c.corpRoleId left join CORP_COMPANY_MASTER comp on comp.id=c.CORP_COMP_ID where bk.id=:id";

			list = getSession().createSQLQuery(sqlquery).addScalar("id").addScalar("customerId")
					.addScalar("accountNumber").addScalar("statusId").addScalar("referencenumber").addScalar("appId")
					.addScalar("reqInitiatedFor").addScalar("createdBy").addScalar("createdOn").addScalar("remarkGp")
					.addScalar("statusName").addScalar("customername").addScalar("mobile").addScalar("cif")
					.addScalar("typeOfRequest").addScalar("companyName")
					.addScalar("isCorporate", StandardBasicTypes.CHARACTER)
//					.addScalar("branchCode", StandardBasicTypes.STRING).addScalar("userRole").addScalar("roleName")
					.addScalar("branchCode", StandardBasicTypes.STRING).addScalar("roleName")
					.addScalar("approvalLevel").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(BankTokenEntity.class)).list();
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
		}
		return list;
	}

	@Override
	public List<BankTokenEntity> getBankTokenByIdForRetail(int id) {
		List<BankTokenEntity> list = null;
		try {

			String sqlquery = "select bk.id, bk.customerId as customerId,bk.accountNumber as accountNumber, bk.statusId , bk.referencenumber , bk.appId,bk.reqInitiatedFor,  "
					+ " s.name as statusName, bk.TypeOfRequest as typeOfRequest, bk.USER_ROLE as userRole, bk.CREATEDBY as createdBy, bk.CREATEDON as createdOn, bk.UPDATEDBY as updatedBy, bk.UPDATEDON as updatedOn, "
					+ "bk.branch_Code as branchCode from BANKTOKEN bk inner join STATUSMASTER s on s.id=bk.statusid where bk.id=:id";
			list = getSession().createSQLQuery(sqlquery).addScalar("id").addScalar("customerId")
					.addScalar("accountNumber").addScalar("statusId").addScalar("referencenumber").addScalar("appId")
					.addScalar("reqInitiatedFor").addScalar("statusName").addScalar("typeOfRequest")
					.addScalar("createdBy").addScalar("createdOn").addScalar("updatedBy").addScalar("updatedOn")
					.addScalar("branchCode", StandardBasicTypes.STRING).setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(BankTokenEntity.class)).list();
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean rejectBankTokenForCorp(BankTokenEntity bankToken) {
		boolean success = true;
		try {
			String sqlQuery = "delete from BANKTOKEN where id=:id";
			getSession().createSQLQuery(sqlQuery).setParameter("id", bankToken.getId()).executeUpdate();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;

	}
}
