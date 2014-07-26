package com.roisin.spring.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.roisin.spring.utils.ProcessConstants;

@Entity
@Access(AccessType.PROPERTY)
public class SubgroupSettings extends DomainEntity {

	private String mode;

	private String utilityFunction;

	private Double minUtility;

	private Integer kBestRules;

	private String ruleGeneration;

	private Integer maxDepth;

	private Double minCoverage;

	private Process process;

	public SubgroupSettings() {
		super();
		this.mode = ProcessConstants.K_BEST_RULES;
		this.utilityFunction = ProcessConstants.WRACC;
		this.minUtility = 0.0;
		this.kBestRules = 10;
		this.ruleGeneration = ProcessConstants.BOTH;
		this.maxDepth = 5;
		this.minCoverage = 0.0;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getUtilityFunction() {
		return utilityFunction;
	}

	public void setUtilityFunction(String utilityFunction) {
		this.utilityFunction = utilityFunction;
	}

	public Double getMinUtility() {
		return minUtility;
	}

	public void setMinUtility(Double minUtility) {
		this.minUtility = minUtility;
	}

	public Integer getkBestRules() {
		return kBestRules;
	}

	public void setkBestRules(Integer kBestRules) {
		this.kBestRules = kBestRules;
	}

	public String getRuleGeneration() {
		return ruleGeneration;
	}

	public void setRuleGeneration(String ruleGeneration) {
		this.ruleGeneration = ruleGeneration;
	}

	public Integer getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(Integer maxDepth) {
		this.maxDepth = maxDepth;
	}

	public Double getMinCoverage() {
		return minCoverage;
	}

	public void setMinCoverage(Double minCoverage) {
		this.minCoverage = minCoverage;
	}

	@OneToOne
	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

}
