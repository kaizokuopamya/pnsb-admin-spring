package com.itl.pns.bean;

import java.util.List;

import com.itl.pns.corp.entity.CorpAccMapEntityTmp;
import com.itl.pns.corp.entity.CorpMenuMapEntityTmp;

public class CorpMenuAccBeanMapTmp {

	private List<CorpMenuMapEntityTmp> corpMenuList;
	private List<CorpAccMapEntityTmp> corpAccList;

	public List<CorpMenuMapEntityTmp> getCorpMenuList() {
		return corpMenuList;
	}

	public void setCorpMenuList(List<CorpMenuMapEntityTmp> corpMenuList) {
		this.corpMenuList = corpMenuList;
	}

	public List<CorpAccMapEntityTmp> getCorpAccList() {
		return corpAccList;
	}

	public void setCorpAccList(List<CorpAccMapEntityTmp> corpAccList) {
		this.corpAccList = corpAccList;
	}

}
