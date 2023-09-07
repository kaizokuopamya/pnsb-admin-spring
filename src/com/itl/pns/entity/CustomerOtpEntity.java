package com.itl.pns.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name="CUSTOMEROTP")
public class CustomerOtpEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
	@SequenceGenerator(name = "id_sequence", sequenceName = "")
	public long Id;

	@Column(name = "CHANNELID")
	public int CHANNELID;

	@Column(name = "APPID")
	public int APPID;

	@Column(nullable = true, name = "RRN")
	public String RRN;

	@Column(name = "OTP")
	public int OTP;

	@Column(nullable = true, name = "OTPVALIDATEDON")
	public Date OTPVALIDATEDON;

	@Column(name = "GENERATEDONCHANNELID")
	public int GENERATEDONCHANNELID;

	@Column(name = "CUSTOMERID")
	public int CUSTOMERID;

	@Column(name = "VALIDATEDONCHANNELID")
	public int VALIDATEDONCHANNELID;

	@Column(name = "INVALIDOTPATTEMPTS")
	public int INVALIDOTPATTEMPTS;

	@Column(name = "ACTIVITYID")
	public int ACTIVITYID;

	@Column(nullable = true, name = "CREATEDBY")
	public Integer CREATEDBY;

	@Column(nullable = true, name = "CREATEDON")
	public Date CREATEDON;

	@Column(nullable = true, name = "UPDATEDBY")
	public Integer UPDATEDBY;

	@Column(nullable = true, name = "UPDATEDON")
	public Date UPDATEDON;

	@Column(name = "STATUSID")
	public int STATUSID;

	

	public CustomerOtpEntity() {
	}

	public CustomerOtpEntity(int CHANNELID, int APPID, String RRN, int OTP, Date OTPVALIDATEDON, int GENERATEDONCHANNELID, int CUSTOMERID, int VALIDATEDONCHANNELID, int INVALIDOTPATTEMPTS, int ACTIVITYID, Integer CREATEDBY, Date CREATEDON,
			Integer UPDATEDBY, Date UPDATEDON, int STATUSID) {
		super();
		this.CHANNELID = CHANNELID; // 1
		this.APPID = APPID;// 2
		this.RRN = RRN;// 3
		this.OTP = OTP;// 4
		this.OTPVALIDATEDON = OTPVALIDATEDON;// 5
		this.GENERATEDONCHANNELID = GENERATEDONCHANNELID;// 6
		this.CUSTOMERID = CUSTOMERID;// 7
		this.VALIDATEDONCHANNELID = VALIDATEDONCHANNELID;// 8
		this.INVALIDOTPATTEMPTS = INVALIDOTPATTEMPTS;// 9
		this.ACTIVITYID = ACTIVITYID;// 10
		this.CREATEDBY = CREATEDBY;// 19
		this.CREATEDON = CREATEDON;// 20
		this.UPDATEDBY = UPDATEDBY;// 21
		this.UPDATEDON = UPDATEDON;// 22
		this.STATUSID = STATUSID;// 27
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public int getCHANNELID() {
		return CHANNELID;
	}

	public void setCHANNELID(int cHANNELID) {
		CHANNELID = cHANNELID;
	}

	public int getAPPID() {
		return APPID;
	}

	public void setAPPID(int aPPID) {
		APPID = aPPID;
	}

	public String getRRN() {
		return RRN;
	}

	public void setRRN(String rRN) {
		RRN = rRN;
	}

	public int getOTP() {
		return OTP;
	}

	public void setOTP(int oTP) {
		OTP = oTP;
	}

	public Date getOTPVALIDATEDON() {
		return OTPVALIDATEDON;
	}

	public void setOTPVALIDATEDON(Date oTPVALIDATEDON) {
		OTPVALIDATEDON = oTPVALIDATEDON;
	}

	public int getGENERATEDONCHANNELID() {
		return GENERATEDONCHANNELID;
	}

	public void setGENERATEDONCHANNELID(int gENERATEDONCHANNELID) {
		GENERATEDONCHANNELID = gENERATEDONCHANNELID;
	}

	public int getCUSTOMERID() {
		return CUSTOMERID;
	}

	public void setCUSTOMERID(int cUSTOMERID) {
		CUSTOMERID = cUSTOMERID;
	}

	public int getVALIDATEDONCHANNELID() {
		return VALIDATEDONCHANNELID;
	}

	public void setVALIDATEDONCHANNELID(int vALIDATEDONCHANNELID) {
		VALIDATEDONCHANNELID = vALIDATEDONCHANNELID;
	}

	public int getINVALIDOTPATTEMPTS() {
		return INVALIDOTPATTEMPTS;
	}

	public void setINVALIDOTPATTEMPTS(int iNVALIDOTPATTEMPTS) {
		INVALIDOTPATTEMPTS = iNVALIDOTPATTEMPTS;
	}

	public int getACTIVITYID() {
		return ACTIVITYID;
	}

	public void setACTIVITYID(int aCTIVITYID) {
		ACTIVITYID = aCTIVITYID;
	}

	public Integer getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(Integer cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public Date getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public Integer getUPDATEDBY() {
		return UPDATEDBY;
	}

	public void setUPDATEDBY(Integer uPDATEDBY) {
		UPDATEDBY = uPDATEDBY;
	}

	public Date getUPDATEDON() {
		return UPDATEDON;
	}

	public void setUPDATEDON(Date uPDATEDON) {
		UPDATEDON = uPDATEDON;
	}

	public int getSTATUSID() {
		return STATUSID;
	}

	public void setSTATUSID(int sTATUSID) {
		STATUSID = sTATUSID;
	}

	@Override
	public String toString() {
		return "CUSTOMEROTP [Id=" + Id + ", CHANNELID=" + CHANNELID + ", APPID=" + APPID + ", RRN=" + RRN + ", OTP=" + OTP + ", OTPVALIDATEDON=" + OTPVALIDATEDON + ", GENERATEDONCHANNELID=" + GENERATEDONCHANNELID + ", CUSTOMERID=" + CUSTOMERID
				+ ", VALIDATEDONCHANNELID=" + VALIDATEDONCHANNELID + ", INVALIDOTPATTEMPTS=" + INVALIDOTPATTEMPTS + ", ACTIVITYID=" + ACTIVITYID + ", CREATEDBY=" + CREATEDBY + ", CREATEDON=" + CREATEDON + ", UPDATEDBY=" + UPDATEDBY + ", UPDATEDON="
				+ UPDATEDON + ", STATUSID=" + STATUSID + "]";
	}
}
