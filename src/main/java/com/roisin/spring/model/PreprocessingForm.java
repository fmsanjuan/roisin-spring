package com.roisin.spring.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class PreprocessingForm extends DomainEntity {

	private String filterCondition;

	private Collection<DeletedRow> deletedRows;

	private Collection<SelectedAttribute> selectedAttributes;

	private PreprocessedData preprocessedData;

	private File file;

	public PreprocessingForm() {
		super();
		deletedRows = new ArrayList<DeletedRow>();
		selectedAttributes = new ArrayList<SelectedAttribute>();
	}

	public String getFilterCondition() {
		return filterCondition;
	}

	public void setFilterCondition(String filterCondition) {
		this.filterCondition = filterCondition;
	}

	@Valid
	@OneToMany(mappedBy = "preprocessingForm")
	public Collection<DeletedRow> getDeletedRows() {
		return deletedRows;
	}

	public void setDeletedRows(Collection<DeletedRow> deletedRows) {
		this.deletedRows = deletedRows;
	}

	@Valid
	@OneToMany(mappedBy = "preprocessingForm", cascade = CascadeType.ALL)
	public Collection<SelectedAttribute> getSelectedAttributes() {
		return selectedAttributes;
	}

	public void setSelectedAttributes(Collection<SelectedAttribute> selectedAttributes) {
		this.selectedAttributes = selectedAttributes;
	}

	@Valid
	@OneToOne(mappedBy = "preprocessingForm", cascade = CascadeType.ALL)
	public PreprocessedData getPreprocessedData() {
		return preprocessedData;
	}

	public void setPreprocessedData(PreprocessedData preprocessedData) {
		this.preprocessedData = preprocessedData;
	}

	@Valid
	@ManyToOne(optional = true)
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
