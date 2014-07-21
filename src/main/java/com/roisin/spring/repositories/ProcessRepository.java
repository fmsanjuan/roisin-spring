package com.roisin.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roisin.spring.model.Process;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Integer> {

}
