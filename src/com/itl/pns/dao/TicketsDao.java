package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.TicketBean;
import com.itl.pns.entity.BankTokenEntity;
import com.itl.pns.entity.CustomerTokenEntity;

public interface TicketsDao {
	public List<TicketBean> getTicketsList(int statusId);

	public List<TicketBean> getTicketsListById(int ticketId);

	public boolean updateTicketStatus(TicketBean ticketBean);

	List<TicketBean> getAllTicketsList();

	public boolean generateTokenRequest(TicketBean ticketBean);

	boolean rejectTokenRequest(TicketBean ticketBean);

	List<BankTokenEntity> getBankTokenRequest(RequestParamBean requestParamBean);

	public ResponseMessageBean generateBankToken(BankTokenEntity bankToken);

	public List<BankTokenEntity> getBankTokenById(int id);

	public boolean rejectBankToken(BankTokenEntity bankToken);

	public List<CustomerTokenEntity> getAllCustomerTokenRequest();

	boolean generateCustomerToken(CustomerTokenEntity custToken);

	public List<CustomerTokenEntity> getAdminBankTokenById(int id);

	public boolean rejectBankTokenByAdmin(CustomerTokenEntity custToken);

	public List<BankTokenEntity> getBankTokenByRefNo(long id);

}
