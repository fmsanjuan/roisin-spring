package com.roisin.spring.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.roisin.spring.model.PreprocessingForm;

public interface PreprocessingFormRepository extends JpaRepository<PreprocessingForm, Integer> {

	@Query("select f from PreprocessingForm f where f.preprocessedData.id = ?1")
	PreprocessingForm findFormByDataId(int dataId);

	@Query("select f from PreprocessingForm f where f.file.id = ?1")
	Collection<PreprocessingForm> findFormsByFileId(int fileId);

	@Query("select pf from PreprocessingForm pf join pf.preprocessedData pd where pf.file.id = ?1 and pd.name = 'roisinnull' and pd.description = 'roisinnull'")
	Collection<PreprocessingForm> findNullDataForms(int fileId);

}
