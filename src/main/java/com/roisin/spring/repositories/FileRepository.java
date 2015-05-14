package com.roisin.spring.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.roisin.spring.model.File;

public interface FileRepository extends JpaRepository<File, Integer> {

	/**
	 * Returns all the files that where uploaded by a certain user
	 * 
	 * @param userId
	 * @return files
	 */
	@Query("select f from File f where f.user.id= ?1")
	Collection<File> findFilesByUserId(int userId);

	/**
	 * Returns the file of a certain preprocessing form
	 * 
	 * @param formId
	 * @return file
	 */
	@Query("select f from File f join f.preprocessingForms pf where pf.id = ?1")
	File findFileByFormId(int formId);

}
