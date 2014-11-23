package com.roisin.spring.utils;

import static com.roisin.spring.utils.Constants.DOT_SYMBOL;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;
import com.roisin.spring.model.File;

public class FileUtils {

	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

	public static void writeFileFromByteArray(byte[] byteArray, String path) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(path);
			fos.write(byteArray);
			fos.close();
		} catch (IOException e) {
			logger.error("No ha sido posible recuperar el fichero de base de datos para almacenarlo en el servidor");
		}
	}

	public static byte[] getFileFromPath(String path) {
		byte fileContent[] = null;
		try {
			java.io.File file = new java.io.File(path);
			fileContent = Files.toByteArray(file);
		} catch (IOException e) {
			logger.error("No ha sido posible recuperar el fichero del servidor");
		}
		return fileContent;
	}

	public static String getFileFormat(File file) {
		return StringUtils.substringAfterLast(file.getName(), DOT_SYMBOL);
	}

	public static String getFileTmpPath(File file) throws NamingException {
		return getStoragePath() + file.getHash() + DOT_SYMBOL + getFileFormat(file);
	}

	public static String getFileDownloadPath(File file, String fileFormat) throws NamingException {
		return getDownloadPath() + file.getHash() + DOT_SYMBOL + fileFormat;
	}

	public static String getExportFileName(String originalFileName, String exportFormat) {
		String name = StringUtils.substringBeforeLast(originalFileName, DOT_SYMBOL);
		return name + DOT_SYMBOL + exportFormat;
	}

	public static String getStoragePath() throws NamingException {
		return (String) (new InitialContext()).lookup("java:comp/env/storagePath");
	}

	public static String getDownloadPath() throws NamingException {
		return (String) (new InitialContext()).lookup("java:comp/env/downloadPath");
	}

	public static String getConvertPath() throws NamingException {
		return (String) (new InitialContext()).lookup("java:comp/env/convertPath");
	}

}
