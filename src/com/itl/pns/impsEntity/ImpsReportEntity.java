package com.itl.pns.impsEntity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
@Entity
@Table(name="REPORT")
public class ImpsReportEntity {

			@javax.persistence.Id
			@Column(name = "ID")
			@GeneratedValue(strategy=GenerationType.IDENTITY)
			private BigInteger id;
			
			@Column(name = "name")
		    private String name;

			@Column(name = "title")
		    private String title;
		
			@Column(name = "query")
		    private String query;
		
			@Column(name = "file_name_template")
		    private String file_name_template;
		
			@Column(name = "sub_title_template")
		    private String sub_title_template;
		
			@Column(name = "category_id")
		    private BigInteger category_id;

			public BigInteger getId() {
				return id;
			}

			public void setId(BigInteger id) {
				this.id = id;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getQuery() {
				return query;
			}

			public void setQuery(String query) {
				this.query = query;
			}

			public String getFile_name_template() {
				return file_name_template;
			}

			public void setFile_name_template(String file_name_template) {
				this.file_name_template = file_name_template;
			}

			public String getSub_title_template() {
				return sub_title_template;
			}

			public void setSub_title_template(String sub_title_template) {
				this.sub_title_template = sub_title_template;
			}

			public BigInteger getCategory_id() {
				return category_id;
			}

			public void setCategory_id(BigInteger category_id) {
				this.category_id = category_id;
			}
	
}
