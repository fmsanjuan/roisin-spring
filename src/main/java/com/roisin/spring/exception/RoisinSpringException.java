package com.roisin.spring.exception;

public class RoisinSpringException extends Exception {

	private static final long serialVersionUID = -4001195245796150846L;

	public RoisinSpringException() {

	}

	public RoisinSpringException(String message) {
		super(message);
	}

	public RoisinSpringException(Throwable cause) {
		super(cause);
	}

	public RoisinSpringException(String message, Throwable cause) {
		super(message, cause);
	}

}
