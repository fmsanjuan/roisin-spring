package com.roisin.spring.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Process entity
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
@Entity
@Access(AccessType.PROPERTY)
public class Process extends DomainEntity {

	/**
	 * ALgorithm
	 */
	private String algorithm;

	/**
	 * Label
	 */
	private SelectedAttribute label;

	/**
	 * Ripper configuration
	 */
	private RipperSettings ripperSettings;

	/**
	 * Subgroup configuration
	 */
	private SubgroupSettings subgroupSettings;

	/**
	 * Tree to rules configuration
	 */
	private TreeToRulesSettings treeSettings;

	/**
	 * Preprocessed data
	 */
	private PreprocessedData preprocessedData;

	/**
	 * Process results
	 */
	private Results results;

	public Process() {
		super();
	}

	@NotBlank
	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(final String algorithm) {
		this.algorithm = algorithm;
	}

	@OneToOne(mappedBy = "process", optional = true, cascade = CascadeType.ALL)
	public RipperSettings getRipperSettings() {
		return ripperSettings;
	}

	public void setRipperSettings(final RipperSettings ripperSettings) {
		this.ripperSettings = ripperSettings;
	}

	@OneToOne(mappedBy = "process", optional = true, cascade = CascadeType.ALL)
	public SubgroupSettings getSubgroupSettings() {
		return subgroupSettings;
	}

	public void setSubgroupSettings(final SubgroupSettings subgroupSettings) {
		this.subgroupSettings = subgroupSettings;
	}

	@OneToOne(mappedBy = "process", optional = true, cascade = CascadeType.ALL)
	public TreeToRulesSettings getTreeSettings() {
		return treeSettings;
	}

	public void setTreeSettings(final TreeToRulesSettings treeSettings) {
		this.treeSettings = treeSettings;
	}

	@Valid
	@ManyToOne(optional = true)
	public PreprocessedData getPreprocessedData() {
		return preprocessedData;
	}

	public void setPreprocessedData(final PreprocessedData preprocessedData) {
		this.preprocessedData = preprocessedData;
	}

	@Valid
	@OneToOne(mappedBy = "process", cascade = CascadeType.ALL)
	public Results getResults() {
		return results;
	}

	public void setResults(final Results results) {
		this.results = results;
	}

	@Valid
	@ManyToOne(optional = true)
	public SelectedAttribute getLabel() {
		return label;
	}

	public void setLabel(final SelectedAttribute label) {
		this.label = label;
	}

}
