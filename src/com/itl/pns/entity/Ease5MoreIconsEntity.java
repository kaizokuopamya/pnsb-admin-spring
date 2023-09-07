package com.itl.pns.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "EASE5_MORE_ICONS")
public class Ease5MoreIconsEntity {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EASE5_MORE_ICONS_SEQ")
	@SequenceGenerator(name = "EASE5_MORE_ICONS_SEQ", sequenceName = "EASE5_MORE_ICONS_SEQ", allocationSize = 1)
	private Integer id;

	@Column(name = "APPID")
	private BigDecimal appid;
		          
	@Column(name = "APPREDIRECTIONLINK")
	private String appredirectionlink;

	@Column(name = "CREATEDBY")	 
	private BigDecimal createdby;
	     
	@Column(name = "CREATEDON")	
	private Date createdon;
	                    
	@Column(name = "IMAGELINK") 
	private String imagelink; 
	      	
	@Column(name = "MODULENAME")  
	private String modulename;
	  		
	@Column(name = "OPTIONNAME")
	private String optionname;
				
	@Column(name = "OPTIONTYPE")
	private String optiontype;
				
	@Column(name = "PAGENAME")	
	private String pagename;
			
	@Column(name = "REDIRECTIONTYPE")
	private String redirectiontype;	

	@Column(name = "REDIRECTIONURL")
	private String redirectionurl;
			
	@Column(name = "SEQNUMBER")	
	private BigDecimal seqnumber;
		
	@Column(name = "STATUSID")
	private BigDecimal statusid;
	
	@Transient
	private String isActiveAll;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getAppid() {
		return appid;
	}

	public void setAppid(BigDecimal appid) {
		this.appid = appid;
	}

	public String getAppredirectionlink() {
		return appredirectionlink;
	}

	public void setAppredirectionlink(String appredirectionlink) {
		this.appredirectionlink = appredirectionlink;
	}

	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public String getImagelink() {
		return imagelink;
	}

	public void setImagelink(String imagelink) {
		this.imagelink = imagelink;
	}

	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public String getOptionname() {
		return optionname;
	}

	public void setOptionname(String optionname) {
		this.optionname = optionname;
	}

	public String getOptiontype() {
		return optiontype;
	}

	public void setOptiontype(String optiontype) {
		this.optiontype = optiontype;
	}

	public String getPagename() {
		return pagename;
	}

	public void setPagename(String pagename) {
		this.pagename = pagename;
	}

	public String getRedirectiontype() {
		return redirectiontype;
	}

	public void setRedirectiontype(String redirectiontype) {
		this.redirectiontype = redirectiontype;
	}

	public String getRedirectionurl() {
		return redirectionurl;
	}

	public void setRedirectionurl(String redirectionurl) {
		this.redirectionurl = redirectionurl;
	}

	public BigDecimal getSeqnumber() {
		return seqnumber;
	}

	public void setSeqnumber(BigDecimal seqnumber) {
		this.seqnumber = seqnumber;
	}

	public BigDecimal getStatusid() {
		return statusid;
	}

	public void setStatusid(BigDecimal statusid) {
		this.statusid = statusid;
	}

	public String getIsActiveAll() {
		return isActiveAll;
	}

	public void setIsActiveAll(String isActiveAll) {
		this.isActiveAll = isActiveAll;
	}
	
	

}
