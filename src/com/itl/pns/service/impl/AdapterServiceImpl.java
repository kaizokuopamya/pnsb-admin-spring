package com.itl.pns.service.impl;


import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.itl.pns.bean.AdapterAuditLogsBean;
import com.itl.pns.bean.AdminWorkFlowReqBean;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.dao.AdapterDao;
import com.itl.pns.entity.AdapterSrcChannelEntity;
import com.itl.pns.entity.AdapterSrcIPEntity;
import com.itl.pns.service.AdapterService;

@Service
@Qualifier("AdapterService")
public class AdapterServiceImpl implements AdapterService{

	@Autowired 
	AdapterDao adapterDao;
	
	@Override
	public boolean updateAdapterSrcChannel(AdapterSrcChannelEntity adapterSrcChanneData) {
	   return	adapterDao.updateAdapterSrcChannel(adapterSrcChanneData);
		
	}

	@Override
	public boolean addAdapterSrcChannel(AdapterSrcChannelEntity adapterSrcChanneData) {
		return	adapterDao.addAdapterSrcChannel(adapterSrcChanneData);
	}

	@Override
	public boolean deletetAdapterSrcChannel(AdapterSrcChannelEntity adapterSrcChanneData) {
		return	adapterDao.deletetAdapterSrcChannel(adapterSrcChanneData);
	}

	
	@Override
	public List<AdapterSrcChannelEntity> getAdaptrSrcChannel() {
		return	adapterDao.getAdaptrSrcChannel();
	}

	@Override
	public List<AdapterSrcChannelEntity> getAdaptrSrcChannelById(BigInteger id) {
		return	adapterDao.getAdaptrSrcChannelById(id);
	}

	@Override
	public boolean addAdapterSrcIP(AdapterSrcIPEntity adapterIpdata) {
		 return	adapterDao.addAdapterSrcIP(adapterIpdata);
	}

	@Override
	public boolean updateAdapterSrcIP(AdapterSrcIPEntity adapterIpdata) {
		 return	adapterDao.updateAdapterSrcIP(adapterIpdata);
	}

	@Override
	public boolean deleteAdapterSrcIP(AdapterSrcIPEntity adapterIpdata) {
		 return	adapterDao.deleteAdapterSrcIP(adapterIpdata);
	}

	@Override
	public List<AdapterSrcIPEntity> getAdapterSrcIpDetails() {
		 return	adapterDao.getAdapterSrcIpDetails();
	}

	@Override
	public List<AdapterSrcIPEntity> getAdapterSrcIpById(BigInteger id) {
		 return	adapterDao.getAdapterSrcIpById(id);
	}

	@Override
	public List<AdapterAuditLogsBean> getAdapterAuditLogsById(BigInteger id) {
		return	adapterDao.getAdapterAuditLogsById(id);
	}

	@Override
	public List<AdapterAuditLogsBean> getAdapterAuditLogs() {
		return	adapterDao.getAdapterAuditLogs();
	}

	@Override
	public List<AdapterAuditLogsBean> getAdapterAuditLogByDate(DateBean datebean) {
		return	adapterDao.getAdapterAuditLogByDate(datebean);
	}

	@Override
	public ResponseMessageBean isChannelNameExist(AdapterSrcChannelEntity adapterSrcChanneData) {
		return	adapterDao.isChannelNameExist(adapterSrcChanneData);
	}

	@Override
	public ResponseMessageBean updateChannelNameExist(AdapterSrcChannelEntity adapterSrcChanneData) {
		return	adapterDao.updateChannelNameExist(adapterSrcChanneData);
	}

	@Override
	public ResponseMessageBean isIpExitForSameChannel(AdapterSrcIPEntity adapterSrcIpData) {
		return	adapterDao.isIpExitForSameChannel(adapterSrcIpData);
	}

	@Override
	public ResponseMessageBean UpdateIpExitForSameChannel(AdapterSrcIPEntity adapterSrcIpData) {
		return	adapterDao.UpdateIpExitForSameChannel(adapterSrcIpData);
	}

	
	
}
