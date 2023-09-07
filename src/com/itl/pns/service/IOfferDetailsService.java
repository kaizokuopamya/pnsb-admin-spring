package com.itl.pns.service;

import java.math.BigInteger;
import java.util.List;

import com.itl.pns.bean.DeviceMasterDetailsBean;
import com.itl.pns.bean.OfferDetailsBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.TicketBean;
import com.itl.pns.entity.OfferDetailsEntity;
import com.itl.pns.entity.UserDeviceDetails;

public interface IOfferDetailsService {

	List<OfferDetailsBean> getOfferDetails();

	boolean insertOfferDetails(OfferDetailsEntity offerDetail);

	boolean updateuploadOffer(OfferDetailsEntity offerDetail);

	List<DeviceMasterDetailsBean> getDeviceMasterDetails();

	void updateDeviceDetails(UserDeviceDetails userDeviceDetails);

	List<DeviceMasterDetailsBean> getDeviceMasterDetailsById(int id);

	List<DeviceMasterDetailsBean> getDeviceMasterDetailsByCustId(BigInteger custId);
	
	List<TicketBean> getKycImage(int custId);

	public ResponseMessageBean isSeqnoExit(OfferDetailsEntity offerDetail);
	
	public ResponseMessageBean updateCheckSeqno(OfferDetailsEntity offerDetail);
	
	public boolean addOfferToAdminWorkFlow(OfferDetailsEntity offerDetail,int userStatus);
	
	public boolean updateuploadOfferToAdminWorkFlow(OfferDetailsEntity offerDetail,int userStatus);
	
	public List<String> serviceTypeList();
	
}
