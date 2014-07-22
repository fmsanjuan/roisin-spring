package com.roisin.spring.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Process extends DomainEntity {

	private String algorithm;

	private SelectedAttribute label;

	private RipperSettings ripperSettings;

	private SubgroupSettings subgroupSettings;

	private TreeToRulesSettings treeToRulesSettings;

	private PreprocessedData preprocessedData;

	private Results results;

	public Process() {
		super();
	}

	@NotBlank
	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	@OneToOne(mappedBy = "process", optional = true)
	public RipperSettings getRipperSettings() {
		return ripperSettings;
	}

	public void setRipperSettings(RipperSettings ripperSettings) {
		this.ripperSettings = ripperSettings;
	}

	@OneToOne(mappedBy = "process", optional = true)
	public SubgroupSettings getSubgroupSettings() {
		return subgroupSettings;
	}

	public void setSubgroupSettings(SubgroupSettings subgroupSettings) {
		this.subgroupSettings = subgroupSettings;
	}

	@OneToOne(mappedBy = "process", optional = true)
	public TreeToRulesSettings getTreeToRulesSettings() {
		return treeToRulesSettings;
	}

	public void setTreeToRulesSettings(TreeToRulesSettings treeToRulesSettings) {
		this.treeToRulesSettings = treeToRulesSettings;
	}

	@Valid
	@ManyToOne(optional = true)
	public PreprocessedData getPreprocessedData() {
		return preprocessedData;
	}

	public void setPreprocessedData(PreprocessedData preprocessedData) {
		this.preprocessedData = preprocessedData;
	}

	@Valid
	@OneToOne(mappedBy = "process", cascade = CascadeType.ALL)
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	@Valid
	@OneToOne(mappedBy = "process", cascade = CascadeType.ALL)
	public SelectedAttribute getLabel() {
		return label;
	}

	public void setLabel(SelectedAttribute label) {
		this.label = label;
	}

}
