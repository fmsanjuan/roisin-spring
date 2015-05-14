package com.roisin.spring.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.roisin.spring.model.PreprocessedData;

@Repository
public interface PreprocessedDataRepository extends JpaRepository<PreprocessedData, Integer> {

	/**
	 * Returns all the preprocessed data of a certain file
	 * 
	 * @param fileId
	 * @return preprocessed data collection
	 */
	@Query("select pd from PreprocessedData pd join pd.preprocessingForm pf where pf.file.id = ?1")
	Collection<PreprocessedData> findDataByFileId(int fileId);

	/**
	 * Returns preprocessed data that was temporaly created
	 * 
	 * @param fileId
	 * @return preprocessed data collection
	 */
	@Query("select pd from PreprocessedData pd join pd.preprocessingForm pf where pf.file.id = ?1 and pd.name = 'roisinnull' and pd.description = 'roisinnull'")
	Collection<PreprocessedData> findNullDataByFileId(int fileId);

}
