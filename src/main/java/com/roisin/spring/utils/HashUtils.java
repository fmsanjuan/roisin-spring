package com.roisin.spring.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.roisin.spring.controllers.HomeController;

public class HashUtils {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	public static String fileChecksum(byte[] file, String digestMethod) {
		String checkSum = new String();
		try {
			ByteArrayInputStream fileInputStream = new ByteArrayInputStream(file);
			MessageDigest md = MessageDigest.getInstance(digestMethod);
			// Obtención de DigestInputStream a partir del fichero y de
			// MessageDigest
			DigestInputStream din = new DigestInputStream(fileInputStream, md);
			// Procesamiento de bytes
			byte[] byteArray = new byte[din.available()];
			din.read(byteArray, 0, din.available());
			md = din.getMessageDigest();
			din.close();
			// Se obtienen los bytes del checksum
			byte[] digestedArray = md.digest();
			// Transformación a string
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < digestedArray.length; i++) {
				// Se usa el format con %02X para que obligue a devolver el 0.
				// En caso de que transforme los bytes y salga un número entre 0
				// y 15 es decir A, sin este formateo sólo da A, cuando debería
				// devolver 0A.
				sb.append(String.format("%02X", digestedArray[i]));
				// 02 indica el número de caracteres a devolver y x indica que
				// deben de ser devueltos en hexdecimal
			}
			checkSum = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			logger.error("El algoritmo indicado no es soportado o no ha sido introducido correctamente.");
		} catch (IOException e) {
			logger.error("No ha sido posible recuperar el número de bytes del DigestedInputStream.");
		}
		return checkSum;
	}

	public static String passwordEncoder(String password) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String hash = encoder.encodePassword(password, null);

		return hash;
	}
}
