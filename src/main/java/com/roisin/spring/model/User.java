package com.roisin.spring.model;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.googlecode.charts4j.collect.Lists;
import com.roisin.spring.security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class User extends DomainEntity {

	private String name;

	private String surname;

	private String email;

	private String nationality;

	private String city;

	private UserAccount userAccount;

	private Collection<Examples> examples;

	public User() {
		super();
		this.examples = Lists.newArrayList();
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@NotBlank
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotBlank
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@NotBlank
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "user")
	public Collection<Examples> getExamples() {
		return examples;
	}

	public void setExamples(Collection<Examples> examples) {
		this.examples = examples;
	}

}
