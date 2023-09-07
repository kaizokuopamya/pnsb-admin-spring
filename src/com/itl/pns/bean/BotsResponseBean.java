package com.itl.pns.bean;
import java.util.List;

public class BotsResponseBean {
	private String responseCode;
	private String responseValue;
	private Object result;
	private List<InvalidLeasingDetailsBean> invalidLeasingDetailsBean;
	private List<WalletPointsBean> walletPointsBean;

	String cssData;
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseValue() {
		return responseValue;
	}
	public void setResponseValue(String responseValue) {
		this.responseValue = responseValue;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public List<InvalidLeasingDetailsBean> getInvalidLeasingDetailsBean() {
		return invalidLeasingDetailsBean;
	}
	public void setInvalidLeasingDetailsBean(List<InvalidLeasingDetailsBean> list) {
		this.invalidLeasingDetailsBean = list;
	}
	public List<WalletPointsBean> getWalletPointsBean() {
		return walletPointsBean;
	}
	public void setWalletPointsBean(List<WalletPointsBean> walletPointsBean) {
		this.walletPointsBean = walletPointsBean;
	}
	public String getCssData() {
		return cssData;
	}
	public void setCssData(String cssData) {
		this.cssData = cssData;
	}
	
	

}
