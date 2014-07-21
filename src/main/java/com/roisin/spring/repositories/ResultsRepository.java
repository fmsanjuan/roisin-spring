package com.roisin.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roisin.spring.model.Results;

@Repository
public interface ResultsRepository extends JpaRepository<Results, Integer> {

}
