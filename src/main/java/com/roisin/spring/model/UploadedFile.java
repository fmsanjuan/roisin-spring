package com.roisin.spring.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * Uploaded file class wrapper
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
public class UploadedFile {

	/**
	 * Multipart file
	 */
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(final MultipartFile file) {
		this.file = file;
	}
}
