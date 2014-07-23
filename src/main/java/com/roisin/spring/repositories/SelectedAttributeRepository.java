package com.roisin.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roisin.spring.model.SelectedAttribute;

public interface SelectedAttributeRepository extends JpaRepository<SelectedAttribute, Integer> {

}
