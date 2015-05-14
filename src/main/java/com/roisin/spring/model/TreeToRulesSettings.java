package com.roisin.spring.model;

import static com.roisin.spring.utils.ProcessConstants.GAIN_RATIO;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Tree to rules settings entity
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
@Entity
@Access(AccessType.PROPERTY)
public class TreeToRulesSettings extends DomainEntity {

	/**
	 * Tree to rules criterion
	 */
	private String tree2RulesCriterion;

	/**
	 * Minimal size for split
	 */
	private Integer minimalSizeForSplit;

	/**
	 * Minimal leaf size
	 */
	private Integer minimalLeafSize;

	/**
	 * Minimal gain
	 */
	private Double minimalGain;

	/**
	 * Maximal depth
	 */
	private Integer maximalDepth;

	/**
	 * Confidence
	 */
	private Double confidence;

	/**
	 * Prepruning alternatives
	 */
	private Integer numberOfPrepruningAlternatives;

	/**
	 * No prepruning option
	 */
	private Boolean noPrepruning;

	/**
	 * No pruning option
	 */
	private Boolean noPruning;

	/**
	 * Process
	 */
	private Process process;

	public TreeToRulesSettings() {
		super();
		this.tree2RulesCriterion = GAIN_RATIO;
		this.minimalSizeForSplit = 4;
		this.minimalLeafSize = 2;
		this.minimalGain = 0.1;
		this.maximalDepth = 20;
		this.confidence = 0.25;
		this.numberOfPrepruningAlternatives = 3;
		this.noPrepruning = false;
		this.noPruning = false;
	}

	public String getTree2RulesCriterion() {
		return tree2RulesCriterion;
	}

	public void setTree2RulesCriterion(final String tree2RulesCriterion) {
		this.tree2RulesCriterion = tree2RulesCriterion;
	}

	public Integer getMinimalSizeForSplit() {
		return minimalSizeForSplit;
	}

	public void setMinimalSizeForSplit(final Integer minimalSizeForSplit) {
		this.minimalSizeForSplit = minimalSizeForSplit;
	}

	public Integer getMinimalLeafSize() {
		return minimalLeafSize;
	}

	public void setMinimalLeafSize(final Integer minimalLeafSize) {
		this.minimalLeafSize = minimalLeafSize;
	}

	public Double getMinimalGain() {
		return minimalGain;
	}

	public void setMinimalGain(final Double minimalGain) {
		this.minimalGain = minimalGain;
	}

	public Integer getMaximalDepth() {
		return maximalDepth;
	}

	public void setMaximalDepth(final Integer maximalDepth) {
		this.maximalDepth = maximalDepth;
	}

	public Double getConfidence() {
		return confidence;
	}

	public void setConfidence(final Double confidence) {
		this.confidence = confidence;
	}

	public Integer getNumberOfPrepruningAlternatives() {
		return numberOfPrepruningAlternatives;
	}

	public void setNumberOfPrepruningAlternatives(final Integer numberOfPrepruningAlternatives) {
		this.numberOfPrepruningAlternatives = numberOfPrepruningAlternatives;
	}

	public Boolean getNoPrepruning() {
		return noPrepruning;
	}

	public void setNoPrepruning(final Boolean noPrepruning) {
		this.noPrepruning = noPrepruning;
	}

	public Boolean getNoPruning() {
		return noPruning;
	}

	public void setNoPruning(final Boolean noPruning) {
		this.noPruning = noPruning;
	}

	@OneToOne
	public Process getProcess() {
		return process;
	}

	public void setProcess(final Process process) {
		this.process = process;
	}

}
