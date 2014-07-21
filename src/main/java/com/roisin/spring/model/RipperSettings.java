package com.roisin.spring.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class RipperSettings extends DomainEntity {

	private String ripperCriterion;

	private Double sampleRatio;

	private Double pureness;

	private Double minimalPruneBenefit;

	private Process process;

	public RipperSettings() {
		super();
	}

	@NotBlank
	public String getRipperCriterion() {
		return ripperCriterion;
	}

	public void setRipperCriterion(String ripperCriterion) {
		this.ripperCriterion = ripperCriterion;
	}

	public Double getSampleRatio() {
		return sampleRatio;
	}

	public void setSampleRatio(Double sampleRatio) {
		this.sampleRatio = sampleRatio;
	}

	public Double getPureness() {
		return pureness;
	}

	public void setPureness(Double pureness) {
		this.pureness = pureness;
	}

	public Double getMinimalPruneBenefit() {
		return minimalPruneBenefit;
	}

	public void setMinimalPruneBenefit(Double minimalPruneBenefit) {
		this.minimalPruneBenefit = minimalPruneBenefit;
	}

	@OneToOne
	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

}
