package com.roisin.spring.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.roisin.spring.model.File;

public interface FileRepository extends JpaRepository<File, Integer> {

	@Query("select f from File f where f.user.id= ?1")
	Collection<File> findFilesByUserId(int userId);

}
