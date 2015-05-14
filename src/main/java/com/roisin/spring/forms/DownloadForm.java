package com.roisin.spring.forms;

/**
 * Download file form
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
public class DownloadForm {

	/**
	 * File hash
	 */
	private String hash;

	/**
	 * File output format
	 */
	private String outputFormat;

	/**
	 * File name
	 */
	private String fileName;

	public DownloadForm() {
		super();
	}

	public String getHash() {
		return hash;
	}

	public void setHash(final String hash) {
		this.hash = hash;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(final String outputFormat) {
		this.outputFormat = outputFormat;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

}
