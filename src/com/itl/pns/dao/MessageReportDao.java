package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.entity.MessageReport;


public interface MessageReportDao {
	
	public List<MessageReport> getMessageReportDetails(MessageReport messageReport);
	
	public List<MessageReport> getChannelDetails();

}
