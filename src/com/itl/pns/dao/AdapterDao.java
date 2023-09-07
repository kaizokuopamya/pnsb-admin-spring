package com.itl.pns.dao;

import java.math.BigInteger;
import java.util.List;

import com.itl.pns.bean.AdapterAuditLogsBean;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.AdapterSrcChannelEntity;
import com.itl.pns.entity.AdapterSrcIPEntity;

public interface AdapterDao {

	public boolean updateAdapterSrcChannel(AdapterSrcChannelEntity adapterSrcChanneData);

	public boolean addAdapterSrcChannel(AdapterSrcChannelEntity adapterSrcChanneData);

	public boolean deletetAdapterSrcChannel(AdapterSrcChannelEntity adapterSrcChanneData);

	public List<AdapterSrcChannelEntity> getAdaptrSrcChannel();

	public List<AdapterSrcChannelEntity> getAdaptrSrcChannelById(BigInteger id);

	public boolean addAdapterSrcIP(AdapterSrcIPEntity adapterIpdata);

	public boolean updateAdapterSrcIP(AdapterSrcIPEntity adapterIpdata);

	public boolean deleteAdapterSrcIP(AdapterSrcIPEntity adapterIpdata);

	public List<AdapterSrcIPEntity> getAdapterSrcIpDetails();

	public List<AdapterSrcIPEntity> getAdapterSrcIpById(BigInteger id);

	public List<AdapterAuditLogsBean> getAdapterAuditLogsById(BigInteger id);

	public List<AdapterAuditLogsBean> getAdapterAuditLogs();

	public List<AdapterAuditLogsBean> getAdapterAuditLogByDate(DateBean datebean);

	public ResponseMessageBean isChannelNameExist(AdapterSrcChannelEntity adapterSrcChanneData);

	public ResponseMessageBean updateChannelNameExist(AdapterSrcChannelEntity adapterSrcChanneData);

	public ResponseMessageBean isIpExitForSameChannel(AdapterSrcIPEntity adapterSrcIpData);

	public ResponseMessageBean UpdateIpExitForSameChannel(AdapterSrcIPEntity adapterSrcIpData);
}
