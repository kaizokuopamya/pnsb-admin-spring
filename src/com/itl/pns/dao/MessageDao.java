package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.LanguageJsonBean;
import com.itl.pns.bean.TicketBean;

public interface MessageDao {

	@SuppressWarnings("rawtypes")
	List getCountByProcessing();

	@SuppressWarnings("rawtypes")
	List<LanguageJsonBean> getLanguageJson();

	List<TicketBean> getShortname();

	List<TicketBean> getStatusById(int id);

	public List<TicketBean> getStatus();

	List getCountByDate(DateBean datebean);

	List getChannelCount(DateBean datebean);

	List getTransactionlogByStatusNew(DateBean datebean);

	public List getTransactionByChannelNew(DateBean datebean);

	public List getServiceRequestByStatusNew(DateBean datebean);

	List getTransactionByChannel(String type);

	public List getServiceRequestByStatus(String type);

	public List getTransactionlogByStatus(String type);

	public List getCustomerCount(DateBean datebean);

	public List getCountByDate(String type);

	List getCustomerlocation(String type);

	List getCustomerlocation(DateBean date);

}
