package com.roisin.spring.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class DeletedRow extends DomainEntity {

	private Integer number;

	private PreprocessingForm preprocessingForm;

	public DeletedRow() {
		super();
	}

	@Min(1)
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Valid
	@ManyToOne(optional = true)
	public PreprocessingForm getPreprocessingForm() {
		return preprocessingForm;
	}

	public void setPreprocessingForm(PreprocessingForm preprocessingForm) {
		this.preprocessingForm = preprocessingForm;
	}

}
