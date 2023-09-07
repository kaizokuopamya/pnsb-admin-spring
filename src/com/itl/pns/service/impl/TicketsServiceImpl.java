package com.itl.pns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.TicketBean;
import com.itl.pns.dao.TicketsDao;
import com.itl.pns.entity.BankTokenEntity;
import com.itl.pns.entity.CustomerTokenEntity;
import com.itl.pns.service.TicketsService;
@Service
public class TicketsServiceImpl implements TicketsService {

	
	@Autowired
	TicketsDao ticketsDao;
	@Override
	public List<TicketBean> getTickets(int statusId) {
		return ticketsDao.getTicketsList(statusId);
	}
	@Override
	public List<TicketBean> getTicketsById(int ticketId) {
		return ticketsDao.getTicketsListById(ticketId);
		
	}
	
	@Override
	public boolean updateTicketStatus(TicketBean ticketBean) {
		return ticketsDao.updateTicketStatus(ticketBean);
	}
	
	@Override
	public List<TicketBean> getAllTicketsList() {
		return ticketsDao.getAllTicketsList();
	}
	
	@Override
	public boolean generateTokenRequest(TicketBean ticketBean) {
		return ticketsDao.generateTokenRequest(ticketBean);
	}
	
	@Override
	public boolean rejectTokenRequest(TicketBean ticketBean) {
		return ticketsDao.rejectTokenRequest(ticketBean);
	}
	@Override
	public List<BankTokenEntity> getBankTokenRequest(RequestParamBean requestParamBean) {
		return ticketsDao.getBankTokenRequest(requestParamBean);
	}
	
	@Override
	public ResponseMessageBean generateBankToken(BankTokenEntity bankToken) {
		return ticketsDao.generateBankToken(bankToken);
	}
	
	@Override
	public List<BankTokenEntity> getBankTokenById(int id) {
		return ticketsDao.getBankTokenById(id);
	}
	
	@Override
	public boolean rejectBankToken(BankTokenEntity bankToken) {
		return ticketsDao.rejectBankToken(bankToken);
	}
	@Override
	public List<CustomerTokenEntity> getAllCustomerTokenRequest() {
		
		return ticketsDao.getAllCustomerTokenRequest();
	}
	@Override
	public boolean generateCustomerToken(CustomerTokenEntity custToken) {
		
		return ticketsDao.generateCustomerToken(custToken);
	}
	@Override
	public List<CustomerTokenEntity> getAdminBankTokenById(int id) {
		return ticketsDao.getAdminBankTokenById(id);
	}
	
	@Override
	public boolean rejectBankTokenByAdmin(CustomerTokenEntity custToken) {
		return ticketsDao.rejectBankTokenByAdmin(custToken);
	}
	
	@Override
	public List<BankTokenEntity> getBankTokenByRefNo(long refNo) {
		return ticketsDao.getBankTokenByRefNo(refNo);
	}

}
