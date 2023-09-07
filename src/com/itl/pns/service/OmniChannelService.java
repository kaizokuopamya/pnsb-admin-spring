package com.itl.pns.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itl.pns.bean.OmniChannelBean;
import com.itl.pns.bean.VerifyCifPara;
import com.itl.pns.entity.OmniChannelRequest;

@Service
public interface OmniChannelService {

	String proceedOmniChannelRequest(OmniChannelRequest omni);

	List<OmniChannelBean> getOmniChannelRequestById(int id);

	List<OmniChannelBean> getOmniChannelRequest(int roleId);

	List<OmniChannelBean> getOmniChannelRequestReport(String status);

	String proceedOmniChannelRequestNew(OmniChannelRequest omni, int roleid, int status);

	String generateOtpRequest(OmniChannelRequest omni);

	String validateOtpRequest(OmniChannelRequest omni);

}
