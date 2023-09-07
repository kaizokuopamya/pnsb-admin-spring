package com.itl.pns.dao;

import java.util.List;

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

public interface FinancialCountMobDao {

	List<FinancialCountMobBean> getTotalFinancialTransaction(DateBean datebean);

	List<FinancialCountMobBean> getTotalNonfinancialTransaction(DateBean datebean);

	List<FinancialCountMobBean> getTotalNonfinancialTransactionExclude(DateBean datebean);

	List<FinancialCountMobBean> getTotalCountFromCustomers(FinancialCountMobBean financialCountMobBean);

	List<SolePropritorRegistrationBean> getSolePropritorRegistrationDetails(DateBean datebean);

	List<CorpUserBean> getSolePropritorRegistrationCount(DateBean datebean);

	List<FinancialCountMobBean> getTransactionsById(FinancialCountMobBean financialCountMobBean);

	List<FinancialCountMobBean> getFundTransferDetails(FinancialCountMobBean financialCountMobBean);

	List<FinancialCountMobBean> getRibandRmob();

	List<FinancialCountMobBean> getTotalCustRegWithRibRmob(FinancialCountMobBean financialCountMobBean);

	List<FinancialCountMobBean> getTotalActiveCustWithRibRmob(FinancialCountMobBean financialCountMobBean);

	List<Payaggregatorbean> getMerchantNameDropdown();

	List<Payaggregatorbean> getPaymentaggDataByMerchantname(Payaggregatorbean payaggregatorbean);

	List<TimestampDateBean> getTableNameDropdown();

	List<TinServicesData> getTinServiceData(TinServicesData TinServicesData);

	List<Tin2> getTin2ServiceData(Tin2 tin2);

	List<TinMultilevelCallBackLogs> gettinMultilevelCallLogs(TinMultilevelCallBackLogs tinCallLogs);

	List<OltasTinBean> getOltasTinServiceData(OltasTinBean oltasTin);

	List<ReconcileBillDeskBean> getReconcileBillDeskServiceData(ReconcileBillDeskBean billDesk);

	List<SftpFileStatuses> getSftpFileStatus(SftpFileStatuses fileStatus);

}
