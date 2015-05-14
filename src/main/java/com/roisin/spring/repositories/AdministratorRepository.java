package com.roisin.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.roisin.spring.model.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	/**
	 * Returns the administrator given a user account identifier
	 * 
	 * @param id
	 *            user account identifier
	 * @return administrator
	 */
	@Query("select a from Administrator a where a.userAccount.id= ?1")
	Administrator findByUserAccountId(int identifier);

}
