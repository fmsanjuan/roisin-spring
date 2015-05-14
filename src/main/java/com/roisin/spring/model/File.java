package com.roisin.spring.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * File entity
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
@Entity
@Access(AccessType.PROPERTY)
public class File extends DomainEntity {

	/**
	 * File name
	 */
	private String name;

	/**
	 * File description
	 */
	private String description;

	/**
	 * File
	 */
	private byte[] originalFile;

	/**
	 * File hash
	 */
	private String hash;

	/**
	 * File owner
	 */
	private User user;

	/**
	 * Associated preprocessing forms
	 */
	private Collection<PreprocessingForm> preprocessingForms;

	public File() {
		super();
		preprocessingForms = new ArrayList<PreprocessingForm>();
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public byte[] getOriginalFile() {
		return originalFile;
	}

	public void setOriginalFile(final byte[] originalFile) {
		this.originalFile = originalFile;
	}

	@NotBlank
	public String getHash() {
		return hash;
	}

	public void setHash(final String hash) {
		this.hash = hash;
	}

	@Valid
	@ManyToOne(optional = true)
	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "file", cascade = CascadeType.ALL)
	public Collection<PreprocessingForm> getPreprocessingForms() {
		return preprocessingForms;
	}

	public void setPreprocessingForms(final Collection<PreprocessingForm> preprocessingForms) {
		this.preprocessingForms = preprocessingForms;
	}

}
