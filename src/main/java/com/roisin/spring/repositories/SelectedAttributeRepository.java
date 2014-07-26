package com.roisin.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.roisin.spring.model.SelectedAttribute;

public interface SelectedAttributeRepository extends JpaRepository<SelectedAttribute, Integer> {

	@Query("select sa from SelectedAttribute sa where sa.preprocessingForm.id = ?1 and sa.name = ?2")
	SelectedAttribute findLabel(int formId, String label);
}
