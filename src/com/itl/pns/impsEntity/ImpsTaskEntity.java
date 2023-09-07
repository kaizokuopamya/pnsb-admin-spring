package com.itl.pns.impsEntity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name="TASK")
public class ImpsTaskEntity {

		@javax.persistence.Id
		@Column(name = "ID")
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private BigInteger id;
		
		@Column(name = "TYPE")
	    private String type;
		
		@Column(name = "task_desc")
	    private String task_desc;

		public BigInteger getId() {
			return id;
		}

		public void setId(BigInteger id) {
			this.id = id;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getTask_desc() {
			return task_desc;
		}

		public void setTask_desc(String task_desc) {
			this.task_desc = task_desc;
		}

		
		
			
}
