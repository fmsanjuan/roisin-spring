package com.roisin.spring.model;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.rapidminer.example.ExampleSet;

@Entity
@Access(AccessType.PROPERTY)
public class Examples extends DomainEntity {

	private String filePath;

	private ExampleSet exampleSet;

	private User user;

	private Collection<Process> processes;

	public Examples() {
		super();
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Lob
	public ExampleSet getExampleSet() {
		return exampleSet;
	}

	public void setExampleSet(ExampleSet exampleSet) {
		this.exampleSet = exampleSet;
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
	@OneToMany(mappedBy = "examples")
	public Collection<Process> getProcesses() {
		return processes;
	}

	public void setProcesses(Collection<Process> processes) {
		this.processes = processes;
	}

}
