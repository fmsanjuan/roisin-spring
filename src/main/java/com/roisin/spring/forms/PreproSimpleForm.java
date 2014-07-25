package com.roisin.spring.forms;

import java.util.List;
import java.util.SortedSet;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class PreproSimpleForm {

	private List<String> attributeSelection;

	private SortedSet<Integer> deletedRows;

	private String filterAttribute;

	private String filterOperator;

	private String filterValue;

	private String filterCondition;

	public PreproSimpleForm() {
		attributeSelection = Lists.newArrayList();
		deletedRows = Sets.newTreeSet();
		filterCondition = StringUtils.EMPTY;
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

}
