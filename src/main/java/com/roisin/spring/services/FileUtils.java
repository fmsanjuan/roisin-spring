package com.roisin.spring.services;

import static com.roisin.spring.utils.Constants.DOT_SYMBOL;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.io.Files;
import com.roisin.spring.model.File;

@Service
@Transactional
public class FileUtils {

	/**
	 * Class log
	 */
	private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * Storage path for files uploaded by the user
	 */
	@Value("${storage.path}")
	private String storagePath;

	/**
	 * Storage path for files that will be downloaded by the user
	 */
	@Value("${download.path}")
	private String downloadPath;

	/**
	 * Storage path for Roisin conversion functionality
	 */
	@Value("${convert.path}")
	private String convertPath;

	/**
	 * Activation token
	 */
	@Value("${activation.token}")
	private String activationToken;

	public void writeFileFromByteArray(final byte[] byteArray, final String path) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(path);
			fos.write(byteArray);
			fos.close();
		} catch (IOException e) {
			LOG.error("No ha sido posible recuperar el fichero de base de datos para almacenarlo en el servidor");
		}
	}

	public byte[] getFileFromPath(final String path) {
		byte fileContent[] = null;
		try {
			final java.io.File file = new java.io.File(path);
			fileContent = Files.toByteArray(file);
		} catch (IOException e) {
			LOG.error("No ha sido posible recuperar el fichero del servidor");
		}
		return fileContent;
	}

	public String getFileFormat(final File file) {
		return StringUtils.substringAfterLast(file.getName(), DOT_SYMBOL);
	}

	public String getFileTmpPath(final File file) {
		return getStoragePath() + file.getHash() + DOT_SYMBOL + getFileFormat(file);
	}

	public String getFileDownloadPath(final File file, final String fileFormat) {
		return getDownloadPath() + file.getHash() + DOT_SYMBOL + fileFormat;
	}

	public static String getExportFileName(final String originalFileName, final String exportFormat) {
		final String name = StringUtils.substringBeforeLast(originalFileName, DOT_SYMBOL);
		return name + DOT_SYMBOL + exportFormat;
	}

	public String getStoragePath() {
		return storagePath;
	}

	public void setStoragePath(final String storagePath) {
		this.storagePath = storagePath;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(final String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getConvertPath() {
		return convertPath;
	}

	public void setConvertPath(final String convertPath) {
		this.convertPath = convertPath;
	}

	public String getActivationToken() {
		return activationToken;
	}

	public void setActivationToken(final String activationToken) {
		this.activationToken = activationToken;
	}

}
