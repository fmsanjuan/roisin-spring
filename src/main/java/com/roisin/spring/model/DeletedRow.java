package com.roisin.spring.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Deleted row entity
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
@Entity
@Access(AccessType.PROPERTY)
public class DeletedRow extends DomainEntity {

	/**
	 * Row number
	 */
	private Integer number;

	/**
	 * Preprocessing form
	 */
	private PreprocessingForm preprocessingForm;

	public DeletedRow() {
		super();
	}

	@Min(1)
	public Integer getNumber() {
		return number;
	}

	public void setNumber(final Integer number) {
		this.number = number;
	}

	@Valid
	@ManyToOne(optional = true)
	public PreprocessingForm getPreprocessingForm() {
		return preprocessingForm;
	}

	public void setPreprocessingForm(final PreprocessingForm preprocessingForm) {
		this.preprocessingForm = preprocessingForm;
	}

}
