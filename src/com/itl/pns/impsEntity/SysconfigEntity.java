package com.itl.pns.impsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="SYSCONFIG")
public class SysconfigEntity {

	
		@javax.persistence.Id
		@Column(name = "ID")
		private String id;
		
		@Column(name = "VALUE")
	    private String value;
	    
	    @Column(name = "READPERM")
	    private String readperm;
	    
	    @Column(name = "WRITEPERM")
	    private String writeperm;

	    
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getReadperm() {
			return readperm;
		}

		public void setReadperm(String readperm) {
			this.readperm = readperm;
		}

		public String getWriteperm() {
			return writeperm;
		}

		public void setWriteperm(String writeperm) {
			this.writeperm = writeperm;
		}
	    
	   
}
