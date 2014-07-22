package com.roisin.spring.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class SelectedAttribute extends DomainEntity {

	private String name;

	private PreprocessingForm preprocessingForm;

	private Process process;

	public SelectedAttribute() {
		super();
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Valid
	@ManyToOne(optional = true)
	public PreprocessingForm getPreprocessingForm() {
		return preprocessingForm;
	}

	public void setPreprocessingForm(PreprocessingForm preprocessingForm) {
		this.preprocessingForm = preprocessingForm;
	}

	@OneToOne
	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

}
