package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.roisin.spring.model.Administrator;
import com.roisin.spring.repositories.AdministratorRepository;
import com.roisin.spring.security.Authority;
import com.roisin.spring.security.LoginService;
import com.roisin.spring.security.UserAccount;

public class AdministratorService {

	/**
	 * Administrator repository
	 */
	@Autowired
	private transient AdministratorRepository adminRepository;

	public AdministratorService() {
		super();
	}

	public boolean amIMySelf(final int userId) {
		final UserAccount principal = LoginService.getPrincipal();
		return LoginService.getPrincipalId(principal) == userId;
	}

	public boolean iAmACustomer() {
		return checkRole(Authority.USER);
	}

	public boolean iAmAnAdmin() {
		return checkRole(Authority.ADMIN);
	}

	public boolean amIAGuest() {
		return false;
	}

	private boolean checkRole(final String role) {
		final UserAccount principal = LoginService.getPrincipal();
		final Collection<Authority> authorities = LoginService.getPrincipalAuthorities(principal);

		boolean res = false;

		for (final Authority auth : authorities)
			res = res || auth.getAuthority().toUpperCase().compareTo(role) == 0;

		return res;
	}

	public Administrator findByPrincipal() {

		Administrator result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Administrator findByUserAccount(final UserAccount adminAccount) {

		Assert.notNull(adminAccount);
		Administrator result;
		result = adminRepository.findByUserAccountId(adminAccount.getId());

		return result;
	}
}
