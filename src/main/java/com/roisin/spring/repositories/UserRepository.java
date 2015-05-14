package com.roisin.spring.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.roisin.spring.model.User;

/**
 * User repository
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * Returns the user entity given a username
	 * 
	 * @param username
	 * @return user
	 */
	@Query("select a from User a where a.userAccount.username = '?1'")
	Collection<User> findUserByUsername(String username);

	/**
	 * Returns a user account given its identifier
	 * 
	 * @param id
	 * @return
	 */
	@Query("select a from User a where a.userAccount.id= ?1")
	User findByUserAccountId(int id);

}
