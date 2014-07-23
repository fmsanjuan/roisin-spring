package com.roisin.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roisin.spring.model.PreprocessedData;

@Repository
public interface PreprocessedDataRepository extends JpaRepository<PreprocessedData, Integer> {

}
