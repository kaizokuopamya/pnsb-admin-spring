package com.itl.pns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.service.MessageReportService;
import com.itl.pns.dao.MessageReportDao;
import com.itl.pns.entity.MessageReport;

@Service
public class MessageReportServiceImpl implements MessageReportService {
	
	
	@Autowired
	MessageReportDao messageReportDao;

	@Override
	public List<MessageReport> getChannelDetails() {
		return messageReportDao.getChannelDetails();
	}

	@Override
	public List<MessageReport> getMessageReportDetails(MessageReport messageReport) {
		return messageReportDao.getMessageReportDetails(messageReport);
	}
	
}
