package com.roisin.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roisin.spring.model.DeletedRow;

public interface DeletedRowRepository extends JpaRepository<DeletedRow, Integer> {

}
