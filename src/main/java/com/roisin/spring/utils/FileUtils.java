package com.roisin.spring.utils;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	public static String getFileFormat(File file) {
		return StringUtils.substringAfterLast(file.getName(), Constants.DOT_SYMBOL);
	}

	public static String getFileTmpPath(File file) {
		return Constants.STORAGE_PATH + file.getHash() + Constants.DOT_SYMBOL + getFileFormat(file);
	}

	public static String getFileDownloadPath(File file, String fileFormat) {
		return Constants.DOWNLOAD_PATH + file.getHash() + Constants.DOT_SYMBOL + fileFormat;
	}

	public static String getExportFileName(String originalFileName, String exportFormat) {
		String name = StringUtils.substringBeforeLast(originalFileName, Constants.DOT_SYMBOL);
		return name + Constants.DOT_SYMBOL + exportFormat;
	}

}
