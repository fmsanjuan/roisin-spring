package com.roisin.spring.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.roisin.spring.model.DeletedRow;

public interface DeletedRowRepository extends JpaRepository<DeletedRow, Integer> {

	@Query("select dr from DeletedRow dr where dr.preprocessingForm.id = ?1")
	Collection<DeletedRow> findFormDeletedRows(int formId);

	@Query("select dr from DeletedRow dr where dr.preprocessingForm.id = ?1 and dr.number = ?2")
	DeletedRow findSpecificDeletedRow(int formId, int number);

}
