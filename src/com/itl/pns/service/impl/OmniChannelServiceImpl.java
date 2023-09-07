package com.itl.pns.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.bean.OmniChannelBean;
import com.itl.pns.dao.OmniChannelDao;
import com.itl.pns.entity.OmniChannelRequest;
import com.itl.pns.repository.OmniChannelRequestRepo;
import com.itl.pns.service.OmniChannelService;

@Service
@Qualifier("OmniChannelService")
public class OmniChannelServiceImpl implements OmniChannelService {

	static Logger LOGGER = Logger.getLogger(OmniChannelServiceImpl.class);
	@Autowired
	OmniChannelDao omnnichannelDao;

	@Autowired
	OmniChannelRequestRepo omniChannelRequestRepo;

	@Autowired
	RestServiceCall rest;

	@Override
	public List<OmniChannelBean> getOmniChannelRequest(int roleid) {
		List<OmniChannelBean> list = null;
		try {
			list = omnnichannelDao.getomniChannel(roleid);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<OmniChannelBean> getOmniChannelRequestReport(String status) {
		List<OmniChannelBean> list = null;
		try {
			list = omnnichannelDao.getOmniChannelRequestReport(status);
			for (OmniChannelBean omniChannelBean : list) {

				List<OmniChannelBean> bean = omnnichannelDao.getMappedMobile(omniChannelBean.getMOBILE());
				if (bean.size() != 0) {
					omniChannelBean.setMOBILE(bean.get(0).getIVRMOBILENO());

				} else {

				}
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<OmniChannelBean> getOmniChannelRequestById(int id) {

		List<OmniChannelBean> list = omnnichannelDao.getOmniChannelRequestById(id);
		return list;

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public String proceedOmniChannelRequest(OmniChannelRequest omni) {
		String resStatus = "";
		String resp = "";
		String result = "";
		try {
			OmniChannelRequest omniChannel = omniChannelRequestRepo.findOne(omni.getId());
			resp = rest.fundTransfer(omniChannel.getContent(), omni.getServiceType(), omni.getCustomerId());
			System.out.println(resp);
			if (resp.equalsIgnoreCase("error")) {
				omniChannel.setStatus(10);
				omniChannelRequestRepo.save(omniChannel);
				return resp;
			}

			JSONObject json1 = new JSONObject(resp);
			JSONObject json2 = json1.getJSONObject("responseParameter");
			resStatus = json2.getString("opstatus");
			result = json2.getString("Result");
			System.out.println(result);
			if (resStatus.equalsIgnoreCase("00") || resStatus.equalsIgnoreCase("04")) {
				omniChannel.setStatus(9);
				omniChannelRequestRepo.save(omniChannel);
				return resp;
			} else {
				omniChannel.setStatus(10);
				omniChannelRequestRepo.save(omniChannel);
				return result;
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return resp;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public String proceedOmniChannelRequestNew(OmniChannelRequest omni, int roleid, int status) {
		OmniChannelRequest omnichannel = omniChannelRequestRepo.findOne(omni.getId());
		omnichannel.setStatus(status);
		omniChannelRequestRepo.save(omnichannel);
		return "success";

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public String generateOtpRequest(OmniChannelRequest omni) {
		String resStatus = "";
		String resp = "";
		String result = "";
		try {
			OmniChannelRequest omniChannel = omniChannelRequestRepo.findOne(omni.getId());
			resp = rest.generateTokenService(omni.getMobile(), "RIB", "","");
			System.out.println(resp);
			if (resp.equalsIgnoreCase("error")) {

				omniChannelRequestRepo.save(omniChannel);
				return resp;
			}

			JSONObject json1 = new JSONObject(resp);
			JSONObject json2 = json1.getJSONObject("responseParameter");
			resStatus = json2.getString("opstatus");
			result = json2.getString("Result");
			System.out.println(result);
			System.out.println(resStatus);
			if (resStatus.equalsIgnoreCase("00") || resStatus.equalsIgnoreCase("04")) {

				omniChannelRequestRepo.save(omniChannel);
				return resp;
			} else {
				omniChannel.setStatus(10);
				omniChannelRequestRepo.save(omniChannel);
				return result;
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return resp;
	}

	@Override
	public String validateOtpRequest(OmniChannelRequest omni) {
		String resStatus = "";
		String resp = "";
		String result = "";
		String status = "";
		try {
			OmniChannelRequest omniChannel = omniChannelRequestRepo.findOne(omni.getId());
			resp = rest.validateOtpService(omni.getOtpCode(), omni.getMobile());
			System.out.println(resp);
			if (resp.equalsIgnoreCase("error")) {
				omniChannel.setStatus(10);
				omniChannelRequestRepo.save(omniChannel);

				return status;
			}

			JSONObject json1 = new JSONObject(resp);
			JSONObject json2 = json1.getJSONObject("responseParameter");
			resStatus = json2.getString("opstatus");
			result = json2.getString("Result");
			System.out.println(result);
			if (resStatus.equalsIgnoreCase("00") || resStatus.equalsIgnoreCase("04")) {
				omniChannel.setStatus(9);
				omniChannelRequestRepo.save(omniChannel);
				status = "OTP Verified";
				return status;
			} else {
				omniChannel.setStatus(10);
				omniChannelRequestRepo.save(omniChannel);
				return status;
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return resp;
	}
}
