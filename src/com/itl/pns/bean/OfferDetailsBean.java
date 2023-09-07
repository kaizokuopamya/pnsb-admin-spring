package com.itl.pns.bean;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.Timestamp;

public class OfferDetailsBean {
	 private BigInteger ID;  
		
	    private String baseImageLarge;
	    
	    private String baseImageSmall;
	    
	    private Clob base64ImageLarge;
	    
	    private Clob base64ImageSmall;
		
	    private String imgdescLarge;
	    
	    private String imgdescSmall;
	    
	    private String imageCaption;
		
	    private String createdBy;
		
	    private String createdOn;
		
	    private String updatedBy;
		
	    private String updatedOn;
		
	    private char isActive;
		
		private char isdeleted;
		
		private BigInteger appId;  
		
		private BigInteger seqNumber;  
		
		private String productName;
		
	    private Timestamp validFrom;
		
		private Timestamp validTo;
		
		private BigInteger statusId;

		private String latitude;
	    
	    private String longitude;
	    
	    private String weblink;
	    
	    private String serviceType;
		
	    public String getServiceType() {
			return serviceType;
		}

		public void setServiceType(String serviceType) {
			this.serviceType = serviceType;
		}
	    
		public String getWeblink() {
			return weblink;
		}

		public void setWeblink(String weblink) {
			this.weblink = weblink;
		}

		public String getLatitude() {
			return latitude;
		}

		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}

		public String getLongitude() {
			return longitude;
		}

		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}

		public BigInteger getStatusId() {
			return statusId;
		}

		public void setStatusId(BigInteger statusId) {
			this.statusId = statusId;
		}

		public Timestamp getValidFrom() {
			return validFrom;
		}

		public void setValidFrom(Timestamp validFrom) {
			this.validFrom = validFrom;
		}

		public Timestamp getValidTo() {
			return validTo;
		}

		public void setValidTo(Timestamp validTo) {
			this.validTo = validTo;
		}

		public BigInteger getAppId() {
			return appId;
		}

		public void setAppId(BigInteger appId) {
			this.appId = appId;
		}

		public BigInteger getSeqNumber() {
			return seqNumber;
		}

		public void setSeqNumber(BigInteger seqNumber) {
			this.seqNumber = seqNumber;
		}

		public String statusName;

		public String getStatusName() {
			return statusName;
		}

		public void setStatusName(String statusName) {
			this.statusName = statusName;
		}

		public String getBaseImageLarge() {
			return baseImageLarge;
		}

		public void setBaseImageLarge(String baseImageLarge) {
			this.baseImageLarge = baseImageLarge;
		}

		public String getBaseImageSmall() {
			return baseImageSmall;
		}

		public void setBaseImageSmall(String baseImageSmall) {
			this.baseImageSmall = baseImageSmall;
		}

		public Clob getBase64ImageLarge() {
			return base64ImageLarge;
		}

		public void setBase64ImageLarge(Clob base64ImageLarge) {
			this.base64ImageLarge = base64ImageLarge;
		}

		public Clob getBase64ImageSmall() {
			return base64ImageSmall;
		}

		public void setBase64ImageSmall(Clob base64ImageSmall) {
			this.base64ImageSmall = base64ImageSmall;
		}

		public String getImgdescLarge() {
			return imgdescLarge;
		}

		public void setImgdescLarge(String imgdescLarge) {
			this.imgdescLarge = imgdescLarge;
		}

		public String getImgdescSmall() {
			return imgdescSmall;
		}

		public void setImgdescSmall(String imgdescSmall) {
			this.imgdescSmall = imgdescSmall;
		}

	   

		public BigInteger getID() {
			return ID;
		}

		public void setID(BigInteger iD) {
			ID = iD;
		}

		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}



		public String getUpdatedBy() {
			return updatedBy;
		}

		public void setUpdatedBy(String updatedBy) {
			this.updatedBy = updatedBy;
		}

		

		public char getIsActive() {
			return isActive;
		}

		public void setIsActive(char isActive) {
			this.isActive = isActive;
		}

		public char getIsdeleted() {
			return isdeleted;
		}

		public void setIsdeleted(char isdeleted) {
			this.isdeleted = isdeleted;
		}

		public String getCreatedOn() {
			return createdOn;
		}

		public void setCreatedOn(String createdOn) {
			this.createdOn = createdOn;
		}

		public String getUpdatedOn() {
			return updatedOn;
		}

		public void setUpdatedOn(String updatedOn) {
			this.updatedOn = updatedOn;
		}

		public String getImageCaption() {
			return imageCaption;
		}

		public void setImageCaption(String imageCaption) {
			this.imageCaption = imageCaption;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}
		
		
		
}
