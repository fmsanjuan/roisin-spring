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

import java.util.Collection;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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

	/**
	 * User account repository
	 */
	@Autowired
	private transient UserAccountRepository userRepository;

	/**
	 * File utility class
	 */
	@Autowired
	private transient FileUtils fileUtils;

	// Business methods -------------------------------------------------------

	public UserAccount findOne(final Integer identifier) {
		return userRepository.findOne(identifier);
	}

	public UserAccount findByUsername(final String username) {
		return userRepository.findByUsername(username);
	}

	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
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

	public static int getPrincipalId(final UserAccount principal) {
		int principalId = -1;
		if (principal != null) {
			principalId = principal.getId();
		}
		return principalId;
	}

	public static Collection<Authority> getPrincipalAuthorities(final UserAccount principal) {
		Collection<Authority> res = null;
		if (principal != null) {
			res = principal.getAuthorities();
		}
		return res;
	}

	public boolean activateAccount(final Integer identifier, final String activationKey) {
		boolean activated = false;
		final UserAccount account = userRepository.findOne(identifier);

		final String dbActivationKey = generateUserAccountActivationKey(account);

		if (dbActivationKey.equals(activationKey)) {
			account.setEnabled(true);
			userRepository.save(account);
			activated = true;
		}
		return activated;
	}

	public String generateUserAccountActivationKey(final UserAccount account) {
		final StringBuilder strb = new StringBuilder();
		strb.append(account.getUsername());
		strb.append(account.getId());
		strb.append(fileUtils.getActivationToken());

		return DigestUtils.sha256Hex(strb.toString());
	}

	public void sendPasswordRecoverEmail(final String email) {
		final UserAccount userAccount = userRepository.findByUsername(email);
		userAccount.setActivation(new Date());

	}

	public String generatePasswordRecoveryKey(final UserAccount userAccount) {
		final StringBuilder strb = new StringBuilder();
		strb.append(userAccount.getUsername());
		strb.append(userAccount.getActivation().getTime());
		strb.append(fileUtils.getActivationToken());

		return DigestUtils.sha256Hex(strb.toString());
	}

	public UserAccount changePassword(final UserAccount userAccount, final String newPassword) {
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		userAccount.setPassword(encoder.encodePassword(newPassword, null));
		return userRepository.save(userAccount);
	}

}
