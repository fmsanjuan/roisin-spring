package com.roisin.spring.forms;

public class FilterConditionForm {

	private String filterAttribute;

	private String filterOperator;

	private String filterValue;

	private int dataId;

	public FilterConditionForm() {
		super();
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

	public int getDataId() {
		return dataId;
	}

	public void setDataId(int dataId) {
		this.dataId = dataId;
	}

}
