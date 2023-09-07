package com.itl.pns.service;

import java.util.List;

import com.itl.pns.entity.MessageReport;


public interface MessageReportService {
	
	public List<MessageReport> getMessageReportDetails(MessageReport messageReport);
	
	public List<MessageReport> getChannelDetails();
	
	

}
