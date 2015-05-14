package com.roisin.spring.model;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Selected attribute entity
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
@Entity
@Access(AccessType.PROPERTY)
public class SelectedAttribute extends DomainEntity {

	/**
	 * Attribute name
	 */
	private String name;

	/**
	 * Preprocessing forms
	 */
	private PreprocessingForm preprocessingForm;

	/**
	 * Processes
	 */
	private Collection<Process> processes;

	public SelectedAttribute() {
		super();
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Valid
	@ManyToOne(optional = true)
	public PreprocessingForm getPreprocessingForm() {
		return preprocessingForm;
	}

	public void setPreprocessingForm(final PreprocessingForm preprocessingForm) {
		this.preprocessingForm = preprocessingForm;
	}

	@Valid
	@OneToMany(mappedBy = "label")
	public Collection<Process> getProcesses() {
		return processes;
	}

	public void setProcesses(final Collection<Process> processes) {
		this.processes = processes;
	}

}
