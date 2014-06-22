package com.roisin.spring.model;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class PreprocessingForm {

	private List<String> deletedAttributes;

	private Set<Integer> deletedRows;

	private String filterCondition;

	private String label;

	public PreprocessingForm() {
		deletedAttributes = Lists.newArrayList();
		deletedRows = Sets.newHashSet();
		filterCondition = StringUtils.EMPTY;
		label = StringUtils.EMPTY;
	}

	public List<String> getDeletedAttributes() {
		return deletedAttributes;
	}

	public void setDeletedAttributes(List<String> deletedAttributes) {
		this.deletedAttributes = deletedAttributes;
	}

	public Set<Integer> getDeletedRows() {
		return deletedRows;
	}

	public void setDeletedRows(Set<Integer> deletedRows) {
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

}
