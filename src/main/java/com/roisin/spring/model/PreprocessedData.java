package com.roisin.spring.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.rapidminer.example.ExampleSet;

/**
 * Preprocessed data entity
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
@Entity
@Access(AccessType.PROPERTY)
public class PreprocessedData extends DomainEntity {

	/**
	 * Preprocessed data name
	 */
	private String name;

	/**
	 * Preprocessed data description
	 */
	private String description;

	/**
	 * Preprocessed data example set
	 */
	private ExampleSet exampleSet;

	/**
	 * Preprocessing form
	 */
	private PreprocessingForm preprocessingForm;

	/**
	 * Processes
	 */
	private Collection<Process> processes;

	public PreprocessedData() {
		super();
		processes = new ArrayList<Process>();
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

	@Lob
	public ExampleSet getExampleSet() {
		return exampleSet;
	}

	public void setExampleSet(final ExampleSet exampleSet) {
		this.exampleSet = exampleSet;
	}

	@OneToOne(cascade = CascadeType.ALL)
	public PreprocessingForm getPreprocessingForm() {
		return preprocessingForm;
	}

	public void setPreprocessingForm(final PreprocessingForm preprocessingForm) {
		this.preprocessingForm = preprocessingForm;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "preprocessedData", cascade = CascadeType.ALL)
	public Collection<Process> getProcesses() {
		return processes;
	}

	public void setProcesses(final Collection<Process> processes) {
		this.processes = processes;
	}

}
