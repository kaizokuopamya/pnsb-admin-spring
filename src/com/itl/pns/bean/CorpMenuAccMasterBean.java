package com.itl.pns.bean;

import java.util.List;

import com.itl.pns.corp.entity.CorpAccMapEntity;
import com.itl.pns.corp.entity.CorpMenuMapEntity;

public class CorpMenuAccMasterBean {

	private List<CorpMenuMapEntity> corpMenuList;
	private List<CorpAccMapEntity> corpAccList;

	public List<CorpMenuMapEntity> getCorpMenuList() {
		return corpMenuList;
	}

	public void setCorpMenuList(List<CorpMenuMapEntity> corpMenuList) {
		this.corpMenuList = corpMenuList;
	}

	public List<CorpAccMapEntity> getCorpAccList() {
		return corpAccList;
	}

	public void setCorpAccList(List<CorpAccMapEntity> corpAccList) {
		this.corpAccList = corpAccList;
	}

	@Override
	public String toString() {
		return "CorpMenuAccMasterBean [corpMenuList=" + corpMenuList + ", corpAccList=" + corpAccList + "]";
	}

}
