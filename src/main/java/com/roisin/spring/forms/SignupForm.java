package com.roisin.spring.forms;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Sign up form
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
public class SignupForm {

	/**
	 * Name
	 */
	private String name;

	/**
	 * Surname
	 */
	private String surname;

	/**
	 * Email
	 */
	private String email;

	/**
	 * City
	 */
	private String city;

	/**
	 * Nationality
	 */
	private String nationality;

	/**
	 * Password
	 */
	private String password;

	/**
	 * Repeat password
	 */
	private String repeatPassword;

	/**
	 * Identifier
	 */
	private int id;

	/**
	 * Version
	 */
	private int version;

	public SignupForm() {
		super();
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	@NotBlank
	public String getNationality() {
		return nationality;
	}

	public void setNationality(final String nationality) {
		this.nationality = nationality;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(final String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

}
