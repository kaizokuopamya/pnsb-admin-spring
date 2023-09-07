package com.itl.pns.service;

import java.util.List;

import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.LanguageJsonBean;
import com.itl.pns.bean.TicketBean;
import com.itl.pns.entity.LanguageJson;

public interface MessageService {
	
	
	@SuppressWarnings("rawtypes")
	List getCountByProcessing();

	@SuppressWarnings("rawtypes")
	List<LanguageJsonBean> getLanguageJson();

	List<TicketBean> getShortname();

	boolean addLanguagejson(LanguageJson languagejson);

	List<TicketBean> getStatusById(int id);

	List<TicketBean> getStatus();

	List getCountByDate(DateBean date);

	List getChannelCount(DateBean date);

	public List getTransactionlogByStatusNew(DateBean datebean);

	public List getTransactionByChannelNew(DateBean datebean);

	public List getServiceRequestByStatusNew(DateBean datebean);

	public List getTransactionByChannel(String type);

	public List getServiceRequestByStatus(String type);

	public List getMessagingReport(String type);

	public List getTransactionlogByStatus(String type);


	List getCustomerLocation(String type);

	List getCustomerCount(DateBean datebean);

	List getCustomerLocation(DateBean bean);

	

}
