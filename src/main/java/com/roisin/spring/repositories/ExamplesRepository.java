package com.roisin.spring.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.roisin.spring.model.PreprocessedData;

@Repository
public interface ExamplesRepository extends JpaRepository<PreprocessedData, Integer> {

	@Query("select e from Examples e where e.user.id= ?1")
	Collection<PreprocessedData> findExamplesByUserId(int userId);

}
