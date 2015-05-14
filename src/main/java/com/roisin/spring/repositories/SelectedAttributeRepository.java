package com.roisin.spring.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.roisin.spring.model.SelectedAttribute;

/**
 * Selected attribute repository
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
public interface SelectedAttributeRepository extends JpaRepository<SelectedAttribute, Integer> {

	/**
	 * Finds a label given its name and the form it belongs to
	 * 
	 * @param formId
	 * @param label
	 * @return label
	 */
	@Query("select sa from SelectedAttribute sa where sa.preprocessingForm.id = ?1 and sa.name = ?2")
	Collection<SelectedAttribute> findLabel(int formId, String label);

	/**
	 * Returns all the selected attributes from a preprocessing form
	 * 
	 * @param formId
	 * @return
	 */
	@Query("select sa from SelectedAttribute sa where sa.preprocessingForm.id = ?1")
	Collection<SelectedAttribute> findSelectedAttributesByFormId(int formId);

}
