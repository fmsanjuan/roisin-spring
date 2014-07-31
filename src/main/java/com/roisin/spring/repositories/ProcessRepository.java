package com.roisin.spring.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.roisin.spring.model.Process;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Integer> {
	// @Query("select f from PreprocessingForm f where f.preprocessedData.id = ?1")

	@Query("select p from Process p where p.preprocessedData.id = ?1 and p.algorithm = 'roisinnull'")
	Collection<Process> findNullDataProcesses(int dataId);

}
