package com.itl.pns.bean;

import java.util.List;

import com.itl.pns.corp.entity.CorpAccReqEntity;
import com.itl.pns.corp.entity.CorpMenuReqEntity;

public class CorpMenuAccBeanTmp {

	private List<CorpMenuReqEntity> corpMenuList;
	private List<CorpAccReqEntity> corpAccList;

	public List<CorpMenuReqEntity> getCorpMenuList() {
		return corpMenuList;
	}

	public void setCorpMenuList(List<CorpMenuReqEntity> corpMenuList) {
		this.corpMenuList = corpMenuList;
	}

	public List<CorpAccReqEntity> getCorpAccList() {
		return corpAccList;
	}

	public void setCorpAccList(List<CorpAccReqEntity> corpAccList) {
		this.corpAccList = corpAccList;
	}

}
