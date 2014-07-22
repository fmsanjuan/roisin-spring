package com.roisin.spring.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.rapidminer.example.ExampleSet;

@Entity
@Access(AccessType.PROPERTY)
public class PreprocessedData extends DomainEntity {

	private String name;

	private String description;

	private ExampleSet exampleSet;

	private PreprocessingForm preprocessingForm;

	private Collection<Process> processes;

	public PreprocessedData() {
		super();
		processes = new ArrayList<Process>();
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

	@Lob
	public ExampleSet getExampleSet() {
		return exampleSet;
	}

	public void setExampleSet(ExampleSet exampleSet) {
		this.exampleSet = exampleSet;
	}

	@OneToOne
	public PreprocessingForm getPreprocessingForm() {
		return preprocessingForm;
	}

	public void setPreprocessingForm(PreprocessingForm preprocessingForm) {
		this.preprocessingForm = preprocessingForm;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "preprocessedData")
	public Collection<Process> getProcesses() {
		return processes;
	}

	public void setProcesses(Collection<Process> processes) {
		this.processes = processes;
	}

}
