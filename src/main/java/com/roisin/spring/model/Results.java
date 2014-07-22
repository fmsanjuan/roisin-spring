package com.roisin.spring.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Results extends DomainEntity {

	private Double auc;

	private Process process;

	private Collection<Rule> rules;

	public Results() {
		super();
		this.rules = new ArrayList<Rule>();
	}

	public Double getAuc() {
		return auc;
	}

	public void setAuc(Double auc) {
		this.auc = auc;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "results")
	public Collection<Rule> getRules() {
		return rules;
	}

	public void setRules(Collection<Rule> rules) {
		this.rules = rules;
	}

	@Valid
	@OneToOne(optional = true)
	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

}
