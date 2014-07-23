package com.roisin.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roisin.spring.model.File;

public interface FileRepository extends JpaRepository<File, Integer> {

}
