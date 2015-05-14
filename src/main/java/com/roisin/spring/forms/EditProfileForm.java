package com.roisin.spring.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class EditProfileForm {

	/**
	 * Profile edition name
	 */
	private String name;

	/**
	 * Profile edition surname
	 */
	private String surname;

	/**
	 * Profile edition email
	 */
	private String email;

	/**
	 * Profile edition city
	 */
	private String city;

	/**
	 * Profile edition nationality
	 */
	private String nationality;

	/**
	 * Profile edition old password
	 */
	private String oldPassword;

	/**
	 * Profile edition new password
	 */
	private String newPassword;

	/**
	 * Profile edition repeat password
	 */
	private String repeatNewPassword;

	/**
	 * Profile identifier
	 */
	private int id;

	/**
	 * Profile version
	 */
	private int version;

	public EditProfileForm() {
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

	public void setId(final int identifier) {
		this.id = identifier;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(final String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(final String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatNewPassword() {
		return repeatNewPassword;
	}

	public void setRepeatNewPassword(final String repeatNewPassword) {
		this.repeatNewPassword = repeatNewPassword;
	}

}
