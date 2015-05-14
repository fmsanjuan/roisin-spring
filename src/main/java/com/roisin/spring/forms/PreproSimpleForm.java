package com.roisin.spring.forms;

import java.util.List;

import com.google.common.collect.Lists;

public class PreproSimpleForm {

	/**
	 * Export attribute selection
	 */
	private List<String> exportAttributeSelection;

	/**
	 * Process attribute selection
	 */
	private List<String> processAttributeSelection;

	/**
	 * Label
	 */
	private String label;

	/**
	 * Data identifier
	 */
	private String dataId;

	/**
	 * Export format
	 */
	private String exportFormat;

	/**
	 * Name
	 */
	private String name;

	/**
	 * Description
	 */
	private String description;

	public PreproSimpleForm() {
		exportAttributeSelection = Lists.newArrayList();
		processAttributeSelection = Lists.newArrayList();
	}

	public List<String> getExportAttributeSelection() {
		return exportAttributeSelection;
	}

	public void setExportAttributeSelection(final List<String> exportAttributeSelection) {
		this.exportAttributeSelection = exportAttributeSelection;
	}

	public List<String> getProcessAttributeSelection() {
		return processAttributeSelection;
	}

	public void setProcessAttributeSelection(final List<String> processAttributeSelection) {
		this.processAttributeSelection = processAttributeSelection;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(final String dataId) {
		this.dataId = dataId;
	}

	public String getExportFormat() {
		return exportFormat;
	}

	public void setExportFormat(final String exportFormat) {
		this.exportFormat = exportFormat;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
