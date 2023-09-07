package com.itl.pns.dao.impl;

import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.dao.DonationDao;
import com.itl.pns.entity.BeneficaryMasterEntity;
import com.itl.pns.entity.Donation;

@Repository("DonationDAO")
@Transactional
public class DonationDaoImpl implements DonationDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	private static final Logger logger = LogManager.getLogger(DonationDaoImpl.class);

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Donation> getDonations() {
		logger.info("in getDonations()");
		List<Donation> list = null;
		try {
			String sqlQuery = "   select d.id,d.account_number as accountNumber,d.bankingType,d.createdon ,d.name as name ,d.BANKINGTYPE as bankingType ,d.statusid as statusId,s.name as statusName,d.createdby as createdby, um.USERID as createdByName ,"
					+ "d.category as category" + "  from DONATION_PRD d"
					+ "  inner join statusmaster s  on d.statusid=s.id inner join user_master um on d.createdby = um.id where d.bankingType='RETAIL' order by d.createdon desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("accountNumber").addScalar("name")
					.addScalar("category").addScalar("bankingType").addScalar("createdon").addScalar("statusId")
					.addScalar("statusName").addScalar("createdby").addScalar("createdByName")
					.setResultTransformer(Transformers.aliasToBean(Donation.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Donation> getDonationById(int id) {
		logger.info("in getDonations()");
		List<Donation> list = null;
		try {
			String sqlQuery = " select d.id,d.account_number as accountNumber ,d.BANKINGTYPE,d.name as name ,d.bankingtype as bankingType,d.createdon,d.statusid as statusId,s.name as statusName, um.USERID as createdByName ,aw.remark,aw.userAction "
					+ "  ,d.category as category from DONATION_PRD d "
					+ "  inner join statusmaster s  on d.statusid=s.id "
					+ "  inner join user_master um on d.createdby = um.id left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='DONATION_PRD' "
					+ "  where d.id=:id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("category")
					.addScalar("accountNumber").addScalar("name").addScalar("bankingType").addScalar("createdon")
					.addScalar("statusId").addScalar("createdByName").addScalar("remark").addScalar("userAction")
					.addScalar("statusName").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(Donation.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;

	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseMessageBean checkIsAccNoExistForSameTrustName(Donation donation) {

		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlCalculartorNameExist = " SELECT count(*) FROM DONATION_PRD WHERE  ACCOUNT_NUMBER=:accNo   AND bankingType =:bankType";

			List nameExit = getSession().createSQLQuery(sqlCalculartorNameExist)
					.setParameter("accNo", donation.getAccountNumber())
					.setParameter("bankType", donation.getBankingType()).list();

			if (!(nameExit.get(0).toString().equalsIgnoreCase("0")) || ObjectUtils.isEmpty(nameExit)) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Company Name Already Exist For Same Account");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseMessageBean checIsAccNoExistForSameTrustNameWhileUpdate(Donation donation) {

		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlCalculartorNameExist = " SELECT count(*) FROM DONATION_PRD WHERE ACCOUNT_NUMBER=:accNo  AND id !=:id AND bankingType =:bankType";

			List nameExit = getSession().createSQLQuery(sqlCalculartorNameExist)
					.setParameter("accNo", donation.getAccountNumber())
					.setParameter("bankType", donation.getBankingType()).setParameter("id", donation.getId()).list();

			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Company Name Already Exist For Same Account");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	public List<BeneficaryMasterEntity> getBeneficaryList() {
		logger.info("in getDonations()");
		List<BeneficaryMasterEntity> list = null;
		try {
			String sqlQuery = "		select b.id as id, b.beneficiarytype as beneficiarytype, b.beneficiaryname as beneficiaryname,b.bankcode as bankcode,"
					+ "	b.BEN_MOBILENO as ben_mobileno, b.BEN_ACCOUNTNUMBER as ben_accountnumber, b.BEN_NICKNAME as ben_nickname, "
					+ "	b.createdon as createdon,b.CREATEDBY as createdby, b.updatedon as updatedon,b.updatedby as updatedby,b.currency as currency,"
					+ "	b.CITYID as cityid,b.BRANCHNAME as branchname, b.MAXAMOUNT as maxamount, b.IFSCCODE as ifsccode, b.SWIFTCODE as swiftcode,"
					+ "	b.CUSTOMERID as customerid,b.statusid as statusid,b.VPA as vpa,b.MMID as mmid,b.BENE_CREDIT_CARD_NO as bene_credit_card_no,"
					+ "	b.BANK_NAME as bank_name,b.CORPCOMPID as corpcompid,b.CIFNUMBER as cifnumber ,"
					+ "    s.name as statusName, um.USERID as createdByName  from BENEFICIARYMASTER b "
					+ "     left join statusmaster s  on b.statusid=s.id left join user_master um on b.createdby = um.id where b.beneficiarytype=5";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("beneficiarytype")
					.addScalar("beneficiaryname").addScalar("bankcode").addScalar("ben_mobileno")
					.addScalar("ben_accountnumber").addScalar("ben_nickname").addScalar("createdon")
					.addScalar("createdby").addScalar("updatedon").addScalar("updatedby").addScalar("currency")
					.addScalar("cityid").addScalar("branchname").addScalar("maxamount").addScalar("ifsccode")
					.addScalar("swiftcode").addScalar("customerid").addScalar("statusid").addScalar("vpa")
					.addScalar("mmid").addScalar("bene_credit_card_no").addScalar("bank_name").addScalar("corpcompid")
					.addScalar("cifnumber").addScalar("statusName").addScalar("createdByName")
					.setResultTransformer(Transformers.aliasToBean(BeneficaryMasterEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<BeneficaryMasterEntity> getBeneficaryById(int id) {
		logger.info("in getDonations()");
		List<BeneficaryMasterEntity> list = null;
		try {
			String sqlQuery = "	select b.id as id, b.beneficiarytype as beneficiarytype, b.beneficiaryname as beneficiaryname,b.bankcode as bankcode,"
					+ "	b.BEN_MOBILENO as ben_mobileno, b.BEN_ACCOUNTNUMBER as ben_accountnumber, b.BEN_NICKNAME as ben_nickname, "
					+ "	b.createdon as createdon,b.CREATEDBY as createdby, b.updatedon as updatedon,b.updatedby as updatedby,b.currency as currency,"
					+ "	b.CITYID as cityid,b.BRANCHNAME as branchname, b.MAXAMOUNT as maxamount, b.IFSCCODE as ifsccode, b.SWIFTCODE as swiftcode,"
					+ "	b.CUSTOMERID as customerid,b.statusid as statusid,b.VPA as vpa,b.MMID as mmid,b.BENE_CREDIT_CARD_NO as bene_credit_card_no,"
					+ "	b.BANK_NAME as bank_name,b.CORPCOMPID as corpcompid,b.CIFNUMBER as CIFNUMBER ,"
					+ "s.name as statusName, um.USERID as createdByName,aw.remark,aw.userAction  from BENEFICIARYMASTER b "
					+ "     left join statusmaster s  on b.statusid=s.id left join user_master um on b.createdby = um.id"
					+ "    left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='BENEFICIARYMASTER'  where b.id=:id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("beneficiarytype")
					.addScalar("beneficiaryname").addScalar("bankcode").addScalar("ben_mobileno")
					.addScalar("ben_accountnumber").addScalar("ben_nickname").addScalar("createdon")
					.addScalar("createdby").addScalar("updatedon").addScalar("updatedby").addScalar("currency")
					.addScalar("cityid").addScalar("branchname").addScalar("maxamount").addScalar("ifsccode")
					.addScalar("swiftcode").addScalar("customerid").addScalar("statusid").addScalar("vpa")
					.addScalar("mmid").addScalar("bene_credit_card_no").addScalar("bank_name").addScalar("corpcompid")
					.addScalar("cifnumber").addScalar("statusName").addScalar("createdByName").addScalar("remark")
					.addScalar("userAction").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(BeneficaryMasterEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

}
