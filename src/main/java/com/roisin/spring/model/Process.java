package com.roisin.spring.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@TableGenerator(name = "Process", schema = "roisin")
public class Process extends DomainEntity {

	private String algorithm;

	private String filterCondition;

	private String label;

	private RipperSettings ripperSettings;

	private SubgroupSettings subgroupSettings;

	private TreeToRulesSettings treeToRulesSettings;

	private Examples examples;

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

	@NotBlank
	public String getFilterCondition() {
		return filterCondition;
	}

	public void setFilterCondition(String filterCondition) {
		this.filterCondition = filterCondition;
	}

	@NotBlank
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@OneToOne(mappedBy = "process")
	public RipperSettings getRipperSettings() {
		return ripperSettings;
	}

	public void setRipperSettings(RipperSettings ripperSettings) {
		this.ripperSettings = ripperSettings;
	}

	@OneToOne(mappedBy = "process")
	public SubgroupSettings getSubgroupSettings() {
		return subgroupSettings;
	}

	public void setSubgroupSettings(SubgroupSettings subgroupSettings) {
		this.subgroupSettings = subgroupSettings;
	}

	@OneToOne(mappedBy = "process")
	public TreeToRulesSettings getTreeToRulesSettings() {
		return treeToRulesSettings;
	}

	public void setTreeToRulesSettings(TreeToRulesSettings treeToRulesSettings) {
		this.treeToRulesSettings = treeToRulesSettings;
	}

	@Valid
	@ManyToOne(optional = true)
	public Examples getExamples() {
		return examples;
	}

	public void setExamples(Examples examples) {
		this.examples = examples;
	}

	@Valid
	@OneToOne
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

}
