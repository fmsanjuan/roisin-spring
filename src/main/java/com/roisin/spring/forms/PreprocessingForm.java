package com.roisin.spring.forms;

import java.util.List;
import java.util.SortedSet;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.roisin.spring.utils.ProcessConstants;

public class PreprocessingForm {

	private List<String> attributeSelection;

	private SortedSet<Integer> deletedRows;

	private String filterAttribute;

	private String filterOperator;

	private String filterValue;

	private String filterCondition;

	private String label;

	private String filePath;

	private int exampleSetSize;

	/*
	 * Ripper Form
	 */
	private String ripperCriterion;

	private Double sampleRatio;

	private Double pureness;

	private Double minimalPruneBenefit;
	/*
	 * Subgroup Discovery Form
	 */
	private String mode;

	private String utilityFunction;

	private Double minUtility;

	private Integer kBestRules;

	private Integer maxDepth;

	private Double minCoverage;
	/*
	 * Tree 2 Rules Form
	 */
	private String tree2RulesCriterion;

	private Integer minimalSizeForSplit;

	private Integer minimalLeafSize;

	private Double minimalGain;

	private Integer maximalDepth;

	private Double confidence;

	private Integer numberOfPrepruningAlternatives;

	private Boolean noPrepruning;

	private Boolean noPruning;

	public PreprocessingForm() {
		attributeSelection = Lists.newArrayList();
		deletedRows = Sets.newTreeSet();
		filterCondition = StringUtils.EMPTY;
		label = StringUtils.EMPTY;
		// Ripper
		this.ripperCriterion = ProcessConstants.INFORMATION_GAIN;
		this.sampleRatio = 0.9;
		this.pureness = 0.9;
		this.minimalPruneBenefit = 0.25;
		// Subgroup
		this.mode = ProcessConstants.K_BEST_RULES;
		this.utilityFunction = ProcessConstants.WRACC;
		this.minUtility = 0.0;
		this.kBestRules = 10;
		this.maxDepth = 5;
		this.minCoverage = 0.0;
		// Tree2Rules
		this.tree2RulesCriterion = ProcessConstants.GAIN_RATIO;
		this.minimalSizeForSplit = 4;
		this.minimalLeafSize = 2;
		this.minimalGain = 0.1;
		this.maximalDepth = 20;
		this.confidence = 0.25;
		this.numberOfPrepruningAlternatives = 3;
		this.noPrepruning = false;
		this.noPruning = false;
	}

	public List<String> getAttributeSelection() {
		return attributeSelection;
	}

	public void setAttributeSelection(List<String> attributeSelection) {
		this.attributeSelection = attributeSelection;
	}

	public SortedSet<Integer> getDeletedRows() {
		return deletedRows;
	}

	public void setDeletedRows(SortedSet<Integer> deletedRows) {
		this.deletedRows = deletedRows;
	}

	public String getFilterCondition() {
		return filterCondition;
	}

	public void setFilterCondition(String filterCondition) {
		this.filterCondition = filterCondition;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFilterAttribute() {
		return filterAttribute;
	}

	public void setFilterAttribute(String filterAttribute) {
		this.filterAttribute = filterAttribute;
	}

	public String getFilterOperator() {
		return filterOperator;
	}

	public void setFilterOperator(String filterOperator) {
		this.filterOperator = filterOperator;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getExampleSetSize() {
		return exampleSetSize;
	}

	public void setExampleSetSize(int exampleSetSize) {
		this.exampleSetSize = exampleSetSize;
	}

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

}
