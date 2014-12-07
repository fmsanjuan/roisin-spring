/* LoginService.java
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package com.roisin.spring.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.roisin.spring.services.FileUtils;

@Service
@Transactional
public class LoginService implements UserDetailsService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserAccountRepository userRepository;
	
	@Autowired
	private FileUtils fileUtils;

	// Business methods -------------------------------------------------------

	public UserAccount findOne(Integer id) {
		return userRepository.findOne(id);
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Assert.notNull(username);

		UserDetails result;

		result = userRepository.findByUsername(username);
		Assert.notNull(result);
		// WARNING: The following sentences prevent lazy initialisation
		// problems!
		Assert.notNull(result.getAuthorities());
		result.getAuthorities().size();

		return result;
	}

	public static UserAccount getPrincipal() {
		UserAccount result;
		SecurityContext context;
		Authentication authentication;
		Object principal;

		// If the asserts in this method fail, then you're
		// likely to have your Tomcat's working directory
		// corrupt. Please, clear your browser's cache, stop
		// Tomcat, update your Maven's project configuration,
		// clean your project, clean Tomcat's working directory,
		// republish your project, and start it over.

		context = SecurityContextHolder.getContext();
		Assert.notNull(context);
		authentication = context.getAuthentication();
		Assert.notNull(authentication);
		principal = authentication.getPrincipal();
		Assert.isTrue(principal instanceof UserAccount);
		result = (UserAccount) principal;
		Assert.notNull(result);
		Assert.isTrue(result.getId() != 0);

		return result;
	}

	public boolean activateAccount(Integer id, String activationKey) {
		boolean activated = false;
		UserAccount account = userRepository.findOne(id);

		String dbActivationKey = generateUserAccountActivationKey(account);

		if (dbActivationKey.equals(activationKey)) {
			account.setEnabled(true);
			userRepository.save(account);
			activated = true;
		}
		return activated;
	}

	public String generateUserAccountActivationKey(UserAccount account) {
		StringBuilder sb = new StringBuilder();
		sb.append(account.getUsername());
		sb.append(account.getId());
		sb.append(fileUtils.getActivationToken());

		return DigestUtils.sha256Hex(sb.toString());
	}

}
