package com.roisin.spring.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Rule entity
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
@Entity
@Access(AccessType.PROPERTY)
public class Rule extends DomainEntity {

	/**
	 * Premise
	 */
	private String premise;

	/**
	 * Conclusion
	 */
	private String conclusion;

	/**
	 * Precision
	 */
	private Double rulePrecision;

	/**
	 * Support
	 */
	private Double support;

	/**
	 * True positive rate
	 */
	private Double tpr;

	/**
	 * False positive rate
	 */
	private Double fpr;

	/**
	 * True positives
	 */
	private Integer tp;

	/**
	 * True negatives
	 */
	private Integer tn;

	/**
	 * False positives
	 */
	private Integer fp;

	/**
	 * False negatives
	 */
	private Integer fn;

	/**
	 * Area under the curve
	 */
	private Double auc;

	/**
	 * Results
	 */
	private Results results;

	public Rule() {
		super();
	}

	@NotBlank
	public String getPremise() {
		return premise;
	}

	public void setPremise(final String premise) {
		this.premise = premise;
	}

	@NotBlank
	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(final String conclusion) {
		this.conclusion = conclusion;
	}

	public Double getRulePrecision() {
		return rulePrecision;
	}

	public void setRulePrecision(final Double rulePrecision) {
		this.rulePrecision = rulePrecision;
	}

	public Double getSupport() {
		return support;
	}

	public void setSupport(final Double support) {
		this.support = support;
	}

	public Double getTpr() {
		return tpr;
	}

	public void setTpr(final Double tpr) {
		this.tpr = tpr;
	}

	public Double getFpr() {
		return fpr;
	}

	public void setFpr(final Double fpr) {
		this.fpr = fpr;
	}

	public Integer getTp() {
		return tp;
	}

	public void setTp(final Integer tp) {
		this.tp = tp;
	}

	public Integer getTn() {
		return tn;
	}

	public void setTn(final Integer tn) {
		this.tn = tn;
	}

	public Integer getFp() {
		return fp;
	}

	public void setFp(final Integer fp) {
		this.fp = fp;
	}

	public Integer getFn() {
		return fn;
	}

	public void setFn(final Integer fn) {
		this.fn = fn;
	}

	public Double getAuc() {
		return auc;
	}

	public void setAuc(final Double auc) {
		this.auc = auc;
	}

	@Valid
	@ManyToOne(optional = true)
	public Results getResults() {
		return results;
	}

	public void setResults(final Results results) {
		this.results = results;
	}

}
