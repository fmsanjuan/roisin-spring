package com.roisin.spring.exception;

public class RoisinSpringException extends Exception {

	/**
	 * Serial uid
	 */
	private static final long serialVersionUID = -4001195245796150846L;

	public RoisinSpringException() {

	}

	public RoisinSpringException(final String message) {
		super(message);
	}

	public RoisinSpringException(final Throwable cause) {
		super(cause);
	}

	public RoisinSpringException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
