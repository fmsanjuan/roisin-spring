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

/**
 * Preprocessing form entity
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
@Entity
@Access(AccessType.PROPERTY)
public class PreprocessingForm extends DomainEntity {

	/**
	 * Filter condition
	 */
	private String filterCondition;

	/**
	 * Deleted rows
	 */
	private Collection<DeletedRow> deletedRows;

	/**
	 * Selected attributes
	 */
	private Collection<SelectedAttribute> selectedAtts;

	/**
	 * Preprocessed data
	 */
	private PreprocessedData preprocessedData;

	/**
	 * File
	 */
	private File file;

	public PreprocessingForm() {
		super();
		deletedRows = new ArrayList<DeletedRow>();
		selectedAtts = new ArrayList<SelectedAttribute>();
	}

	public String getFilterCondition() {
		return filterCondition;
	}

	public void setFilterCondition(final String filterCondition) {
		this.filterCondition = filterCondition;
	}

	@Valid
	@OneToMany(mappedBy = "preprocessingForm")
	public Collection<DeletedRow> getDeletedRows() {
		return deletedRows;
	}

	public void setDeletedRows(final Collection<DeletedRow> deletedRows) {
		this.deletedRows = deletedRows;
	}

	@Valid
	@OneToMany(mappedBy = "preprocessingForm", cascade = CascadeType.ALL)
	public Collection<SelectedAttribute> getSelectedAtts() {
		return selectedAtts;
	}

	public void setSelectedAtts(final Collection<SelectedAttribute> selectedAtts) {
		this.selectedAtts = selectedAtts;
	}

	@Valid
	@OneToOne(mappedBy = "preprocessingForm", cascade = CascadeType.ALL)
	public PreprocessedData getPreprocessedData() {
		return preprocessedData;
	}

	public void setPreprocessedData(final PreprocessedData preprocessedData) {
		this.preprocessedData = preprocessedData;
	}

	@Valid
	@ManyToOne(optional = true)
	public File getFile() {
		return file;
	}

	public void setFile(final File file) {
		this.file = file;
	}

}
