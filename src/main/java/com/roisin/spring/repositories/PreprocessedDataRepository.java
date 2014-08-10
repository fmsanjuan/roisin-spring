package com.roisin.spring.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.roisin.spring.model.PreprocessedData;

@Repository
public interface PreprocessedDataRepository extends JpaRepository<PreprocessedData, Integer> {

	@Query("select pd from PreprocessedData pd join pd.preprocessingForm pf where pf.file.id = ?1")
	Collection<PreprocessedData> findDataByFileId(int fileId);

}
