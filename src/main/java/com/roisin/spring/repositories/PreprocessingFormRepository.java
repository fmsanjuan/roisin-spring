package com.roisin.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.roisin.spring.model.PreprocessingForm;

public interface PreprocessingFormRepository extends JpaRepository<PreprocessingForm, Integer> {

	@Query("select f from PreprocessingForm f where f.preprocessedData.id = ?1")
	PreprocessingForm findFormByDataId(int dataId);

}
