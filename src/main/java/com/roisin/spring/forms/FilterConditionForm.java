package com.roisin.spring.forms;

public class FilterConditionForm {

	/**
	 * Filter attribute
	 */
	private String filterAttribute;

	/**
	 * Filter operator
	 */
	private String filterOperator;

	/**
	 * Filter value
	 */
	private String filterValue;

	/**
	 * Data id
	 */
	private int dataId;

	public FilterConditionForm() {
		super();
	}

	public String getFilterAttribute() {
		return filterAttribute;
	}

	public void setFilterAttribute(final String filterAttribute) {
		this.filterAttribute = filterAttribute;
	}

	public String getFilterOperator() {
		return filterOperator;
	}

	public void setFilterOperator(final String filterOperator) {
		this.filterOperator = filterOperator;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(final String filterValue) {
		this.filterValue = filterValue;
	}

	public int getDataId() {
		return dataId;
	}

	public void setDataId(final int dataId) {
		this.dataId = dataId;
	}

}
