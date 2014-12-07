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

	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	@Value("${storage.path}")
	private String storagePath;
	
	@Value("${download.path}")
	private String downloadPath;
	
	@Value("${convert.path}")
	private String convertPath;
	
	@Value("${activation.token}")
	private String activationToken;
	
	public void writeFileFromByteArray(byte[] byteArray, String path) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(path);
			fos.write(byteArray);
			fos.close();
		} catch (IOException e) {
			logger.error("No ha sido posible recuperar el fichero de base de datos para almacenarlo en el servidor");
		}
	}

	public byte[] getFileFromPath(String path) {
		byte fileContent[] = null;
		try {
			java.io.File file = new java.io.File(path);
			fileContent = Files.toByteArray(file);
		} catch (IOException e) {
			logger.error("No ha sido posible recuperar el fichero del servidor");
		}
		return fileContent;
	}

	public String getFileFormat(File file) {
		return StringUtils.substringAfterLast(file.getName(), DOT_SYMBOL);
	}

	public String getFileTmpPath(File file) {
		return getStoragePath() + file.getHash() + DOT_SYMBOL + getFileFormat(file);
	}

	public String getFileDownloadPath(File file, String fileFormat) {
		return getDownloadPath() + file.getHash() + DOT_SYMBOL + fileFormat;
	}

	public static String getExportFileName(String originalFileName, String exportFormat) {
		String name = StringUtils.substringBeforeLast(originalFileName, DOT_SYMBOL);
		return name + DOT_SYMBOL + exportFormat;
	}

	public String getStoragePath() {
		return storagePath;
	}

	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getConvertPath() {
		return convertPath;
	}

	public void setConvertPath(String convertPath) {
		this.convertPath = convertPath;
	}

	public String getActivationToken() {
		return activationToken;
	}

	public void setActivationToken(String activationToken) {
		this.activationToken = activationToken;
	}

//	public static String getStoragePath() throws NamingException {
//		return (String) (new InitialContext()).lookup("java:comp/env/storagePath");
//	}
//
//	public static String getDownloadPath() throws NamingException {
//		return (String) (new InitialContext()).lookup("java:comp/env/downloadPath");
//	}
//
//	public static String getConvertPath() throws NamingException {
//		return (String) (new InitialContext()).lookup("java:comp/env/convertPath");
//	}
//
//	public static String getActivationToken() {
//		try {
//			return (String) (new InitialContext()).lookup("java:comp/env/activationToken");
//		} catch (NamingException e) {
//			return StringUtils.EMPTY;
//		}
//	}

}
