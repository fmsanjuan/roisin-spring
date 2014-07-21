package com.roisin.spring.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Rule extends DomainEntity {

	private String premise;

	private String conclusion;

	private Double rulePrecision;

	private Double support;

	private Double tpr;

	private Double fpr;

	private Integer tp;

	private Integer tn;

	private Integer fp;

	private Integer fn;

	private Double auc;

	private Results results;

	public Rule() {
		super();
	}

	@NotBlank
	public String getPremise() {
		return premise;
	}

	public void setPremise(String premise) {
		this.premise = premise;
	}

	@NotBlank
	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public Double getRulePrecision() {
		return rulePrecision;
	}

	public void setRulePrecision(Double rulePrecision) {
		this.rulePrecision = rulePrecision;
	}

	public Double getSupport() {
		return support;
	}

	public void setSupport(Double support) {
		this.support = support;
	}

	public Double getTpr() {
		return tpr;
	}

	public void setTpr(Double tpr) {
		this.tpr = tpr;
	}

	public Double getFpr() {
		return fpr;
	}

	public void setFpr(Double fpr) {
		this.fpr = fpr;
	}

	public Integer getTp() {
		return tp;
	}

	public void setTp(Integer tp) {
		this.tp = tp;
	}

	public Integer getTn() {
		return tn;
	}

	public void setTn(Integer tn) {
		this.tn = tn;
	}

	public Integer getFp() {
		return fp;
	}

	public void setFp(Integer fp) {
		this.fp = fp;
	}

	public Integer getFn() {
		return fn;
	}

	public void setFn(Integer fn) {
		this.fn = fn;
	}

	public Double getAuc() {
		return auc;
	}

	public void setAuc(Double auc) {
		this.auc = auc;
	}

	@Valid
	@ManyToOne(optional = true)
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

}
