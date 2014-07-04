package com.roisin.spring.model;

import java.util.List;
import java.util.SortedSet;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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

	public PreprocessingForm() {
		attributeSelection = Lists.newArrayList();
		deletedRows = Sets.newTreeSet();
		filterCondition = StringUtils.EMPTY;
		label = StringUtils.EMPTY;
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

}
