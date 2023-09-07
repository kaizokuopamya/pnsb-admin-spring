package com.itl.pns.corp.entity;

import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CORPTRANSACTIONLOGS")
public class CorporateTransactionLogs {
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "corptransactionslogs_ID_SEQ", allocationSize = 1)
  private BigInteger id;
  
  @Column(name = "ACTIVITYID")
  private BigInteger activityId;
  
  @Column(name = "RRN")
  private String rrn;
  
  @Column(name = "REQ_RES")
  private String req_res;
  
  @Column(name = "MESSAGE1")
  private String message1;
  
  @Column(name = "MESSAGE2")
  private String message2;
  
  @Column(name = "AMOUNT")
  private int amount;
  
  @Column(name = "USERDEVICEID")
  private BigInteger userDeviceId;
  
  @Column(name = "SERVICEREFNO")
  private String serviceRefNo;
  
  @Column(name = "CUSTOMERID")
  private long customerId;
  
  @Column(name = "CREATEDBY")
  private int createdBy;
  
  @Column(name = "CREATEDON")
  private Date createdOn;
  
  @Column(name = "STATUSID")
  private BigInteger statusId;
  
  @Column(name = "APPID")
  private BigInteger appId;
  
  @Column(name = "THIRDPARTYREFNO")
  private String thirdPartyRefNo;
  
  @Column(name = "MESSAGE3")
  private String message3;
  
  @Column(name = "SESSION_ID")
  private String session_id;
  
  public BigInteger getId() {
    return this.id;
  }
  
  public void setId(BigInteger id) {
    this.id = id;
  }
  
  public BigInteger getActivityId() {
    return this.activityId;
  }
  
  public void setActivityId(BigInteger activityId) {
    this.activityId = activityId;
  }
  
  public String getRrn() {
    return this.rrn;
  }
  
  public void setRrn(String rrn) {
    this.rrn = rrn;
  }
  
  public String getReq_res() {
    return this.req_res;
  }
  
  public void setReq_res(String req_res) {
    this.req_res = req_res;
  }
  
  public String getMessage1() {
    return this.message1;
  }
  
  public void setMessage1(String message1) {
    this.message1 = message1;
  }
  
  public String getMessage2() {
    return this.message2;
  }
  
  public void setMessage2(String message2) {
    this.message2 = message2;
  }
  
  public int getAmount() {
    return this.amount;
  }
  
  public void setAmount(int amount) {
    this.amount = amount;
  }
  
  public BigInteger getUserDeviceId() {
    return this.userDeviceId;
  }
  
  public void setUserDeviceId(BigInteger userDeviceId) {
    this.userDeviceId = userDeviceId;
  }
  
  public String getServiceRefNo() {
    return this.serviceRefNo;
  }
  
  public void setServiceRefNo(String serviceRefNo) {
    this.serviceRefNo = serviceRefNo;
  }
  
  public long getCustomerId() {
    return this.customerId;
  }
  
  public void setCustomerId(long customerId) {
    this.customerId = customerId;
  }
  
  public int getCreatedBy() {
    return this.createdBy;
  }
  
  public void setCreatedBy(int createdBy) {
    this.createdBy = createdBy;
  }
  
  public Date getCreatedOn() {
    return this.createdOn;
  }
  
  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }
  
  public BigInteger getStatusId() {
    return this.statusId;
  }
  
  public void setStatusId(BigInteger statusId) {
    this.statusId = statusId;
  }
  
  public BigInteger getAppId() {
    return this.appId;
  }
  
  public void setAppId(BigInteger appId) {
    this.appId = appId;
  }
  
  public String getThirdPartyRefNo() {
    return this.thirdPartyRefNo;
  }
  
  public void setThirdPartyRefNo(String thirdPartyRefNo) {
    this.thirdPartyRefNo = thirdPartyRefNo;
  }
  
  public String getMessage3() {
    return this.message3;
  }
  
  public void setMessage3(String message3) {
    this.message3 = message3;
  }
  
  public String getSession_id() {
    return this.session_id;
  }
  
  public void setSession_id(String session_id) {
    this.session_id = session_id;
  }
}
