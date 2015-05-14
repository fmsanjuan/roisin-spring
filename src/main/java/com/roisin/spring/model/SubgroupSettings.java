package com.roisin.spring.model;

import static com.roisin.spring.utils.ProcessConstants.BOTH;
import static com.roisin.spring.utils.ProcessConstants.K_BEST_RULES;
import static com.roisin.spring.utils.ProcessConstants.WRACC;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Subgroup settings entity
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
@Entity
@Access(AccessType.PROPERTY)
public class SubgroupSettings extends DomainEntity {

	/**
	 * Subgroup mode configuration
	 */
	private String mode;

	/**
	 * Subgroup utility function
	 */
	private String utilityFunction;

	/**
	 * Min utility variable
	 */
	private Double minUtility;

	/**
	 * k best rules
	 */
	private Integer kBestRules;

	/**
	 * Rule generation
	 */
	private String ruleGeneration;

	/**
	 * Max depth
	 */
	private Integer maxDepth;

	/**
	 * Min coverage
	 */
	private Double minCoverage;

	/**
	 * Process
	 */
	private Process process;

	public SubgroupSettings() {
		super();
		this.mode = K_BEST_RULES;
		this.utilityFunction = WRACC;
		this.minUtility = 0.0;
		this.kBestRules = 10;
		this.ruleGeneration = BOTH;
		this.maxDepth = 5;
		this.minCoverage = 0.0;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(final String mode) {
		this.mode = mode;
	}

	public String getUtilityFunction() {
		return utilityFunction;
	}

	public void setUtilityFunction(final String utilityFunction) {
		this.utilityFunction = utilityFunction;
	}

	public Double getMinUtility() {
		return minUtility;
	}

	public void setMinUtility(final Double minUtility) {
		this.minUtility = minUtility;
	}

	public Integer getKBestRules() {
		return kBestRules;
	}

	public void setKBestRules(final Integer kBestRules) {
		this.kBestRules = kBestRules;
	}

	public String getRuleGeneration() {
		return ruleGeneration;
	}

	public void setRuleGeneration(final String ruleGeneration) {
		this.ruleGeneration = ruleGeneration;
	}

	public Integer getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(final Integer maxDepth) {
		this.maxDepth = maxDepth;
	}

	public Double getMinCoverage() {
		return minCoverage;
	}

	public void setMinCoverage(final Double minCoverage) {
		this.minCoverage = minCoverage;
	}

	@OneToOne
	public Process getProcess() {
		return process;
	}

	public void setProcess(final Process process) {
		this.process = process;
	}

}
