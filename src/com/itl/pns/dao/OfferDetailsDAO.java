package com.itl.pns.dao;

import java.math.BigInteger;
import java.util.List;

import com.itl.pns.bean.DeviceMasterDetailsBean;
import com.itl.pns.bean.OfferDetailsBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.TicketBean;
import com.itl.pns.entity.OfferDetailsEntity;

public interface OfferDetailsDAO {

	List<OfferDetailsBean> getOfferDetails();

	List<DeviceMasterDetailsBean> getDeviceMasterDetails();

	List<DeviceMasterDetailsBean> getDeviceMasterDetailsById(int id);
	
	List<DeviceMasterDetailsBean> getDeviceMasterDetailsByCustId(BigInteger custId);

	List<TicketBean> getKycImage(int custId);

	public ResponseMessageBean isSeqnoExit(OfferDetailsEntity offerDetail);

	public ResponseMessageBean updateCheckSeqno(OfferDetailsEntity offerDetail);
	
	public List<OfferDetailsEntity> getAllOfferDetails()  ;
	
}
