package com.roisin.spring.forms;

import java.util.List;

import com.google.common.collect.Lists;

public class PreproSimpleForm {

	private List<String> attributeSelection;

	private String label;

	private String dataId;

	public PreproSimpleForm() {
		attributeSelection = Lists.newArrayList();
	}

	public List<String> getAttributeSelection() {
		return attributeSelection;
	}

	public void setAttributeSelection(List<String> attributeSelection) {
		this.attributeSelection = attributeSelection;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

}
