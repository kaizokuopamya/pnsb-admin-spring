package com.itl.pns.service;

import java.util.List;

import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.TicketBean;
import com.itl.pns.entity.BankTokenEntity;
import com.itl.pns.entity.CustomerTokenEntity;

public interface TicketsService {

	public List<TicketBean> getTickets(int statusId);
	
	public List<TicketBean> getTicketsById(int ticketId);
	
	public boolean updateTicketStatus(TicketBean ticketBean);
	
	public boolean generateTokenRequest(TicketBean ticketBean);
	
	public List<TicketBean> getAllTicketsList();
	
	public boolean rejectTokenRequest(TicketBean ticketBean);
	
	public List<BankTokenEntity> getBankTokenRequest(RequestParamBean requestParamBean);
	
	public ResponseMessageBean generateBankToken(BankTokenEntity bankToken);
	
	public List<BankTokenEntity> getBankTokenById(int id);
	
	public boolean rejectBankToken(BankTokenEntity bankToken);
	
	
	public List<CustomerTokenEntity> getAllCustomerTokenRequest();

	boolean generateCustomerToken(CustomerTokenEntity custToken);

	public List<CustomerTokenEntity> getAdminBankTokenById(int id);

	public boolean rejectBankTokenByAdmin(CustomerTokenEntity bankToken);
	
	public List<BankTokenEntity> getBankTokenByRefNo(long id);
	
	
	
	
	
	
}
