package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.bean.OmniChannelBean;
import com.itl.pns.impsEntity.ImpsMasterEntity;

public interface OmniChannelDao {
	



	List<OmniChannelBean> getOmniChannelRequestById(int id);

	List<OmniChannelBean> getomniChannel(int roleid);

	List<OmniChannelBean> getMappedMobile(String string);

	List<OmniChannelBean> findMappedMobileAccount(String mobile);

	List<OmniChannelBean> getOmniChannelRequestReport(String status);

	List<OmniChannelBean> findIVR(String mobile);
	
	public boolean insertImpsMasterDetails(ImpsMasterEntity impsMasterData);

	public boolean updateImpsMasterDetails(ImpsMasterEntity impsMasterData);

	public List<ImpsMasterEntity> getImpsMasterDetails();

	public List<ImpsMasterEntity> getImpsMasterDetailsById(ImpsMasterEntity impsMasterData);
	
	
	public  List<ImpsMasterEntity> getImpsMasterState();

	public  List<ImpsMasterEntity> getImpsMasterDistrictByState(ImpsMasterEntity impsMasterData);

	public List<ImpsMasterEntity> getImpsMasterCityByDistrict(ImpsMasterEntity impsMasterData);

	public List<ImpsMasterEntity> getImpsMasterDataByCity(ImpsMasterEntity impsMasterData);
	
	public List<ImpsMasterEntity> getImpsMasterDataByIFSC(ImpsMasterEntity impsMasterData);
	
	
	
	
	
	
	

}
