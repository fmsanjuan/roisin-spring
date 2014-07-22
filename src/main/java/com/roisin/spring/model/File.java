package com.roisin.spring.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class File extends DomainEntity {

	private String name;

	private String description;

	private byte[] originalFile;

	private String hash;

	private User user;

	private Collection<PreprocessingForm> preprocessingForms;

	public File() {
		super();
		preprocessingForms = new ArrayList<PreprocessingForm>();
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotBlank
	public byte[] getOriginalFile() {
		return originalFile;
	}

	public void setOriginalFile(byte[] originalFile) {
		this.originalFile = originalFile;
	}

	@NotBlank
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@Valid
	@ManyToOne(optional = true)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "file")
	public Collection<PreprocessingForm> getPreprocessingForms() {
		return preprocessingForms;
	}

	public void setPreprocessingForms(Collection<PreprocessingForm> preprocessingForms) {
		this.preprocessingForms = preprocessingForms;
	}

}
