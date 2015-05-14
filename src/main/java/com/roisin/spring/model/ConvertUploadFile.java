package com.roisin.spring.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * Convert upload file wrapper for data converter functionality
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
public class ConvertUploadFile {

	/**
	 * Multipart file
	 */
	private MultipartFile file;

	/**
	 * Output format
	 */
	private String outputFormat;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(final MultipartFile file) {
		this.file = file;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(final String outputFormat) {
		this.outputFormat = outputFormat;
	}

}
