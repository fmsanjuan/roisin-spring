package com.roisin.spring.utils;

import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

}
