/**
 * 
 */
package com.itl.pns.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ashish.yadav
 *
 */
public class CorporateUserBean {

	private BigDecimal corpCompId;
	private List<String> userNameList;

	public List<String> getUserNameList() {
		return userNameList;
	}

	public void setUserNameList(List<String> userNameList) {
		this.userNameList = userNameList;
	}

	public BigDecimal getCorpCompId() {
		return corpCompId;
	}

	public void setCorpCompId(BigDecimal corpCompId) {
		this.corpCompId = corpCompId;
	}

}
