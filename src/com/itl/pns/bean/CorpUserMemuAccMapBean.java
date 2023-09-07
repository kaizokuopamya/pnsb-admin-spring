package com.itl.pns.bean;

import java.util.List;

import com.itl.pns.corp.entity.CorpUserAccMapEntity;
import com.itl.pns.corp.entity.CorpUserMenuMapEntity;

public class CorpUserMemuAccMapBean {

	private List<CorpUserMenuMapEntity> corpUserMenuMapData;
	
    private List<CorpUserAccMapEntity> corpUserAccMapData;

	public List<CorpUserMenuMapEntity> getCorpUserMenuMapData() {
		return corpUserMenuMapData;
	}

	public void setCorpUserMenuMapData(List<CorpUserMenuMapEntity> corpUserMenuMapData) {
		this.corpUserMenuMapData = corpUserMenuMapData;
	}

	public List<CorpUserAccMapEntity> getCorpUserAccMapData() {
		return corpUserAccMapData;
	}

	public void setCorpUserAccMapData(List<CorpUserAccMapEntity> corpUserAccMapData) {
		this.corpUserAccMapData = corpUserAccMapData;
	}
    
}
