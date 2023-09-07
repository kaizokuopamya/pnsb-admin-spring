package com.itl.pns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itl.pns.bean.CorpUserBean;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.FinancialCountMobBean;
import com.itl.pns.bean.OltasTinBean;
import com.itl.pns.bean.Payaggregatorbean;
import com.itl.pns.bean.ReconcileBillDeskBean;
import com.itl.pns.bean.SftpFileStatuses;
import com.itl.pns.bean.SolePropritorRegistrationBean;
import com.itl.pns.bean.Tin2;
import com.itl.pns.bean.TinMultilevelCallBackLogs;
import com.itl.pns.bean.TinServicesData;
import com.itl.pns.bean.TimestampDateBean;
import com.itl.pns.dao.FinancialCountMobDao;
import com.itl.pns.service.FinancialCountMobService;

@Service
public class FinancialCountMobServiceImpl implements FinancialCountMobService {

	@Autowired
	private FinancialCountMobDao financialCountMobDaoImpl;

	@Override
	public List<FinancialCountMobBean> getTotalFinancialTransaction(DateBean datebean) {
		return financialCountMobDaoImpl.getTotalFinancialTransaction(datebean);
	}

	@Override
	public List<FinancialCountMobBean> getTotalNonfinancialTransaction(DateBean datebean) {
		return financialCountMobDaoImpl.getTotalNonfinancialTransaction(datebean);
	}

	@Override
	public List<FinancialCountMobBean> getTotalNonfinancialTransactionExclude(DateBean datebean) {
		return financialCountMobDaoImpl.getTotalNonfinancialTransactionExclude(datebean);
	}

	@Override
	public List<FinancialCountMobBean> getTotalCountFromCustomers(FinancialCountMobBean financialCountMobBean) {
		return financialCountMobDaoImpl.getTotalCountFromCustomers(financialCountMobBean);
	}

	@Override
	public List<SolePropritorRegistrationBean> getSolePropritorRegistrationDetails(DateBean datebean) {
		return financialCountMobDaoImpl.getSolePropritorRegistrationDetails(datebean);
	}

	@Override
	public List<CorpUserBean> getSolePropritorRegistrationCount(DateBean datebean) {
		return financialCountMobDaoImpl.getSolePropritorRegistrationCount(datebean);
	}

	@Override
	public List<FinancialCountMobBean> getTransactionsById(FinancialCountMobBean financialCountMobBean) {

		return financialCountMobDaoImpl.getTransactionsById(financialCountMobBean);
	}

	@Override
	public List<FinancialCountMobBean> getFundTransferDetails(FinancialCountMobBean financialCountMobBean) {
		return financialCountMobDaoImpl.getFundTransferDetails(financialCountMobBean);
	}

	@Override
	public List<FinancialCountMobBean> getRibandRmob() {
		return financialCountMobDaoImpl.getRibandRmob();
	}

	@Override
	public List<FinancialCountMobBean> getTotalCustRegWithRibRmob(FinancialCountMobBean financialCountMobBean) {
		return financialCountMobDaoImpl.getTotalCustRegWithRibRmob(financialCountMobBean);
	}

	@Override
	public List<FinancialCountMobBean> getTotalActiveCustWithRibRmob(FinancialCountMobBean financialCountMobBean) {
		return financialCountMobDaoImpl.getTotalActiveCustWithRibRmob(financialCountMobBean);
	}

	@Override
	public List<Payaggregatorbean> getMerchantNameDropdown() {
		return financialCountMobDaoImpl.getMerchantNameDropdown();
	}

	@Override
	public List<Payaggregatorbean> getPaymentaggDataByMerchantname(Payaggregatorbean payaggregatorbean) {
		return financialCountMobDaoImpl.getPaymentaggDataByMerchantname(payaggregatorbean);
	}

	@Override
	public List<TimestampDateBean> getTableNameDropdown() {
		return financialCountMobDaoImpl.getTableNameDropdown();
	}

	@Override
	public List<TinServicesData> getTinServiceData(TinServicesData tinServicesData) {
		return financialCountMobDaoImpl.getTinServiceData(tinServicesData);
	}

	@Override
	public List<Tin2> getTin2ServiceData(Tin2 tin2) {
		return financialCountMobDaoImpl.getTin2ServiceData(tin2);
	}

	@Override
	public List<TinMultilevelCallBackLogs> gettinMultilevelCallLogs(TinMultilevelCallBackLogs tinCallLogs) {
		return financialCountMobDaoImpl.gettinMultilevelCallLogs(tinCallLogs);
	}

	@Override
	public List<OltasTinBean> getOltasTinServiceData(OltasTinBean oltasTin) {
		return financialCountMobDaoImpl.getOltasTinServiceData(oltasTin);
	}

	@Override
	public List<ReconcileBillDeskBean> getReconcileBillDeskServiceData(ReconcileBillDeskBean billDesk) {
		return financialCountMobDaoImpl.getReconcileBillDeskServiceData(billDesk);
	}

	@Override
	public List<SftpFileStatuses> getSftpFileStatus(SftpFileStatuses fileStatus) {
		return financialCountMobDaoImpl.getSftpFileStatus(fileStatus);
	}
	
}
