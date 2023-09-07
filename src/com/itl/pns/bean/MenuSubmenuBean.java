package com.itl.pns.bean;

import java.util.List;

import com.itl.pns.entity.SubMenuEntity;


public class MenuSubmenuBean {
	
	int menuId;
	
	String menuName;
	
	List<SubMenuEntity> subMenuList;

	String menuImage ;
	
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public List<SubMenuEntity> getSubMenuList() {
		return subMenuList;
	}

	public void setSubMenuList(List<SubMenuEntity> subMenuList) {
		this.subMenuList = subMenuList;
	}

	public String getMenuImage() {
		return menuImage;
	}

	public void setMenuImage(String menuImage) {
		this.menuImage = menuImage;
	}
	
	
	
}
