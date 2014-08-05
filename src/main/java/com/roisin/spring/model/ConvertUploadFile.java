package com.roisin.spring.model;

import org.springframework.web.multipart.MultipartFile;

public class ConvertUploadFile {

	private MultipartFile file;

	private String outputFormat;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}

}
