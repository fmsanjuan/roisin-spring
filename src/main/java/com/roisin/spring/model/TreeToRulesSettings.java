package com.roisin.spring.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
public class TreeToRulesSettings extends DomainEntity {

	private String tree2RulesCriterion;

	private Integer minimalSizeForSplit;

	private Integer minimalLeafSize;

	private Double minimalGain;

	private Integer maximalDepth;

	private Double confidence;

	private Integer numberOfPrepruningAlternatives;

	private Boolean noPrepruning;

	private Boolean noPruning;

	private Process process;

	public TreeToRulesSettings() {
		super();
	}

	public String getTree2RulesCriterion() {
		return tree2RulesCriterion;
	}

	public void setTree2RulesCriterion(String tree2RulesCriterion) {
		this.tree2RulesCriterion = tree2RulesCriterion;
	}

	public Integer getMinimalSizeForSplit() {
		return minimalSizeForSplit;
	}

	public void setMinimalSizeForSplit(Integer minimalSizeForSplit) {
		this.minimalSizeForSplit = minimalSizeForSplit;
	}

	public Integer getMinimalLeafSize() {
		return minimalLeafSize;
	}

	public void setMinimalLeafSize(Integer minimalLeafSize) {
		this.minimalLeafSize = minimalLeafSize;
	}

	public Double getMinimalGain() {
		return minimalGain;
	}

	public void setMinimalGain(Double minimalGain) {
		this.minimalGain = minimalGain;
	}

	public Integer getMaximalDepth() {
		return maximalDepth;
	}

	public void setMaximalDepth(Integer maximalDepth) {
		this.maximalDepth = maximalDepth;
	}

	public Double getConfidence() {
		return confidence;
	}

	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}

	public Integer getNumberOfPrepruningAlternatives() {
		return numberOfPrepruningAlternatives;
	}

	public void setNumberOfPrepruningAlternatives(Integer numberOfPrepruningAlternatives) {
		this.numberOfPrepruningAlternatives = numberOfPrepruningAlternatives;
	}

	public Boolean getNoPrepruning() {
		return noPrepruning;
	}

	public void setNoPrepruning(Boolean noPrepruning) {
		this.noPrepruning = noPrepruning;
	}

	public Boolean getNoPruning() {
		return noPruning;
	}

	public void setNoPruning(Boolean noPruning) {
		this.noPruning = noPruning;
	}

	@OneToOne
	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

}
