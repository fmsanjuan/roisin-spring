package com.roisin.spring.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.roisin.spring.controllers.HomeController;

/**
 * Hash utility class
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
public class HashUtils {

	/**
	 * HashUtils class log
	 */
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

	public static String fileChecksum(final byte[] file, final String digestMethod) {
		String checkSum = StringUtils.EMPTY;
		try {
			final ByteArrayInputStream fileInputStream = new ByteArrayInputStream(file);
			MessageDigest mDiggest = MessageDigest.getInstance(digestMethod);
			// Obtención de DigestInputStream a partir del fichero y de
			// MessageDigest
			final DigestInputStream din = new DigestInputStream(fileInputStream, mDiggest);
			// Procesamiento de bytes
			final byte[] byteArray = new byte[din.available()];
			din.read(byteArray, 0, din.available());
			mDiggest = din.getMessageDigest();
			din.close();
			// Se obtienen los bytes del checksum
			final byte[] digestedArray = mDiggest.digest();
			// Transformación a string
			final StringBuffer strb = new StringBuffer();
			for (int i = 0; i < digestedArray.length; i++) {
				// Se usa el format con %02X para que obligue a devolver el 0.
				// En caso de que transforme los bytes y salga un número entre 0
				// y 15 es decir A, sin este formateo sólo da A, cuando debería
				// devolver 0A.
				strb.append(String.format("%02X", digestedArray[i]));
				// 02 indica el número de caracteres a devolver y x indica que
				// deben de ser devueltos en hexdecimal
			}
			checkSum = strb.toString();
		} catch (NoSuchAlgorithmException e) {
			LOG.error("El algoritmo indicado no es soportado o no ha sido introducido correctamente.");
		} catch (IOException e) {
			LOG.error("No ha sido posible recuperar el número de bytes del DigestedInputStream.");
		}
		return checkSum;
	}

	public static String passwordEncoder(final String password) {
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hash = encoder.encodePassword(password, null);

		return hash;
	}
}
