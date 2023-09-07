package com.itl.pns.bean;

import java.util.List;

import com.itl.pns.corp.entity.CorpUserAccMapEntity;
import com.itl.pns.corp.entity.CorpUserEntity;
import com.itl.pns.corp.entity.CorpUserMenuMapEntity;

public class CorpMasterUserMenuAccBean {

	private List<CorpUserEntity> corpUsers;
	private List<CorpUserMenuMapEntity> corpUsersMenu;
	private List<CorpUserAccMapEntity> corpUsersAcc;

	public List<CorpUserEntity> getCorpUsers() {
		return corpUsers;
	}

	public void setCorpUsers(List<CorpUserEntity> corpUsers) {
		this.corpUsers = corpUsers;
	}

	public List<CorpUserMenuMapEntity> getCorpUsersMenu() {
		return corpUsersMenu;
	}

	public void setCorpUsersMenu(List<CorpUserMenuMapEntity> corpUsersMenu) {
		this.corpUsersMenu = corpUsersMenu;
	}

	public List<CorpUserAccMapEntity> getCorpUsersAcc() {
		return corpUsersAcc;
	}

	public void setCorpUsersAcc(List<CorpUserAccMapEntity> corpUsersAcc) {
		this.corpUsersAcc = corpUsersAcc;
	}

}
