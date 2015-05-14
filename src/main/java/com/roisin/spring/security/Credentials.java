/* Credentials.java
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package com.roisin.spring.security;

import javax.validation.constraints.Size;

public class Credentials {

	// Attributes -------------------------------------------------------------

	/**
	 * Credential username
	 */
	private String username;

	/**
	 * Credential password
	 */
	private String password;

	// Constructors -----------------------------------------------------------

	public Credentials() {
		super();
	}

	@Size(min = 5, max = 32)
	public String getUsername() {
		return username;
	}

	public void setJ_username(final String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

}
