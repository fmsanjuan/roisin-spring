package com.roisin.spring.model;

import static com.roisin.spring.utils.ProcessConstants.INFORMATION_GAIN;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Ripper algorithm configuration
 *
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
@Entity
@Access(AccessType.PROPERTY)
public class RipperSettings extends DomainEntity {

	/**
	 * Ripper critetion
	 */
	private String ripperCriterion;

	/**
	 * Sample ratio
	 */
	private Double sampleRatio;

	/**
	 * Pureness
	 */
	private Double pureness;

	/**
	 * Minimal prune benefit
	 */
	private Double minimalPruneBenefit;

	/**
	 * Process
	 */
	private Process process;

	public RipperSettings() {
		super();
		this.ripperCriterion = INFORMATION_GAIN;
		this.sampleRatio = 0.9;
		this.pureness = 0.9;
		this.minimalPruneBenefit = 0.25;
	}

	@NotBlank
	public String getRipperCriterion() {
		return ripperCriterion;
	}

	public void setRipperCriterion(final String ripperCriterion) {
		this.ripperCriterion = ripperCriterion;
	}

	public Double getSampleRatio() {
		return sampleRatio;
	}

	public void setSampleRatio(final Double sampleRatio) {
		this.sampleRatio = sampleRatio;
	}

	public Double getPureness() {
		return pureness;
	}

	public void setPureness(final Double pureness) {
		this.pureness = pureness;
	}

	public Double getMinimalPruneBenefit() {
		return minimalPruneBenefit;
	}

	public void setMinimalPruneBenefit(final Double minPruneBenefit) {
		this.minimalPruneBenefit = minPruneBenefit;
	}

	@OneToOne
	public Process getProcess() {
		return process;
	}

	public void setProcess(final Process process) {
		this.process = process;
	}

}
