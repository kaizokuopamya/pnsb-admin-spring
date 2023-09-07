package com.itl.pns.bean;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class EmailBean {
	@NotEmpty
	private List<String> to;
	private List<String> cc;
	private List<String> bcc;

	@NotEmpty
	@NotBlank
	@NotNull
	private String subject;

	@NotEmpty
	@NotBlank
	private String body;
	private MultipartFile[] files;
	private List<FileBean> filesBean;

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public List<String> getCc() {
		return cc;
	}

	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	public List<String> getBcc() {
		return bcc;
	}

	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public List<FileBean> getFilesBean() {
		return filesBean;
	}

	public void setFilesBean(List<FileBean> filesBean) {
		this.filesBean = filesBean;
	}

}
