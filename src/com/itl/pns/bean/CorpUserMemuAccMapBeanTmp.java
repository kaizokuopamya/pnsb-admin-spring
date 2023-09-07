package com.itl.pns.bean;

import java.util.List;

import com.itl.pns.corp.entity.CorpUserAccMapEntityTmp;
import com.itl.pns.corp.entity.CorpUserMenuMapEntityTmp;

public class CorpUserMemuAccMapBeanTmp {

	private List<CorpUserMenuMapEntityTmp> corpUserMenuMapData;

	private List<CorpUserAccMapEntityTmp> corpUserAccMapData;

	public List<CorpUserMenuMapEntityTmp> getCorpUserMenuMapData() {
		return corpUserMenuMapData;
	}

	public void setCorpUserMenuMapData(List<CorpUserMenuMapEntityTmp> corpUserMenuMapData) {
		this.corpUserMenuMapData = corpUserMenuMapData;
	}

	public List<CorpUserAccMapEntityTmp> getCorpUserAccMapData() {
		return corpUserAccMapData;
	}

	public void setCorpUserAccMapData(List<CorpUserAccMapEntityTmp> corpUserAccMapData) {
		this.corpUserAccMapData = corpUserAccMapData;
	}

}
