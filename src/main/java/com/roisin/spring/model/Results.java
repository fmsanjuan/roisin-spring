package com.roisin.spring.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Results entity
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
@Entity
@Access(AccessType.PROPERTY)
public class Results extends DomainEntity {

	/**
	 * Area under de curve
	 */
	private Double auc;

	/**
	 * Process
	 */
	private Process process;

	/**
	 * Rules
	 */
	private Collection<Rule> rules;

	public Results() {
		super();
		this.rules = new ArrayList<Rule>();
	}

	public Double getAuc() {
		return auc;
	}

	public void setAuc(final Double auc) {
		this.auc = auc;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "results", cascade = CascadeType.ALL)
	public Collection<Rule> getRules() {
		return rules;
	}

	public void setRules(final Collection<Rule> rules) {
		this.rules = rules;
	}

	@Valid
	@OneToOne(optional = true)
	public Process getProcess() {
		return process;
	}

	public void setProcess(final Process process) {
		this.process = process;
	}

}
