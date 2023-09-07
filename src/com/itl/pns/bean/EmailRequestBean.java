package com.itl.pns.bean;

public class EmailRequestBean {

	private String api_name;

	private String[] columanNames;

	private String[] emailList;

	private String emailMsgBody;

	public String getApi_name() {
		return api_name;
	}

	public void setApi_name(String api_name) {
		this.api_name = api_name;
	}

	public String[] getColumanNames() {
		return columanNames;
	}

	public void setColumanNames(String[] columanNames) {
		this.columanNames = columanNames;
	}

	public String[] getEmailList() {
		return emailList;
	}

	public void setEmailList(String[] emailList) {
		this.emailList = emailList;
	}

	public String getEmailMsgBody() {
		return emailMsgBody;
	}

	public void setEmailMsgBody(String emailMsgBody) {
		this.emailMsgBody = emailMsgBody;
	}

}
