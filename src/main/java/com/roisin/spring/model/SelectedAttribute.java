package com.roisin.spring.model;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class SelectedAttribute extends DomainEntity {

	private String name;

	private PreprocessingForm preprocessingForm;

	private Collection<Process> processes;

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

	@Valid
	@OneToMany(mappedBy = "label")
	public Collection<Process> getProcess() {
		return processes;
	}

	public void setProcess(Collection<Process> processes) {
		this.processes = processes;
	}

}
