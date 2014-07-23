package com.roisin.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roisin.spring.model.PreprocessingForm;

public interface PreprocessingFormRepository extends JpaRepository<PreprocessingForm, Integer> {

}
