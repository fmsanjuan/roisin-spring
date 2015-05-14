package com.roisin.spring.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.roisin.spring.security.UserAccount;

/**
 * User entity
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
@Entity
@Access(AccessType.PROPERTY)
public class User extends DomainEntity {

	/**
	 * User name
	 */
	private String name;

	/**
	 * User surname
	 */
	private String surname;

	/**
	 * User email
	 */
	private String email;

	/**
	 * User nationality
	 */
	private String nationality;

	/**
	 * User city
	 */
	private String city;

	/**
	 * User account
	 */
	private UserAccount userAccount;

	public User() {
		super();
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	public String getNationality() {
		return nationality;
	}

	public void setNationality(final String nationality) {
		this.nationality = nationality;
	}

	@NotBlank
	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
